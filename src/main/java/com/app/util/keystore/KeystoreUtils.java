package com.app.util.keystore;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;

public class KeystoreUtils {

	// keytool -genkey -validity 36000 -alias ctc-partner-prod -keyalg RSA
	// -keysize 2048 -sigalg MD5withRSA -keystore test.keystore
	// 秘钥库口令：43hDSt5hewr

	// 别名：ctc-partner-prod
	// 别名口令：43hDSt5hewr

	// keystore 私钥加密
	public static String encryptAndSignApiRequest(String data) throws Exception {

		// 获得密钥库
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(new FileInputStream("D:\\成茂涛-\\test.keystore"), "43hDSt5hewr".toCharArray());

		// 根据密钥库获得私钥
		PrivateKey key = (PrivateKey) keystore.getKey("ctc-partner-prod-pex", "43hDSt5hewr".toCharArray());

		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(key);
		signature.update(data.getBytes("UTF-8"));
		String printBase64Binary = DatatypeConverter.printBase64Binary(signature.sign());
		return printBase64Binary;

	}

	// keystore 公钥解密
	public static void verifySignature(String signData, String signature) throws Exception {
		// 获得密钥库
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(new FileInputStream("D:\\成茂涛-\\test.keystore"), "43hDSt5hewr".toCharArray());

		// 根据密钥库获得证书
		Certificate cert = keystore.getCertificate("ctc-partner-prod-pex");

		// 根据证书获得公钥
		PublicKey publicKey = cert.getPublicKey();

		Signature sign = Signature.getInstance("MD5withRSA");
		sign.initVerify(publicKey);
		sign.update(signData.getBytes("UTF-8"));

		boolean verify = sign.verify(DatatypeConverter.parseBase64Binary(signature));
		System.out.println(verify);
	}

	// 获取公钥字符串
	public static String getPublicKey() throws Exception {
		// 获得密钥库
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(new FileInputStream("D:\\成茂涛-\\test.keystore"), "43hDSt5hewr".toCharArray());

		// 根据密钥库获得证书
		Certificate cert = keystore.getCertificate("ctc-partner-prod-pex");

		// 根据证书获得公钥
		PublicKey publicKey = cert.getPublicKey();

		String strKey = Base64.encodeBase64String(publicKey.getEncoded());
		System.out.println("公钥字符串：" + strKey);
		return strKey;
	}

	// 根据公钥字符串获得公钥对象
	public static PublicKey getPublicKeyByStr(String pubKey) throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(pubKey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	// 获取私钥字符串
	public static String getPrivateKey() throws Exception {
		// 获得密钥库
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(new FileInputStream("D:\\成茂涛-\\test.keystore"), "43hDSt5hewr".toCharArray());

		// 根据密钥库获得私钥
		PrivateKey key = (PrivateKey) keystore.getKey("ctc-partner-prod-pex", "43hDSt5hewr".toCharArray());

		String strKey = Base64.encodeBase64String(key.getEncoded());
		return strKey;
	}

	// 根据私钥字符串获得私钥对象
	public static PrivateKey getPrivateKeyByStr(String priKey) throws Exception {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(priKey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static void jiemisanhu(String pubKey, String srcData, String signature) throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(pubKey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PublicKey publicKey = keyFactory.generatePublic(keySpec);

		Signature sign = Signature.getInstance("MD5withRSA");
		sign.initVerify(publicKey);
		sign.update(srcData.getBytes("UTF-8"));
		boolean verify = sign.verify(DatatypeConverter.parseBase64Binary(signature));
		System.out.println(verify);
	}

	public static void main(String[] args) throws Exception {

		/*
		 * String srcData = "wangx"; String jiami =
		 * encryptAndSignApiRequest(srcData); verifySignature(srcData, jiami);
		 * 
		 * String publicKey = getPublicKey(); PublicKey publicKeyByStr =
		 * getPublicKeyByStr(publicKey);
		 * 
		 * String privateKey = getPrivateKey(); PrivateKey privateKeyByStr =
		 * getPrivateKeyByStr(privateKey);
		 * 
		 * Signature signature = Signature.getInstance("MD5withRSA");
		 * signature.initSign(privateKeyByStr);
		 * signature.update(srcData.getBytes("UTF-8")); String printBase64Binary
		 * = DatatypeConverter.printBase64Binary(signature.sign());
		 * System.out.println("---------");
		 * System.out.println(StringUtils.equals(jiami, printBase64Binary));
		 */

		//String packet = "8s8KcP/87xmBXjzAYV8afpVM+bRsWDpDSeUpmdZuwqNHEIerUOW2TloCsT2/sSUeaoxVVY9E0cop6kEr9lQtxUS43iT7xCVDwxNbP/8rNSQ=";
		String packet = "VtZ43Qffj6kiTV5vYkCVGSGgpni17MP5Is7HMAC747UbyqHV4vsxWBjNh5ak4nKpB8JCMKEs+CXU3fbyY+lK8ba4RbUcKWkvUU3Q/nvz5GkH/SEogc3Sr0oia2xg7eDyE3M4np009EMd2Z91Je+TMAyQNIwTrhicl8Ay2mMfs0p7YHZ27MLd5mPnTwmeOL7H5qRXtTt8Bt11iUmc7Tq+rQ==";

		byte[] dataBytes = DatatypeConverter.parseBase64Binary(packet);
		SecretKeySpec key = new SecretKeySpec("x4pykoZ7bZre5KAC".getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		String res = new String(cipher.doFinal(dataBytes), "UTF-8");
		
		System.out.print(res);
	}

}
