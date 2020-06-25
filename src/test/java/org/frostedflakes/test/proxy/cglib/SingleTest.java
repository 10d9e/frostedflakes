package org.frostedflakes.test.proxy.cglib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;
import org.junit.Test;

public class SingleTest {

	private IFrostedFlakes<Service> FLAKES = new FrostedFlakes<>();

	@Test
	public void testProxy() throws Exception {
		// given
		Service service = new Service();
		// when
		String res = service.setState(null);

		// then
		assertEquals("Hello Tom!", res);

		byte[] bytes = FLAKES.toBytes(service);
		Service test2 = FLAKES.fromBytes(bytes);
		System.out.println(test2);
		
		assertFalse(Objects.deepEquals(service, test2));
	}
}
