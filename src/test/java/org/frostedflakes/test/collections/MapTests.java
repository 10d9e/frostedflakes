package org.frostedflakes.test.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;
import org.frostedflakes.test.object.DefaultRepositoryMetadata;
import org.junit.Test;

public class MapTests {


	@Test
	public void testHashMap() {
		
		final IFrostedFlakes<Map<Integer, String>> FLAKES = new FrostedFlakes<>();

		// Creating an empty Map
		Map<Integer, String> map1 = new HashMap<>();

		// Mapping string values to int keys
		map1.put(10, "Geeks");
		map1.put(15, "4");
		map1.put(20, "Geeks");
		map1.put(25, "Welcomes");
		map1.put(30, "You");

		// Checking the equality
		Map<Integer, String> map2 = FLAKES.deepCopy(map1);
		TestUtilities.testMapEquality(map1, map2);
		assertTrue(Objects.deepEquals(map1, map2));
	}

	@Test
	public void testConcurrentHashMap() {
		
		final IFrostedFlakes<Map<Integer, String>> FLAKES = new FrostedFlakes<>();
		
		Map<Integer, String> map1 = new ConcurrentHashMap<>();
		map1.put(1, "hello");
		map1.put(2, "hi");
		map1.put(3, "bonjour");

		byte[] bytes = FLAKES.toBytes(map1);

		Map<Integer, String> map2 = FLAKES.fromBytes(bytes);
		// then
		assertEquals(map1.size(), map2.size());
		assertTrue(map1.equals(map2));
		assertTrue(Objects.deepEquals(map1, map2));
	}
	
	@Test
	public void testConcurrentHashMap2() throws IOException {
		
		final IFrostedFlakes<Map<Class<?>, String>> FLAKES = new FrostedFlakes<>();

		Map<Class<?>, String> map1 = new ConcurrentHashMap<>();		
		map1.put(IFrostedFlakes.class, "hello");
		
		// then
		
		FLAKES.save(map1, "map.bin");

		Map<Class<?>, String> map2 = FLAKES.open("map.bin");
		
		assertEquals(map1.get(IFrostedFlakes.class), map2.get(IFrostedFlakes.class));
		assertEquals(map1.size(), map2.size());
		assertTrue(map1.equals(map2));
		TestUtilities.testMapEquality(map1, map2);
		assertTrue(Objects.deepEquals(map1, map2));
	}

	@Test
	public void testZeroSizedConcurrentHashMap() {
		final IFrostedFlakes<Map<Integer, String>> FLAKES = new FrostedFlakes<>();

		Map<Integer, String> map1 = new ConcurrentHashMap<>();
		byte[] bytes = FLAKES.toBytes(map1);

		// Checking the equality
		Map<Integer, String> map2 = FLAKES.deepCopy(map1);
		TestUtilities.testMapEquality(map1, map2);
		assertTrue(Objects.deepEquals(map1, map2));
	}

	@Test
	public void testNullConcurrentHashMap() {
		final IFrostedFlakes<Map<Integer, String>> FLAKES = new FrostedFlakes<>();

		Map<Integer, String> map1 = null;
		byte[] bytes = FLAKES.toBytes(map1);

		Map<Integer, String> map2 = FLAKES.fromBytes(bytes);

		assertNull(map1);
		assertNull(map2);
		assertTrue(Objects.deepEquals(map1, map2));
	}

}