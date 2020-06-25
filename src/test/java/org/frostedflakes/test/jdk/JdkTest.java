package org.frostedflakes.test.jdk;

import static java.nio.file.Paths.get;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Objects;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;
import org.junit.Test;

public class JdkTest {

	@Test
	public void testArrayList() {
		
		File f = get("test-tmp").toFile();
	
		IFrostedFlakes<File> FLAKES = new FrostedFlakes<>();
		// Creating an empty Map
		
		// Checking the equality
		File f2 = FLAKES.deepCopy(f);

		// then
		//TestUtilities.testCollectionEquality(list1, list2);

		assertTrue(Objects.deepEquals(f, f2));
	}

}