package org.frostedflakes.test.object;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;
import org.frostedflakes.test.collections.TestUtilities;
import org.junit.Test;

public class FileSerializationTests {

	@Test
	public void testFileIO() throws IOException {

		IFrostedFlakes<A> FLAKES = new FrostedFlakes<>();
		// Creating an empty Map
		List<String> list1 = new ArrayList<String>();

		// Mapping string values to int keys
		list1.add("Jay");
		list1.add("4");
		list1.add("Geeks");
		list1.add("Welcomes");
		list1.add("You");

		A a = new A(list1);

		// Checking the equality

		FLAKES.save(a, "a.bin");

		A a1 = FLAKES.open("a.bin");

		// then
		TestUtilities.testCollectionEquality(a1.list, a.list);

		assertFalse(Objects.deepEquals(a, a1));

	}

}