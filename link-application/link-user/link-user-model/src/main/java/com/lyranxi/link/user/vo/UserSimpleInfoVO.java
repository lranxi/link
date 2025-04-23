package com.lyranxi.link.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ranxi
 * @date 2025-04-18 16:17
 */
@Data
public class UserSimpleInfoVO implements Serializable {

    /** 用户ID */
    private Long id;
    /** 用户名 */
    private String username;
    /** 电话号码 */
    private String phone;
    /** 昵称 */
    private String nickname;
    /** 头像 */
    private String avatar;
    /** 性别 */
    private Integer gender;

}
