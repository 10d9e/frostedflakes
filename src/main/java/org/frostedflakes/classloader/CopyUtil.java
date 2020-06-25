package org.frostedflakes.classloader;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class CopyUtil {

	public static void copyFolder(Path src, Path dest) throws IOException {
		Files.walk(src).forEach(source -> copy(source, dest.resolve(src.relativize(source))));
	}

	public static void copy(Path source, Path dest) {
		try {
			Files.copy(source, dest, REPLACE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}