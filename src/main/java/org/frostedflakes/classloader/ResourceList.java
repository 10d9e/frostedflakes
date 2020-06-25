package org.frostedflakes.classloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ResourceList {

	/**
	 * for all elements of java.class.path get a Collection of resources Pattern
	 * pattern = Pattern.compile(".*"); gets all resources
	 * 
	 * @param pattern the pattern to match
	 * @return the resources in the order they are found
	 */
	public static Collection<String> getResources(final Pattern pattern) {
		final ArrayList<String> retval = new ArrayList<String>();
		for (final String element : getClassPathElements()) {
			retval.addAll(getResources(element, pattern));
		}
		return retval;
	}

	public static String[] getClassPathElements() {
		final String classPath = System.getProperty("java.class.path", ".");
		final String[] classPathElements = classPath.split(System.getProperty("path.separator"));
		return classPathElements;
	}

	public static void copyClassPathToFolder(String folder) throws IOException {
		Path targetDir = Paths.get(folder);
		// delete the directory first
		if (Files.exists(targetDir)) {
			try (Stream<Path> walk = Files.walk(targetDir)) {
				walk.sorted(Comparator.reverseOrder()).map(Path::toFile).peek(System.out::println)
						.forEach(File::delete);
			}
		}
		
		Files.createDirectories(targetDir);
		
		for (String f : getClassPathElements()) {
			Path sourceFile = Paths.get(f);
			Path targetFile = targetDir.resolve(sourceFile.getFileName());

			if (sourceFile.toFile().isDirectory()) {
				CopyUtil.copyFolder(sourceFile, targetFile);
			} else {
				CopyUtil.copy(sourceFile, targetFile);
			}
		}
	}

	private static Collection<String> getResources(final String element, final Pattern pattern) {
		final ArrayList<String> retval = new ArrayList<String>();
		final File file = new File(element);
		if (file.isDirectory()) {
			retval.addAll(getResourcesFromDirectory(file, pattern));
		} else {
			retval.addAll(getResourcesFromJarFile(file, pattern));
		}
		return retval;
	}

	private static Collection<String> getResourcesFromJarFile(final File file, final Pattern pattern) {
		final ArrayList<String> retval = new ArrayList<String>();
		ZipFile zf;
		try {
			zf = new ZipFile(file);
		} catch (final ZipException e) {
			throw new Error(e);
		} catch (final IOException e) {
			throw new Error(e);
		}
		final Enumeration<? extends ZipEntry> e = zf.entries();
		while (e.hasMoreElements()) {
			final ZipEntry ze = (ZipEntry) e.nextElement();
			final String fileName = ze.getName();
			final boolean accept = pattern.matcher(fileName).matches();
			if (accept) {
				retval.add(fileName);
			}
		}
		try {
			zf.close();
		} catch (final IOException e1) {
			throw new Error(e1);
		}
		return retval;
	}

	private static Collection<String> getResourcesFromDirectory(final File directory, final Pattern pattern) {
		final ArrayList<String> retval = new ArrayList<String>();
		final File[] fileList = directory.listFiles();
		for (final File file : fileList) {
			if (file.isDirectory()) {
				retval.addAll(getResourcesFromDirectory(file, pattern));
			} else {
				try {
					final String fileName = file.getCanonicalPath();
					final boolean accept = pattern.matcher(fileName).matches();
					if (accept) {
						retval.add(fileName);
					}
				} catch (final IOException e) {
					throw new Error(e);
				}
			}
		}
		return retval;
	}

	/**
	 * list the resources that match args[0]
	 * 
	 * @param args args[0] is the pattern to match, or list all resources if there
	 *             are no args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		/*
		 * Pattern pattern; if(args.length < 1){ pattern = Pattern.compile(".*"); }
		 * else{ pattern = Pattern.compile(args[0]); } final Collection<String> list =
		 * ResourceList.getResources(pattern); for(final String name : list){
		 * System.out.println(name); }
		 */

		for (String element : ResourceList.getClassPathElements()) {
			System.out.println(element);
		}

		ResourceList.copyClassPathToFolder(".stacksnap-lib");
	}
}