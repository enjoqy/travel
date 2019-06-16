package cn.itcast.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    /**
     * 用户id
     */
    private int uid;
    /**
     * 用户名，账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 男或女
     */
    private String sex;
    /**
     * 手机号
     */
    private String telephone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 激活状态，Y代表激活，N代表未激活
     */
    private String status;
    /**
     * 激活码（要求唯一）
     */
    private String code;

}
