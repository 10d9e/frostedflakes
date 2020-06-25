package org.frostedflakes.test.primitives;

import java.util.Arrays;

public class Primitives {
	
	public int[] integers = { 1, 2, 3 };
	
	public int i = 42;

	public short[] shorts = { 1, 2, 3 };
	
	public short s = 42;
	
	public double[] doubles = { 1, 2, 3 };
	
	public double d = 42;
	
	public long[] longs = { 1, 2, 3 };
	
	public long l = 42;
	
	public float[] floats = { 1, 2, 3 };
	
	public float f = 42; 
	
	public char[] chars = { 1, 2, 3 };
	
	public char c = 42;
	
	public boolean [] booleans = { true, false, true };
	
	public boolean b = true;
	
	public byte[] bytes = { 1, 2, 3 };
	
	public byte by = 42;

	@Override
	public String toString() {
		return "Primitives [integers=" + Arrays.toString(integers) + ", i=" + i + ", shorts=" + Arrays.toString(shorts)
				+ ", s=" + s + ", doubles=" + Arrays.toString(doubles) + ", d=" + d + ", longs="
				+ Arrays.toString(longs) + ", l=" + l + ", floats=" + Arrays.toString(floats) + ", f=" + f + ", chars="
				+ Arrays.toString(chars) + ", c=" + c + ", booleans=" + Arrays.toString(booleans) + ", b=" + b
				+ ", bytes=" + Arrays.toString(bytes) + ", by=" + by + "]";
	}
	
	
	
}