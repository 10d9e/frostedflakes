package org.frostedflakes.test.random;

import org.frostedflakes.FrostedFlakes;
import org.frostedflakes.IFrostedFlakes;

public class BytesTest {

	public static void main(String... args) throws Exception {

		Cycle test = new Cycle();

		IFrostedFlakes<Cycle> flakes = new FrostedFlakes<>();
		
		// cycle
		byte[] bytes = flakes.toBytes(test);
		Cycle cycle = flakes.fromBytes(bytes);
		System.out.println(cycle);
		
	}

}
