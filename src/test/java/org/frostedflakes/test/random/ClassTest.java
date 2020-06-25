package org.frostedflakes.test.random;

import java.lang.reflect.Method;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;
import org.frostedflakes.types.ObjectType;

public class ClassTest {	
	

	public static void main(String... args) throws Exception {

		Cycle test = new Cycle();

		Method m = test.getClass().getDeclaredMethod("doIt", new Class<?>[] { Long.class });

		Snapshot snapshot = new Snapshot(Thread.currentThread().getId(), Entrance.ENTER, test, m, new Object[] {}, new Exception("Fna"));

		IFrostedFlakes<Cycle> flakes = new FrostedFlakes<>();
		
		byte[] bytes =  flakes.toBytes(test);

		System.out.println(bytes);
		Cycle t2 = flakes.fromBytes(bytes);
		System.out.println(t2);
	}

}
