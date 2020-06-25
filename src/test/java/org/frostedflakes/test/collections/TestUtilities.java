package org.frostedflakes.test.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

public final class TestUtilities {

	public static void testCollectionEquality(Collection<?> c1, Collection<?> c2) {
		assertTrue(c2.containsAll(c1));
		assertTrue(c1.containsAll(c2));
		assertEquals(c1.size(), c2.size());
		assertTrue(c1.equals(c2));

		for (int i = 0; i < c1.size(); i++) {
			assertEquals(c1.toArray()[i], c2.toArray()[i]);
		}

	}

	public static void testMapEquality(Map<?, ?> map1, Map<?, ?> map2) {
		assertEquals(map1.size(), map2.size());
		assertTrue(map1.equals(map2));
		testCollectionEquality(map1.entrySet(), map2.entrySet());
	}

}
