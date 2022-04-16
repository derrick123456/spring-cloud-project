package com.test.demo.user.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wgg
 * @date 2021/04/05
 */
@Data
public class LoginVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8326231619094358484L;

    /**
     * 
     */
    private String tenant;

    private String id;

    /**
     * 用户ID
     */
    private String userId;

    private String userName;
    
    private String mobile;

    private String creator;

    private String editor;

    private String creatorId;

    private String editorId;

    private String creatorName;

    private String editorName;

    private String currentClient;

    private String currentClientName;

    private String authorization;

    private String currentApp;

    private String currentAppName;

}
