package org.frostedflakes.test.proxy.cglib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;
import org.junit.Test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyIntegrationTest {

	private IFrostedFlakes<Original> FLAKES = new FrostedFlakes<>();

	@Test
	public void noProxyTest() {
		// given
		Original service = new Original();

		// when
		String res = service.setState("Tom");

		// then
		assertEquals(res, "Hello Tom");
		
		byte[] bytes = FLAKES.toBytes(service);
		Original test2 = FLAKES.fromBytes(bytes);
		System.out.println(test2);
		
		assertFalse(Objects.deepEquals(service, test2));
	}

	@Test
	public void proxyWithLambdaTest() throws Exception {
		// given
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Original.class);
		enhancer.setCallback((FixedValue) () -> "Hello Tom!");
		Original proxy = (Original) enhancer.create();

		// when
		String res = proxy.setState(null);

		// then
		assertEquals("Hello Tom!", res);
		
		byte[] bytes = FLAKES.toBytes(proxy);
		Original test2 = FLAKES.fromBytes(bytes);
		System.out.println(test2);
		assertFalse(Objects.deepEquals(proxy, test2));
	}

	@Test
	public void proxyWithLambdaTest2() throws Exception {
		// given
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Original.class);
		enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
			if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
				return "Hello Tom!";
			} else {
				return proxy.invokeSuper(obj, args);
			}
		});

		// when
		Original proxy = (Original) enhancer.create();
		
		Original test2 = FLAKES.deepCopy(proxy);

		// then
		assertEquals("Hello Tom!", proxy.setState(null));
		assertEquals("Hello Tom!", test2.setState(null));
		assertFalse(Objects.deepEquals(proxy, test2));
	}

}
