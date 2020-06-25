package org.frostedflakes.types;

import java.lang.reflect.InvocationHandler;

public class JavaProxyType<T> extends ObjectType<T> {

	private static final long serialVersionUID = 1L;

	private ReflectedType<? extends InvocationHandler> reflectedHandler;

	private Class<?>[] interfaces;

	public JavaProxyType(Class<T> type, int identifier, ReflectedType<? extends InvocationHandler> reflectedHandler, Class<?>[] interfaces) {
		super(type, identifier);
		this.reflectedHandler = reflectedHandler;
		this.interfaces = interfaces;
	}

	public ReflectedType<? extends InvocationHandler> getReflectedHandler() {
		return reflectedHandler;
	}

	public void setReflectedHandler(ReflectedType<? extends InvocationHandler> reflectedHandler) {
		this.reflectedHandler = reflectedHandler;
	}

	public Class<?>[] getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Class<?>[] interfaces) {
		this.interfaces = interfaces;
	}

}