package com.zxw.util; 

import java.util.Collection;
import java.util.Map;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：郑雄伟<br/>
 *		    E-mail ：zhengxiongwei@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2017年7月5日 下午5:01:19 
 */
public class Assert {
	public static void notNull(Object object) {
		notNull(object, "参数不能为空!");
	}

	public static void notNull(Object object, String message) {
		if (object == null)
			throw new IllegalArgumentException(message);
	}

	public static void notEmpty(Collection<?> collection, String message) {
		if ((collection == null) || (collection.isEmpty()))
			throw new IllegalArgumentException(message);
	}

	public static void notEmpty(Map<?, ?> map, String message) {
		if ((map == null) || (map.isEmpty()))
			throw new IllegalArgumentException(message);
	}

	public static void hasLength(String text, String message) {
		if ((text == null) || (text.length() == 0))
			throw new IllegalArgumentException(message);
	}

	public static void isNotBlank(String text, String message) {
		if ((text == null) || (text.trim().length() == 0))
			throw new IllegalArgumentException(message);
	}

	public static <T> void notEmpty(T[] array, String message) {
		if ((array == null) || (array.length == 0))
			throw new IllegalArgumentException(message);
	}

	public static void notEmpty(int[] array, String message) {
		if ((array == null) || (array.length == 0))
			throw new IllegalArgumentException(message);
	}

	public static void notEmpty(long[] array, String message) {
		if ((array == null) || (array.length == 0))
			throw new IllegalArgumentException(message);
	}

	public static void notEmpty(float[] array, String message) {
		if ((array == null) || (array.length == 0))
			throw new IllegalArgumentException(message);
	}

	public static void notEmpty(double[] array, String message) {
		if ((array == null) || (array.length == 0))
			throw new IllegalArgumentException(message);
	}

	public static void notEmpty(short[] array, String message) {
		if ((array == null) || (array.length == 0))
			throw new IllegalArgumentException(message);
	}

	public static void notEmpty(char[] array, String message) {
		if ((array == null) || (array.length == 0))
			throw new IllegalArgumentException(message);
	}

	public static void notEmpty(byte[] array, String message) {
		if ((array == null) || (array.length == 0))
			throw new IllegalArgumentException(message);
	}

	public static void notEmpty(boolean[] array, String message) {
		if ((array == null) || (array.length == 0))
			throw new IllegalArgumentException(message);
	}

	public static void isTrue(boolean condition, String message) {
		if (!(condition))
			throw new IllegalArgumentException(message);
	}

	public static void isNotTrue(boolean condition, String message) {
		if (condition)
			throw new IllegalArgumentException(message);
	}
}
 