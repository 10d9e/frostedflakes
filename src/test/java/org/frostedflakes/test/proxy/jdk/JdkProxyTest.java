package org.frostedflakes.test.proxy.jdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.Objects;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;
import org.junit.Test;

public class JdkProxyTest {

	@Test
	public void testDynamicProxy() {

		ServiceWithDynamicProxy test = new ServiceWithDynamicProxy();
		System.out.println(test.getState());
		test.setState("butter");
		System.out.println(test.getState());

		IFrostedFlakes<ServiceWithDynamicProxy> flakes = new FrostedFlakes<>();
		ServiceWithDynamicProxy test2 = flakes.deepCopy(test);

		System.out.println( test2.getState() );

		assertEquals(test.getState(), test2.getState());
		assertFalse(Objects.deepEquals(test, test2));

	}
	
	@Test
	public void testDynamicProxyFile() throws IOException {
		IFrostedFlakes<ServiceWithDynamicProxy> flakes = new FrostedFlakes<>();

		ServiceWithDynamicProxy test = new ServiceWithDynamicProxy();
		test.setState("butter");
		flakes.save(test, "testDyn.out");
		
		ServiceWithDynamicProxy test2 = flakes.open("testDyn.out");

		System.out.println( test2.getState() );

		assertEquals(test.getState(), test2.getState());
		assertFalse(Objects.deepEquals(test, test2));

	}
}
