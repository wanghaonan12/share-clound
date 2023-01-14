package com.whn.user_service.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author wangRich
 * @TableName user
 */
@TableName(value ="user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**
     * 账户id
     */
    @ApiModelProperty("主键")
    private String id;

    /**
     * 账户密码
     */
    @NotNull(message = "密码不能为空")
    @ApiModelProperty("密码")
    @Size(max = 20,message = "密码长度不可以超过20")
    private String password;

    /**
     * 登陆账号
     */
    @NotNull(message = "邮箱不能为空")
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
    @NotNull(message = "昵称不能为空")
    @ApiModelProperty("昵称")
    @Size(max = 20,message = "密码长度不可以超过20")
    private String name;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

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

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}