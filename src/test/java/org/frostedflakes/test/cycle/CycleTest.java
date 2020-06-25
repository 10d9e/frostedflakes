package org.frostedflakes.test.cycle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;
import org.junit.Test;

public class CycleTest {
	
	private IFrostedFlakes<C> FLAKES = new FrostedFlakes<>();
	
	@Test
	public void testSerialize() {

		C test = new C();

		byte[] bytes = FLAKES.toBytes(test);

		C test2 = FLAKES.fromBytes(bytes);
		// then
		assertEquals(test2.a.b, test2.b);
		
		assertFalse(Objects.deepEquals(test, test2));
	}


}