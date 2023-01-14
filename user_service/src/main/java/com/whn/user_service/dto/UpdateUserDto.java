package com.whn.user_service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2023/1/6 17:30
 */
@Data
@Builder
public class UpdateUserDto {


    /**
     * 账户密码
     */
    @ApiModelProperty("密码")
    @Size(max = 20,message = "密码长度不可以超过20")
    private String password;

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
     * 用户年纪
     */
    @ApiModelProperty("年纪")
    private Integer age;

    /**
     * 男为1，女为0，默认-1
     */
    @ApiModelProperty("性别:1男,0女,-1未设置")
    private String sex;

}
