package org.frostedflakes.test.primitives;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Objects;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;
import org.junit.Test;

public class EnumTest {
	
	private static final double DELTA = 1e-15;

	private IFrostedFlakes<ObjectWithEnum> FLAKES = new FrostedFlakes<>();

	@Test
	public void testPrimitives() {

		// Creating an empty Map
		ObjectWithEnum o = new ObjectWithEnum();

		// Checking the equality
		ObjectWithEnum o2 = FLAKES.deepCopy(o);

		System.out.println("o: " + o);
		System.out.println("o2: " + o2);
		
		assertEquals(o.s, o2.s);

		assertEquals(o.s2, o2.s2);
		
		//assertTrue(Objects.deepEquals(o, o2));
	}

}