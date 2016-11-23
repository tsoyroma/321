package ru.croc.sbrf.adapters.mdm.java;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

	public static String getHash(byte[]value) {
		
		if (value == null) {
			return null;
		}
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[]byteHash = digest.digest(value);
			return convertToString(byteHash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final char[] DIGIT = {'0', '1', '2', '3', '4', '5'
		, '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	private static String convertToString(byte[]value) {
		StringBuilder builder = new StringBuilder();
		for (byte b : value) {
			builder.append(DIGIT[(b & 0xf0) >> 4]).append(DIGIT[b & 0xf]);
		}
		return builder.toString();
	}
}
