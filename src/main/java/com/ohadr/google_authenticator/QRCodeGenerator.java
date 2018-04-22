package com.ohadr.google_authenticator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class QRCodeGenerator {

	/**
	 * 
     * @param secretKey Base32 encoded secret key (may have optional whitespace)
     * @param account The user's account name. e.g. an email address or a username
     * @param issuer The organization managing this account
	 * @return Barcode URL, in the following format:
	 * otpauth://totp/{issuer}:{account}?secret={secret}&issuer={issuer}
	 * example: 
	 * otpauth://totp/Example%20Company%3Atest%40example.com?secret=QUU6EA2GHORGMD22SN2YKU6VKISCKYAG&issuer=Example%20Company
	 */
	public static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
		String normalizedBase32Key = secretKey.replace(" ", "").toUpperCase();
		try {
			return "otpauth://totp/"
					+ URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
					+ "?secret=" + URLEncoder.encode(normalizedBase32Key, "UTF-8").replace("+", "%20")
					+ "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * 
	 * @param barCodeData - the URL we get from getGoogleAuthenticatorBarCode()
	 * @param filePath
	 * @param height
	 * @param width
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void createQRCode(String barCodeData, String filePath, int height, int width)
			throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,
				width, height);
		try (FileOutputStream out = new FileOutputStream(filePath)) {
			MatrixToImageWriter.writeToStream(matrix, "png", out);
		}
	}
	
	public static void main(String [] args) throws WriterException, IOException
	{
		String secretKey = TOTPCodeUtils.getRandomSecretKey();
		String barCode = getGoogleAuthenticatorBarCode(secretKey, "ohad.redlich@cellebrote.com", "cellebrote");
		String file = "c:\\ohad\\dev\\barcode.png";
		createQRCode(barCode, file , 100, 100);
	}
}
