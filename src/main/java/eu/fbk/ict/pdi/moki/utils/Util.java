package eu.fbk.ict.pdi.moki.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

	public static String hash(String plaintext) {
		String hashed;
		
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(plaintext.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			hashed = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hashed.length() < 32 ){
				hashed = "0"+hashed;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
		return hashed;
	}
	
	public static Boolean commonInArrays (String[] arr1, String[] arr2) {
		
		for(String s1: arr1) {
			for(String s2: arr2) {
				if(s1.equals(s2))
					return true;
			}
		}
		
		return false;
	}
	
}
