package org.frostedflakes.classloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;


public class DynamicClassLoader extends AggressiveClassLoader {
	
	LinkedList<Function<String, byte[]>> loaders = new LinkedList<>();
	
	public DynamicClassLoader() {
		List<String> paths = new ArrayList<>();
		
		paths.add("/Users/jaylogelin/Documents/stacksnap/target/stacksnap-0.0.1-SNAPSHOT.jar");
		
		for (String element : ResourceList.getClassPathElements()) {
			System.out.println(element);
			paths.add(element);
		}
		for (String path : paths) {
			File file = new File(path);

			Function<String, byte[]> loader = loader(file);
			if (loader == null) {
				throw new RuntimeException("Path not exists " + path);
			}
			loaders.add(loader);
		}
	}
	
	public DynamicClassLoader(String... paths) {
		for (String path : paths) {
			File file = new File(path);

			Function<String, byte[]> loader = loader(file);
			if (loader == null) {
				throw new RuntimeException("Path not exists " + path);
			}
			loaders.add(loader);
		}
	}

	@SuppressWarnings("UnusedDeclaration")
	public DynamicClassLoader(Collection<File> paths) {
		for (File file : paths) {
			Function<String, byte[]> loader = loader(file);
			if (loader == null) {
				throw new RuntimeException("Path not exists " + file.getPath());
			}
			loaders.add(loader);
		}
	}

	public static Function<String, byte[]> loader(File file) {
		if (!file.exists()) {
			return null;
		} else if (file.isDirectory()) {
			return dirLoader(file);
		} else {
			try {
				final JarFile jarFile = new JarFile(file);

				return jarLoader(jarFile);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static File findFile(String filePath, File classPath) {
		File file = new File(classPath, filePath);
		return file.exists() ? file : null;
	}

	public static Function<String, byte[]> dirLoader(final File dir) {
		return filePath -> {
			File file = findFile(filePath, dir);
			if (file == null) {
				return null;
			}
			try {
				return Files.readAllBytes(file.toPath());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		};
	}

	private static Function<String, byte[]> jarLoader(final JarFile jarFile) {
		return new Function<String, byte[]>() {
			@Override
			public byte[] apply(String filePath) {
				ZipEntry entry = jarFile.getJarEntry(filePath);
				if (entry == null) {
					return null;
				}
				try {
					return jarFile.getInputStream(entry).readAllBytes();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	@Override
	protected byte[] loadNewClass(String name) {
		System.out.println("Loading class " + name);
		for (Function<String, byte[]> loader : loaders) {
			byte[] data = loader.apply(AggressiveClassLoader.toFilePath(name));
			if (data != null) {
				return data;
			}
		}
		return null;
	}
}