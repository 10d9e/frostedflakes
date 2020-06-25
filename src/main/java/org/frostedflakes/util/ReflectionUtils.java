package org.frostedflakes.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public final class ReflectionUtils {

	public static Class<?> box(Class<?> target) {
		if (int.class.equals(target)) {
			return Integer.class;
		} else if (boolean.class.equals(target)) {
			return Boolean.class;
		} else if (short.class.equals(target)) {
			return Short.class;
		} else if (double.class.equals(target)) {
			return Double.class;
		} else if (long.class.equals(target)) {
			return Long.class;
		} else if (float.class.equals(target)) {
			return Float.class;
		} else if (byte.class.equals(target)) {
			return Byte.class;
		} else if (char.class.equals(target)) {
			return Character.class;
		} else if (void.class.equals(target)) {
			return Void.class;
		}
		return target;
	}

	public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
		if (type == null) {
			return fields;
		}
		for (Field f : type.getDeclaredFields()) {
			
			if (!(Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()))) {
				fields.add(f);
			}
			//fields.add(f);
		}
		if (type.getSuperclass() != null) {
			getAllFields(fields, type.getSuperclass());
		}
		return fields;
	}

	public static boolean allowed(String classname) {
		return checkClass(classname);
	}

	public static boolean allowed(Class<?> clazz) {
		String n = clazz.getCanonicalName() == null ? clazz.getName() : clazz.getCanonicalName();
		return checkClass(n);
	}

	public static boolean allowed(Object o) {
		return allowed(o.getClass());
	}

	private static boolean checkClass(String n) {
		
		if(n.startsWith("org.stacksnap.org.eclipse.jetty")) {
			return false;
		}
		
		if (n.contains("$$Lambda$") || n.contains("$$EnhancerBySpringCGLIB$$")) {
			return false;
		}
		if (n.startsWith("jdk.internal")) {
			return false;
		}
		return true;
	}
}
