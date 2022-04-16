package com.test.demo.user.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wgg
 * @date 2021/04/05
 */
@Data
public class TokenVO {

	/**
	 * <p>字段描述:[返回的有效token]</p>
	 * @Fields access_token  
	 */
	private String access_token;

	/**
	 * <p>字段描述:[token有效期]</p>
	 * @Fields expiration  
	 */
	private LocalDateTime expiration;
}
