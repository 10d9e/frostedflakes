package org.frostedflakes.test.primitives;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Objects;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;
import org.junit.Test;

public class PrimitiveTests {
	
	private static final double DELTA = 1e-15;

	private IFrostedFlakes<Primitives> FLAKES = new FrostedFlakes<>();

	@Test
	public void testPrimitives() {

		// Creating an empty Map
		Primitives p = new Primitives();

		// Checking the equality
		Primitives p2 = FLAKES.deepCopy(p);

		System.out.println("p: " + p);
		System.out.println("p2: " + p2);
		
		assertEquals(p.i, p2.i);
		assertTrue(Arrays.compare(p.integers, p2.integers) == 0);
		
		assertEquals(p.s, p2.s);
		assertTrue(Arrays.compare(p.shorts, p2.shorts) == 0);
		
		assertEquals(p.d, p2.d, DELTA);
		assertTrue(Arrays.compare(p.doubles, p2.doubles) == 0);
		
		assertEquals(p.i, p2.i);
		assertTrue(Arrays.compare(p.integers, p2.integers) == 0);
		
		assertEquals(p.l, p2.l);
		assertTrue(Arrays.compare(p.longs, p2.longs) == 0);
		
		assertEquals(p.f, p2.f, DELTA);
		assertTrue(Arrays.compare(p.floats, p2.floats) == 0);
		
		assertEquals(p.c, p2.c);
		assertTrue(Arrays.compare(p.chars, p2.chars) == 0);
		
		assertEquals(p.b, p2.b);
		assertTrue(Arrays.compare(p.booleans, p2.booleans) == 0);
		
		assertEquals(p.by, p2.by);
		assertTrue(Arrays.compare(p.bytes, p2.bytes) == 0);
		
		assertFalse(Objects.deepEquals(p, p2));

	}

}