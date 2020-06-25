package org.frostedflakes.serialization;

import static org.frostedflakes.util.ByteUtils.booleanFromByteArray;
import static org.frostedflakes.util.ByteUtils.byteFromByteArray;
import static org.frostedflakes.util.ByteUtils.charFromByteArray;
import static org.frostedflakes.util.ByteUtils.doubleFromByteArray;
import static org.frostedflakes.util.ByteUtils.floatFromByteArray;
import static org.frostedflakes.util.ByteUtils.intFromByteArray;
import static org.frostedflakes.util.ByteUtils.longFromByteArray;
import static org.frostedflakes.util.ByteUtils.shortFromByteArray;
import static org.frostedflakes.util.ReflectionUtils.allowed;
import static org.frostedflakes.util.ReflectionUtils.getAllFields;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
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
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisHelper;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
import org.powermock.reflect.Whitebox;

public class Deserializer<T> {

	private Objenesis objenesis = new ObjenesisStd();

	private Map<Integer, Object> context = new HashMap<>();

	@SuppressWarnings("unchecked")
	public T deserialize(ReflectedType<T> target) throws Exception {
		try {
			context = new HashMap<>();
			return (T) fromReflected(target);
		} finally {
			context.clear();
			context = null;
		}
	}

	@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
	private T extract(ReflectedType<?> value) throws Exception {

		if (value == null) {
			return null;
		}
		Class<?> type = value.getType();

		Object rValue = null;
		if (value instanceof ArrayType) {
			ArrayType<?> arr = (ArrayType<?>) value;
			/*
			if(arr.getType().isAssignableFrom( java.util.Map.Entry.class)  ) {
				System.out.println("Map.entry");
			}
			*/
			Object array = Array.newInstance(arr.getType(), arr.getItems().size());
			int j = 0;
			for (ReflectedType<?> r : arr.getItems()) {
				Object o = null;
				if (r != null) {
					o = extract(r);
				}
				Array.set(array, j, o);
				j++;
			}
			rValue = array;

		} else if (value instanceof PrimitiveType) {
			byte[] bytes = ((PrimitiveType<?>) value).getBytes();
			if (type.equals(Integer.class) || type.equals(int.class)) {
				rValue = intFromByteArray(bytes);
			} else if (type.equals(Byte.class) || type.equals(byte.class)) {
				rValue = byteFromByteArray(bytes);
			} else if (type.equals(Short.class) || type.equals(short.class)) {
				rValue = shortFromByteArray(bytes);
			} else if (type.equals(Long.class) || type.equals(long.class)) {
				rValue = longFromByteArray(bytes);
			} else if (type.equals(Float.class) || type.equals(float.class)) {
				rValue = floatFromByteArray(bytes);
			} else if (type.equals(Double.class) || type.equals(double.class)) {
				rValue = doubleFromByteArray(bytes);
			} else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
				rValue = booleanFromByteArray(bytes);
			} else if (type.equals(Character.class) || type.equals(char.class)) {
				rValue = charFromByteArray(bytes);
			} else if (type.equals(String.class)) {
				try {
					rValue = new String(bytes, "UTF8");// Base64.getEncoder().encodeToString(bytes);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (value instanceof EnumType) {
			EnumType<?> en = (EnumType<?>) value;
			rValue = Enum.valueOf((Class<? extends Enum>) value.getType(), en.getName());

		} else if (FrostedFlakes.USE_SERIALIZABLE && value instanceof SerializableType) {
			
			SerializableType ser = (SerializableType) value;
			ByteArrayInputStream bis = new ByteArrayInputStream(ser.getBytes());
			try (ObjectInput in = new ObjectInputStream(bis)) {
				rValue = in.readObject();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		} else if (value instanceof JavaProxyType) {
			JavaProxyType<?> proxy = (JavaProxyType<?>) value;
			ReflectedType<? extends InvocationHandler> reflectedHandler = proxy.getReflectedHandler();
			InvocationHandler handler = (InvocationHandler) fromReflected(reflectedHandler);
			ClassLoader cl = this.getClass().getClassLoader();// getClassLoader(this.getClass());
			try {
				rValue = Proxy.newProxyInstance(cl, proxy.getInterfaces(), handler);
				
				context.put(proxy.getIdentifier(), rValue);
				System.out.println("+++ " + proxy.getType().getName());
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
		} else if (value instanceof ClassType) {
			ClassType<?> cls = (ClassType<?>) value;
			try {

				if (cls.getName().startsWith("com.sun.proxy.$Proxy") || cls.getName().contains("$HibernateProxy$")) {
					System.err.println("TODO handle " + cls.getName());
					return null;
				}

				if (allowed(cls.getName())) {
					// ClassLoader cl = getClassLoader(cls.getType());
					//ClassLoader cl = this.getClass().getClassLoader();
					rValue = this.getClass().forName(cls.getName());
					// rValue = dcl.loadClass(cls.getName());
				}
				// rValue = Class.forName(cls.getName());
			} catch (ClassNotFoundException c) {
				System.err.println("ClassNotFoundException: " + c.getMessage());
				// c.printStackTrace();
				throw c;

			} catch (Exception c) {
				System.err.println("ERROR! " + c.getMessage());
				System.err.println(cls.toString());
				// HACK
				throw c;
			}
		} else if (value instanceof ReferenceType) {
			ReferenceType<?> ref = (ReferenceType<?>) value;
			return (T) context.get(ref.getReferenceId());
		} else {
			rValue = fromReflected(value);
			ObjectType<T> reflected = (ObjectType<T>) value;
			context.put(reflected.getIdentifier(), rValue);
		}
		return (T) rValue;

	}

	/*
	 * ClassLoader getClassLoader(Class<?> target) { ClassLoader cl =
	 * target.getClassLoader(); if (cl == null) { cl =
	 * Thread.currentThread().getContextClassLoader().getParent(); } return cl; }
	 */

	private Object newInstance(Class<?> clazz) {

		if (clazz.getName().contains("org.springframework.data.repository.core.support.DefaultRepositoryMetadata")) {

			Class<?> repo = org.objenesis.instantiator.util.ClassUtils.getExistingClass(
					Thread.currentThread().getContextClassLoader(), "org.springframework.data.repository.Repository");
			System.out.println("current Thread clsloader: " + repo);

			repo = org.objenesis.instantiator.util.ClassUtils.getExistingClass(Class.class.getClassLoader(),
					"org.springframework.data.repository.Repository");
			System.out.println("Class.class.getClassLoader: " + repo);

			repo = org.objenesis.instantiator.util.ClassUtils.getExistingClass(this.getClass().getClassLoader(),
					"org.springframework.data.repository.Repository");
			System.out.println("this.getClass().getClassLoader: " + repo);

			repo = org.objenesis.instantiator.util.ClassUtils.getExistingClass(Whitebox.class.getClassLoader(),
					"org.springframework.data.repository.Repository");
			System.out.println("Whitebox.class.getClassLoader: " + repo);

			repo = org.objenesis.instantiator.util.ClassUtils.getExistingClass(ObjenesisStd.class.getClassLoader(),
					"org.springframework.data.repository.Repository");
			System.out.println("ObjenesisStd.class.getClassLoader: " + repo);
		}

		return ObjenesisHelper.newInstance(clazz);

		// return Whitebox.newInstance(clazz);
	}

	@SuppressWarnings("deprecation")
	private Object newInstance2(Class<?> clazz) {

		Object instance = null;

		// ObjectInstantiator<?> instantiator = objenesis.getInstantiatorOf(clazz);
		// instance = instantiator.newInstance();

		try {
			// instance = clazz.getDeclaredConstructor().newInstance();
			instance = instantiate(clazz);

			// instance = clazz.newInstance();
		} catch (Throwable e) {

			ObjectInstantiator<?> instantiator = objenesis.getInstantiatorOf(clazz);
			try {
				instance = instantiator.newInstance();
			} catch (Throwable e1) {
				System.err.println("Error creating instance for type: " + clazz.getName());
				e.printStackTrace();
				e1.printStackTrace();
				throw e1;
			}
		}

		return instance;
	}

	private Object instantiate(Class<?> cls) throws Exception {
		Constructor<?> constr = null;
		Object instance = null;
		try {
			constr = cls.getDeclaredConstructor();
			instance = constr.newInstance();
		} catch (NoSuchMethodException e) {
			// Create instance of the given class
			constr = (Constructor<T>) cls.getConstructors()[0];
			final List<Object> params = new ArrayList<Object>();
			for (Class<?> pType : constr.getParameterTypes()) {
				params.add((pType.isPrimitive()) ? ClassUtils.primitiveToWrapper(pType).newInstance() : null);
			}

			try {

				instance = constr.newInstance(params.toArray());
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				// System.err.println(e1.getMessage());
				throw e1;
			}
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	private Object fromReflected(ReflectedType<?> target) throws Exception {
		if (target == null) {
			return null;
		}

		Class<?> clazz = target.getType();

		for (TypeProcessor p : TypeProcessorRegistry.getProcessors()) {
			if (target == null || p.ignore(clazz)) {
				continue;
			}
			return p.deserialize(target, this);
		}

		if (target == null || !allowed(target.getType())) {
			return null;
		}

		if (clazz.isPrimitive()) {
			return target;
		}
		
		if (FrostedFlakes.USE_SERIALIZABLE && target instanceof SerializableType) {
			
			SerializableType<?> ser = (SerializableType<?>) target;
			ByteArrayInputStream bis = new ByteArrayInputStream(ser.getBytes());
			try (ObjectInput in = new ObjectInputStream(bis)) {
				return in.readObject();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		}

		Object instance = newInstance(clazz);

		if (target instanceof ArrayType) {
			ArrayType<?> arr = (ArrayType<?>) target;
			int length = arr.getItems().size();
			Object array = Array.newInstance(arr.getType(), length);
			for (int i = 0; i < length; i++) {
				Array.set(array, i, fromReflected(arr.getItems().get(i)));
			}
			return array;
		}

		ObjectType<T> reflected = (ObjectType<T>) target;
		context.put(reflected.getIdentifier(), instance);

		int i = 0;
		List<Field> fields = getAllFields(new LinkedList<Field>(), clazz);
		for (Field f : fields) {
			// f.setAccessible(true);
			if (f.trySetAccessible()) {

				ReflectedType<?> value = reflected.getFields().get(i);

				String n = f.getType().getName();
				// System.out.println(n);
				if (n.contains("org.springframework.core.SerializableTypeWrapper$TypeProvider") && value != null
						&& value.getType().getName().contains("java.util.LinkedHashMap$Entry")) {
					System.out.println("got it");
				}

				Object val = extract(value);
				f.set(instance, val);

			}
			i++;
		}
		return instance;

	}

}
