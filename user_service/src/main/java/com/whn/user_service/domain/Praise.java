package com.whn.user_service.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author wangRich
 * @TableName praise
 */
@TableName(value ="praise")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Praise implements Serializable {
    /**
     * 点赞id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 点赞用户id
     */
    @NotNull(message = "点赞用户的id不能为空")
    @ApiModelProperty("点赞用户id")
    private String userId;

    /**
     * 点赞文章id
     */
    @NotNull(message = "点赞文章的id不能为空")
    @ApiModelProperty("点赞文章id")
    private Integer articleId;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}