package org.frostedflakes.test.random;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;

public class CycleTest {

	public static void main(String... args) throws Exception {

		Cycle test = new Cycle();
		IFrostedFlakes<Cycle> flakes = new FrostedFlakes<>();
		Cycle t2 = flakes.deepCopy(test);
		System.out.println(t2);
	}

}
