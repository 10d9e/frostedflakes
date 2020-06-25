package org.frostedflakes.serialization;

import static org.frostedflakes.util.ByteUtils.toByteArray;
import static org.frostedflakes.util.ReflectionUtils.allowed;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;
import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.processor.TypeProcessor;
import org.frostedflakes.processor.TypeProcessorRegistry;
import org.frostedflakes.types.ArrayType;
import org.frostedflakes.types.ClassType;
import org.frostedflakes.types.EnumType;
import org.frostedflakes.types.JavaProxyType;
import org.frostedflakes.types.ObjectType;
import org.frostedflakes.types.PrimitiveType;
import org.frostedflakes.types.ReferenceType;
import org.frostedflakes.types.ReflectedType;
import org.frostedflakes.types.SerializableType;
import org.frostedflakes.util.ReflectionUtils;

public class Serializer<T> {
	
	
	private Map<String, JavaProxyType<?>> proxyClasses = new HashMap<>();

	private Map<Integer, ReferenceType<?>> references = new HashMap<>();

	@SuppressWarnings("unchecked")
	public ReflectedType<T> serialize(Object target) {
		try {
			references = new HashMap<>();
			return (ReflectedType<T>) toReflected(target);
		} finally {
			references.clear();
			references = null;
		}
	}

	@SuppressWarnings("unchecked")
	private ReflectedType<?> extract(Object value) {

		if (value == null || !allowed(value)) {
			return null;
		}

		ReflectedType<?> rValue = null;

		boolean isPrimitiveOrWrapped = value.getClass().isPrimitive()
				|| ClassUtils.wrapperToPrimitive(value.getClass()) != null;

		if (value.getClass().isArray()) {
			Class<?> componentType = value.getClass().getComponentType();
			ArrayType<?> arr = new ArrayType<>(componentType);
			int length = Array.getLength(value);
			for (int i = 0; i < length; i++) {
				Object item = Array.get(value, i);
				ReflectedType<T> r = (ReflectedType<T>) extract(item);
				arr.add(r);
			}
			return arr;
		} else if (isPrimitiveOrWrapped) {
			if (value.getClass().equals(Integer.class)) {
				rValue = new PrimitiveType<Integer>(toByteArray((Integer) value), Integer.class);
			} else if (value instanceof Byte) {
				rValue = new PrimitiveType<Byte>(toByteArray((Byte) value), Byte.class);
			} else if (value instanceof Short) {
				rValue = new PrimitiveType<Short>(toByteArray((Short) value), Short.class);
			} else if (value instanceof Long) {
				rValue = new PrimitiveType<Long>(toByteArray((Long) value), Long.class);
			} else if (value instanceof Float) {
				rValue = new PrimitiveType<Float>(toByteArray((Float) value), Float.class);
			} else if (value instanceof Double) {
				rValue = new PrimitiveType<Double>(toByteArray((Double) value), Double.class);
			} else if (value instanceof Boolean) {
				rValue = new PrimitiveType<Boolean>(toByteArray((Boolean) value), Boolean.class);
			} else if (value instanceof Character) {
				rValue = new PrimitiveType<Character>(toByteArray((Character) value), Character.class);
			}
		} else if (value instanceof String) {
			String ss = (String) value;
			try {
				String s = new String(ss.getBytes(), "UTF8");
				rValue = new PrimitiveType<String>(s.getBytes(), String.class);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if (value instanceof Enum) {
			Enum<?> s = (Enum<?>) value;
			rValue = new EnumType<>(s.name(), s.ordinal(), s.getDeclaringClass());
		} else if (value instanceof Class) {
			Class<?> typeValue = ((Class<?>) value);
			if (ReflectionUtils.allowed(typeValue)) {
				if (typeValue.isPrimitive()) {
					typeValue = ReflectionUtils.box(typeValue);
				}
				if(typeValue.getName().startsWith("com.sun.proxy.$Proxy81") ||
						typeValue.getName().startsWith("com.sun.proxy.$Proxy78")) {
					System.out.println("ref: " + typeValue.getName());
					
				}
				rValue = new ClassType<>(value.getClass(), typeValue.getName());
			}

		} else if (Proxy.isProxyClass(value.getClass())) {
			
			int identifier = id(value);
			if (references.containsKey(identifier)) {
				rValue = references.get(identifier);
			} else {
				InvocationHandler handler = Proxy.getInvocationHandler(value);
				ReflectedType<? extends InvocationHandler> reflectedHandler = (ReflectedType<? extends InvocationHandler>) toReflected(
						handler);
				reference(value);
				rValue = new JavaProxyType<>(value.getClass(), identifier, reflectedHandler, value.getClass().getInterfaces());
				
				proxyClasses.put(value.getClass().getName(), (JavaProxyType<?>) rValue);
				
				if(value.getClass().getName().startsWith("com.sun.proxy.$Proxy81") ||
						value.getClass().getName().startsWith("com.sun.proxy.$Proxy78")) {
					System.out.println("+++ " + value.getClass().getName());
					System.out.println("    " + value);
				}
			}
			
			
		} else {

			int identifier = id(value);
			if (references.containsKey(identifier)) {
				rValue = references.get(identifier);
			} else {
				rValue = toReflected(value);
			}
		}

		return (ReflectedType<?>) rValue;

	}

	private int id(Object target) {
		// this is a problem with zero sized HashMap, which hashes to 0

		int thc = 0;
		int hchc = 0;

		try {
			thc = target.hashCode();

		} catch (Throwable t) {
			System.err.println("Warning: Error getting hashCode from target: " + target);
		}

		try {
			hchc = target.getClass().hashCode();

		} catch (Throwable t) {
			System.err.println("Warning: Error getting Class hashCode from target: " + target);
		}

		return thc + hchc;
		// return target.getClass().getName().hashCode();
	}

	private ReferenceType<?> reference(Object target) {
		int identifier = id(target);
		ReferenceType<?> r = new ReferenceType<>(target.getClass(), identifier);
		this.references.put(identifier, r);
		return r;
	}

	private ReferenceType<?> reference(Object target, Class<?> type) {
		int identifier = id(target);
		ReferenceType<?> r = new ReferenceType<>(type, identifier);
		this.references.put(identifier, r);
		return r;
	}

	private ReflectedType<?> toReflected(Object target) {
		if (target == null) {
			return null;
		}
		for (TypeProcessor p : TypeProcessorRegistry.getProcessors()) {
			if (p.ignore(target.getClass())) {
				continue;
			}
			return (ReflectedType<?>) p.serialize(target, this);
		}
		return toReflected(target, null);
	}

	@SuppressWarnings("unchecked")
	public ReflectedType<?> toReflected(Object target, Class<?> type) {

		try {

			if (target == null) {
				return null;
			}

			Class<?> clazz = target.getClass();
			if (type != null) {
				reference(target, type);
				clazz = type;
			} else {
				reference(target);
			}
			
			//System.out.println(target.getClass().getName());

			if (clazz.isArray()) {
				Class<?> componentType = clazz.getComponentType();
				ArrayType<?> arr = new ArrayType<>(componentType);
				int length = Array.getLength(target);
				for (int i = 0; i < length; i++) {
					Object item = Array.get(target, i);
					ReflectedType<T> r = (ReflectedType<T>) toReflected(item);
					arr.add(r);
				}
				return arr;
			}

			// if it's serializable
			//if(clazz.isAssignableFrom(Serializable.class)) {
			if(FrostedFlakes.USE_SERIALIZABLE && target instanceof Serializable) {
				return new SerializableType<>(target);
			}
			
			ObjectType<?> reflectedObject = new ObjectType<>(clazz, id(target));
			List<Field> classFields = ReflectionUtils.getAllFields(new LinkedList<Field>(), clazz);

			for (Field f : classFields) {
				if (f.trySetAccessible()) {
					Object value = f.get(target);
					ReflectedType<?> t = extract(value);
					reflectedObject.addField(t);
				}
			}

			return reflectedObject;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
