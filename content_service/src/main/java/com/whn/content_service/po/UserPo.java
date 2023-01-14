package com.whn.content_service.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/12/26 12:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPo {

    @ApiModelProperty("主键")
    private String id;
    /**
     * 账户密码
     */
    @ApiModelProperty("密码")
    @Size(max = 20,message = "密码长度不可以超过20")
    private String password;

    /**
     * 登陆账号
     */
    @ApiModelProperty("邮箱")
    @Size(max = 20,message = "密码长度不可以超过20")
    private String email;

    /**
     * user头像
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * user名称
     */
    @ApiModelProperty("昵称")
    @Size(max = 20,message = "密码长度不可以超过20")
    private String name;

    /**
     * 账户积分
     */
    @ApiModelProperty("账户积分")
    private Integer bonus;

    /**
     * 用户年纪
     */
    @ApiModelProperty("年纪")
    private Integer age;

    /**
     * 男为1，女为0，默认-1
     */
    @ApiModelProperty("性别:1男,0女,-1未设置")
    private String sex;

    /**
     * 权限设置（user，admin）
     */
    @ApiModelProperty("权限:user,admin")
    private String roles;
}
