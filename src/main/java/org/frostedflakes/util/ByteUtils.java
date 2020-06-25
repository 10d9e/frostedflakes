package org.frostedflakes.util;

import java.nio.ByteBuffer;

public final class ByteUtils {

	public static byte[] toByteArray(byte value) {
		return ByteBuffer.allocate(1).put(value).array();
	}

	public static byte byteFromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).get();
	}

	public static byte[] toByteArray(short value) {
		return ByteBuffer.allocate(2).putShort(value).array();
	}

	public static short shortFromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getShort();
	}

	public static byte[] toByteArray(int value) {
		return ByteBuffer.allocate(4).putInt(value).array();
	}

	public static int intFromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getInt();
	}

	public static byte[] toByteArray(long value) {
		return ByteBuffer.allocate(8).putLong(value).array();
	}

	public static long longFromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getLong();
	}

	public static byte[] toByteArray(float value) {
		return ByteBuffer.allocate(4).putFloat(value).array();
	}

	public static float floatFromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getFloat();
	}

	public static byte[] toByteArray(double value) {
		return ByteBuffer.allocate(8).putDouble(value).array();
	}

	public static double doubleFromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getDouble();
	}

	public static byte[] toByteArray(char value) {
		return ByteBuffer.allocate(2).putChar(value).array();
	}

	public static char charFromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getChar();
	}

	public static byte[] toByteArray(boolean value) {
		byte vOut = (byte) (value ? 1 : 0);
		return ByteBuffer.allocate(1).put(vOut).array();
	}

	public static boolean booleanFromByteArray(byte[] bytes) {
		byte b = ByteBuffer.wrap(bytes).get();
		return b != 0;
	}

}
