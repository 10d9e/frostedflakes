package org.frostedflakes.test.random;

import java.lang.reflect.Field;

public class ClassReflectionTest {
	
	public class Foo {

		private Class<?> clz;
		
		public Foo() {
			super();
		}

		public void doIt() {
			System.out.println("doIt");
		}
	}
	
	public class Bar {
		private int baz = 42;
	}
	

	public void reflect() throws Exception {
		
		//Class<?> starter = Class.forName("org.foo.ClassReflectionTest$Foo");
		//Foo instance = (Foo) starter.getDeclaredConstructor().newInstance();

		Foo instance = new Foo();
		
		Field field = Foo.class.getDeclaredField("clz");
		field.setAccessible(true);
		
		// this works fine
		field.set(instance, Integer.class);
		
		String className = "org.stacksnap.serialization.frostedflakes.Test3";
		Class<?> clazz = Class.forName(className);
		field.set(instance, clazz);

	}
	
	public static void main(String ... args) throws Exception {
		ClassReflectionTest test = new ClassReflectionTest();
		test.reflect();
	}

}
