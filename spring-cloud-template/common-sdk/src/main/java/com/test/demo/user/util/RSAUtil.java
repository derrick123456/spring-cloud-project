/**
 * @Description :
 * @author :
 * @date :2019年8月22日
 */
package com.test.demo.user.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: RSAUtil.java </p> 
 * <p>类型描述: [RSA工具包] </p>
 */
@Slf4j
public class RSAUtil {

	/**
	 * <p>功能描述: [加密] </p>
	 * @Title getCode
	 * @param original .
	 * @param commKey .
	 * @return String
	 */
	public static String getCode(String original, String commKey) {
		byte[] decoded = Base64.decodeBase64(commKey);
		RSAPublicKey pubKey;
		String outStr = "";
		try {
			pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			outStr = Base64.encodeBase64String(cipher.doFinal(original.getBytes("UTF-8")));
		} catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			log.error("", e);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			log.error("", e);
		} catch (NoSuchPaddingException e) {
			log.error("", e);
		} catch (InvalidKeyException e) {
			log.error("", e);
		}
		return outStr;
	}

	/**
	 * <p>功能描述: [解密] </p>
	 * @Title getOriginal
	 * @param code .
	 * @param privaKey .
	 * @return String
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception 
	 */
	public static String getOriginal(String code, String privaKey)
			throws UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// 64位解码加密后的字符串
		byte[] inputByte = Base64.decodeBase64(code.getBytes("UTF-8"));
		// base64编码的私钥
		byte[] decoded = Base64.decodeBase64(privaKey);
		RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
				.generatePrivate(new PKCS8EncodedKeySpec(decoded));
		// RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		return new String(cipher.doFinal(inputByte));
	}

	/**
	 * <p>功能描述: [获取钥对] </p>
	 * @Title getCommAndPrivaKey
	 * @return Map .
	 */
	public static Map<Integer, String> getCommAndPrivaKey() {
		Map<Integer, String> keyMap = new HashMap<Integer, String>();
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
			// 初始化密钥对生成器，密钥大小为96-1024位
			keyPairGen.initialize(1024, new SecureRandom());
			// 生成一个密钥对，保存在keyPair中
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 私钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 公钥
			String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
			// 得到私钥字符串
			String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
			// 将公钥和私钥保存到Map
			keyMap.put(0, publicKeyString); // 0表示公钥
			keyMap.put(1, privateKeyString); // 1表示私钥
		} catch (NoSuchAlgorithmException e) {
			log.error("", e);
		}
		return keyMap;
	}
}
