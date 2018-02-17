package com.mycafeteria.utility;

import java.security.SecureRandom;
import java.util.Random;

public class Utility {

	public static String randomString(int length) {
		char[] CHARSET_AZ_09 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
				.toCharArray();
		Random random = new SecureRandom();
		char[] result = new char[length];
		for (int i = 0; i < result.length; i++) {
			// picks a random index out of character set > random character
			int randomCharIndex = random.nextInt(CHARSET_AZ_09.length);
			result[i] = CHARSET_AZ_09[randomCharIndex];
		}
		return new String(result);
	}
	
}
