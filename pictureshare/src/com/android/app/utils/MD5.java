/**
 * 
 */
package com.android.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * <p>
 * MD5 算法的Java Bean
 * </p>
 * 
 * @author chenshaorong
 * @version 1.0.0
 */
public class MD5 {

	/** 消息摘要对象 */
	private static MessageDigest messageDigest;

	/** 字符集 */
	private static String charsert = "UTF-8";

	/**
	 * 获取md5值
	 * 
	 * @param str
	 *            要加密的字符串
	 * @return
	 */
	public String getMD5ofStr(String str) {
		try {
			/* 获取md5实例 */
			messageDigest = MessageDigest.getInstance("MD5");

			/* 重置摘要以供再次使用 */
			messageDigest.reset();

			/* 使用指定的字节数组更新摘要 */
			messageDigest.update(str.getBytes(charsert));

			/* 通过执行诸如填充之类的最终操作完成哈希计算 */
			byte[] byteArray = messageDigest.digest();

			/* 字节编码buffer */
			StringBuilder md5StrBuff = new StringBuilder();

			/* 循环字节数组 */
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
					md5StrBuff.append("0").append(
							Integer.toHexString(0xFF & byteArray[i]));
				} else {
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
				}
			}
			// 返回大写的MD5值
			return md5StrBuff.toString().toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取md5值
	 * 
	 * @param file
	 *            文件对象
	 * @return
	 */
	public static String getMD5ofFile(File file) {
		try {
			/* 获取md5实例 */
			messageDigest = MessageDigest.getInstance("MD5");

			/* 重置摘要以供再次使用 */
			messageDigest.reset();

			/* 使用指定的字节数组更新摘要 */
			int len;
			byte buffer[] = new byte[1024];
			FileInputStream in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				messageDigest.update(buffer, 0, len);
			}
			in.close();

			/* 通过执行诸如填充之类的最终操作完成哈希计算 */
			byte[] byteArray = messageDigest.digest();

			/* 字节编码buffer */
			StringBuilder md5StrBuff = new StringBuilder();

			/* 循环字节数组 */
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
					md5StrBuff.append("0").append(
							Integer.toHexString(0xFF & byteArray[i]));
				} else {
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
				}
			}
			// 返回大写的MD5值
			return md5StrBuff.toString().toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取md5值
	 * 
	 * @param bytes
	 *            字节数组
	 * @return
	 */
	public String getMD5ofByte(byte[] bytes) {
		try {
			/* 获取md5实例 */
			messageDigest = MessageDigest.getInstance("MD5");

			/* 重置摘要以供再次使用 */
			messageDigest.reset();

			/* 使用指定的字节数组更新摘要 */
			messageDigest.update(bytes);

			/* 通过执行诸如填充之类的最终操作完成哈希计算 */
			byte[] byteArray = messageDigest.digest();

			/* 字节编码buffer */
			StringBuilder md5StrBuff = new StringBuilder();

			/* 循环字节数组 */
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
					md5StrBuff.append("0").append(
							Integer.toHexString(0xFF & byteArray[i]));
				} else {
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
				}
			}
			// 返回大写的MD5值
			return md5StrBuff.toString().toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
