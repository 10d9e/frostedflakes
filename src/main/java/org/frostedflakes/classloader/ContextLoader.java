package org.frostedflakes.classloader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.frostedflakes.IFrostedFlakes;

public class ContextLoader {
	
	



	public static DynamicClassLoader dcl = new DynamicClassLoader();

	// private static Class<?> contextClass;

	// private static Object instance;

	/*
	public static <T> void invokeWithConsumer(Consumer<IFrostedFlakes<T>> func) {
		try {
			Class<?> contextClass = dcl.load("org.frostedflakes.classloader.Context");
			Object instance = contextClass.getDeclaredConstructor().newInstance(); // contextClass.newInstance();
			contextClass.getMethod("init", Consumer.class).invoke(instance, func);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	public static void invoke(Consumer<Object> p) {
		try {
			Class<?> contextClass = dcl.load("org.frostedflakes.classloader.Context");
			Object instance = contextClass.getDeclaredConstructor().newInstance(); // contextClass.newInstance();			
			Method method = contextClass.getDeclaredMethod("init", Consumer.class);
			method.invoke(instance, p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static Object proxyInstance() throws Exception {
		
		Class<?> interfaceClass = dcl.load("org.frostedflakes.IFrostedFlakes");
		
		Class<?> contextClass = dcl.load("org.frostedflakes.FrostedFlakes");
		final Object instance = contextClass.getDeclaredConstructor().newInstance();
		
		return Proxy.newProxyInstance(dcl, new Class[] { interfaceClass }, new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				// TODO Auto-generated method stub
				return method.invoke(instance, args);
			}
		});
	}

}
