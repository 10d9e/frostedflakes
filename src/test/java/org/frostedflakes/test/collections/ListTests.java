package org.frostedflakes.test.collections;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;
import org.junit.Test;

public class ListTests {

	@Test
	public void testArrayList() {

		IFrostedFlakes<List<String>> FLAKES = new FrostedFlakes<>();
		// Creating an empty Map
		List<String> list1 = new ArrayList<String>();

		// Mapping string values to int keys
		list1.add("Geeks");
		list1.add("4");
		list1.add("Geeks");
		list1.add("Welcomes");
		list1.add("You");

		// Checking the equality
		List<String> list2 = FLAKES.deepCopy(list1);

		// then
		TestUtilities.testCollectionEquality(list1, list2);
		
		assertTrue(Objects.deepEquals(list1, list2));

	}

}