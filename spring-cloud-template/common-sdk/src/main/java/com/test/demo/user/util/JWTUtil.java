package com.test.demo.user.util;

import com.test.demo.user.model.TokenVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: JWTUtil.java </p> 
 * <p>类型描述: [jwt工具类] </p>
 */
@Slf4j
public class JWTUtil {

	/**
	 * <p>功能描述: [获取Token] </p>
	 * @Title getToken
	 * @param uid 用户ID
	 * @param exp 失效时间，单位分钟,不传入默认45分钟
	 * @param keyStr .
	 * @return TokenVO
	 */
	public static TokenVO getToken(String uid, Integer exp, String keyStr) {
		TokenVO resultVO = new TokenVO();
		// key.getPrivate().getEncoded();
		//exp = exp == null ? 45 : exp;
		long endTime = new Date().getTime() + 1000 * 60 * (exp == null ? 45 : exp);
		/**
		 * String access_token = Jwts.builder().setSubject(uid).setExpiration(new
		 * Date(endTime)) .signWith(generalKey(keyStr)).compact();// key.getprivate()
		 **/ 
		resultVO.setAccess_token(
				Jwts.builder().setSubject(uid).setExpiration(new Date(endTime)).signWith(generalKey(keyStr)).compact());
		resultVO.setExpiration(new Date(endTime).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime());
		return resultVO;
	}

	/**
	 * <p>功能描述: [ 生成密钥] </p>
	 * @Title generalKey
	 * @param keyStr 32位以上长度字符串
	 * @return SecretKey
	 */
	public static SecretKey generalKey(String keyStr) {
		// SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		byte[] encodedKey = keyStr.getBytes();// Base64Util.decode(keyStr);////
												// Constants.JWT_KEY
		return Keys.hmacShaKeyFor(encodedKey);
	}

	/**
	 * <p>功能描述: [检查Token是否合法] </p>
	 * @Title checkToken
	 * @param token .
	 * @param keyStr .
	 * @return boolean
	 */
	public static boolean checkToken(String token, String keyStr) {
		try {
			Claims claims = Jwts.parser().setSigningKey(generalKey(keyStr)).parseClaimsJws(token).getBody();
			claims.get("sub", String.class);
			return true;
		} catch (ExpiredJwtException e) {
			log.debug("", e);
			// 在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
			return false;
		}
	}

	/**
	 * <p>功能描述: [解析token字符串] </p>
	 * @Title parserToken
	 * @param token .
	 * @param keyStr .
	 * @return Claims
	 */
	public static Claims parserToken(String token, String keyStr) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(generalKey(keyStr)).parseClaimsJws(token).getBody();
			// String sub = claims.get("sub", String.class);
		} catch (ExpiredJwtException e) {
			log.debug("", e);
			// 在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
		}
		return claims;
	}
}
