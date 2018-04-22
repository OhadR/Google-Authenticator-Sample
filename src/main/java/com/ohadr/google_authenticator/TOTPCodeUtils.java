package com.ohadr.google_authenticator;

import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.ietf.tools.TOTP;

public class TOTPCodeUtils {

	public static String getRandomSecretKey() {
	    SecureRandom random = new SecureRandom();
	    byte[] bytes = new byte[20];
	    random.nextBytes(bytes);
	    Base32 base32 = new Base32();
	    String secretKey = base32.encodeToString(bytes);
	    // make the secret key more human-readable by lower-casing and
	    // inserting spaces between each group of 4 characters
	    return secretKey.toLowerCase().replaceAll("(.{4})(?=.{4})", "$1 ");
	}

	public static String getTOTPCode(String secretKey) {
	    String normalizedBase32Key = secretKey.replace(" ", "").toUpperCase();
	    Base32 base32 = new Base32();
	    byte[] bytes = base32.decode(normalizedBase32Key);
	    String hexKey = Hex.encodeHexString(bytes);
	    long time = (System.currentTimeMillis() / 1000) / 30;
	    String hexTime = Long.toHexString(time);
	    return TOTP.generateTOTP(hexKey, hexTime, "6");
	}
	
	public static void testTOTPCode()
	{
		String secretKey = "quu6 ea2g horg md22 sn2y ku6v kisc kyag"; //getRandomSecretKey()
		String lastCode = null;
		while (true) 
		{
		    String code = getTOTPCode(secretKey);
		    if (!code.equals(lastCode)) {
		        // output a new 6 digit code
		        System.out.println(code);
		    }
		    lastCode = code;
		    try {
		        Thread.sleep(1000);
		    } catch (InterruptedException e) {};
		}
	}
	
	public static void main(String [] args)
	{
		testTOTPCode();
		
	}
}
