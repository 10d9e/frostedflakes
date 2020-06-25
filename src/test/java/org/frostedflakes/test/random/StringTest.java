package org.frostedflakes.test.random;

public class StringTest {
	
	public static void main(String ...strings) {
		byte[] bytes = new byte[] {85, 84, 70, 95, 49, 54, 76, 69};
		
		String s = new String(bytes);
		System.out.println(s);
	}

}
