package org.frostedflakes.test.context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

import org.frostedflakes.IFrostedFlakes;
import org.frostedflakes.classloader.ContextLoader;
import org.frostedflakes.classloader.DynamicClassLoader;
import org.frostedflakes.classloader.FrostedFlakesFactory;
import org.junit.Test;

public class ContextTest {

	@Test
	public void testFileIO() throws Exception {
		
		DynamicClassLoader classLoader = new DynamicClassLoader();
		Class<?> carClass = classLoader.loadClass("org.frostedflakes.FrostedFlakes");
		IFrostedFlakes someCar = (IFrostedFlakes) carClass.newInstance(); // Now more useful
		Object result = someCar.deepCopy(new A("test"));
		System.out.println(result); // Honk honk!

		
		//FrostedFlakes<A> FLAKES = (FrostedFlakes<A>) ContextLoader.getFlakes();
		
		//IFrostedFlakes<A> FLAKES = ContextLoader.proxyInstance();
		
		//A cloned = FLAKES.deepCopy(new A("Test"));
		
		//System.out.println(cloned);
		
		/*
		ContextLoader.invoke( (IFrostedFlakes<A> f) -> {
			A a = new A("test");
			A aa = f.deepCopy(a);
		});
		
		
		
		IFrostedFlakes<A> FLAKES = (IFrostedFlakes<A>) ContextLoader.proxyInstance();
		A cloned = FLAKES.deepCopy(new A("Test"));
		System.out.println(cloned);
		*/
		// Checking the equality

		//FLAKES.save(a, "a.bin");

		//A a1 = FLAKES.open("a.bin");

		// then
		//TestUtilities.testCollectionEquality(a1.list, a.list);

		//assertFalse(Objects.deepEquals(a, a1));

	}

}