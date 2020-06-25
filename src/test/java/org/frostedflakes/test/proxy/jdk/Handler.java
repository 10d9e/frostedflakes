package org.frostedflakes.test.proxy.jdk;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Handler implements InvocationHandler {
	private final Service original;

	public Handler(Service original) {
		this.original = original;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("BEFORE");
		Object rValue = method.invoke(original, args);
		System.out.println("AFTER");
		return rValue;
	}
}