package com.whn.user_service.domain;

import com.baomidou.mybatisplus.annotation.*;

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
 * @TableName purchase
 */
@TableName(value ="purchase")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase implements Serializable {
    /**
     * 购买id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 购买文章id
     */
    @ApiModelProperty("购买文章id")
    @NotNull(message = "购买文章id不能为空")
    private Integer articleId;

    /**
     * 购买用户id
     */
    @ApiModelProperty("购买用户id")
    @NotNull(message = "购买用户id不能为空")
    private String userId;

    /**
     * 购买花费
     */
    @ApiModelProperty("购买花费")
    @NotNull(message = "购买花费不能为空")
    private Integer spendPrice;

    /**
     * 购买时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    @NotNull(message = "创建时间不能为空")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}