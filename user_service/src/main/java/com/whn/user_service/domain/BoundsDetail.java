package com.whn.user_service.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author wangRich
 * @TableName bounds_detail
 */
@TableName(value ="bounds_detail")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoundsDetail implements Serializable {
    /**
     * 积分详情id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 积分userid
     */

    @NotNull(message = "用户id不能为空")
    private String userId;

    /**
     * 积分变动时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "创建时间不能为空")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 积分变动金额
     */
    @ApiModelProperty("积分变动金额")
    @NotNull(message = "积分变动金额不能为空")
    private Integer value;

    /**
     * 积分变动状态
     */
    @ApiModelProperty("积分变动状态：增加,减少")
    @NotNull(message = "积分变动状态不能为空")
    private String event;

    /**
     * 积分变动描述
     */
    @ApiModelProperty("积分变动描述")
    @NotNull(message = "积分变动描述不能为空")
    private String description;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}