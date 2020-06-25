package org.frostedflakes.processor.impl;

import org.frostedflakes.processor.TypeProcessor;
import org.frostedflakes.serialization.Deserializer;
import org.frostedflakes.serialization.Serializer;
import org.frostedflakes.types.ReflectedType;

import net.sf.cglib.proxy.Enhancer;

public class CglibProxyTypeProcessor implements TypeProcessor {

	public static final String CGLIB_NAMING_MARKER = "$$EnhancerByCGLIB$$";

	@Override
	public ReflectedType<?> serialize(Object value, Serializer<?> parent) {

		if (isCglibProxy(value.getClass())) {
			Class<?> superclass = value.getClass().getSuperclass();
			if (superclass != null) {
				System.out.println("	Superclass: " + superclass.getName());

				Object o = superclass.cast(value);

				System.out.println(o);

				return parent.toReflected(o, superclass);

			}
		} else if (value.getClass().getName().startsWith("net.sf.cglib.proxy.Enhancer")) {

			System.out.println(value.getClass());
			System.out.println(value);

		} else if (value.getClass().getName().contains("$$Lambda$")) {
			Class<?> superclass = value.getClass().getSuperclass();
			if (superclass != null) {
				System.out.println("	Superclass: " + superclass.getName());
			}

			System.err.println("Ignoring " + value.getClass().getName());

		} else if (value instanceof net.sf.cglib.proxy.Enhancer) {
			System.out.println("ENHANCER FOUND!");
		}

		return null;
	}

	boolean isCglibProxy(final Class<?> cls) {
		try {
			return Enhancer.isEnhanced(cls) && cls.getName().contains(CGLIB_NAMING_MARKER);
		} catch (Throwable e) {
			System.err.println("Error in isCglibProxy: " + e.getMessage());
			return false;
		}
	}

	@Override
	public Object deserialize(ReflectedType<?> type, Deserializer<?> deserializer) {
		if (type == null)
			return null;

		if (isCglibProxy(type.getType())) {
			System.out.println("Got CGLIB");
		}

		return null;
	}

	@Override
	public boolean ignore(Class<?> clazz) {
		if (isCglibProxy(clazz)) {
			Class<?> superclass = clazz.getSuperclass();
			if (superclass != null) {
				return false;

			}
		} else if (clazz.getName().startsWith("net.sf.cglib.proxy.Enhancer")) {

			System.out.println(clazz);
			return false;

		} else if (clazz.getName().contains("$$Lambda$")) {
			Class<?> superclass = clazz.getSuperclass();
			if (superclass != null) {
				System.out.println("	Superclass: " + superclass.getName());
			}

			System.err.println("Ignoring " + clazz.getName());
			return true;

		} else if (clazz.equals(net.sf.cglib.proxy.Enhancer.class)) {
			System.out.println("ENHANCER FOUND!");
		}
		return true;
	}

}
