package com.whn.content_service.domain;

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
 * @TableName brows_history
 */
@TableName(value ="brows_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrowsHistory implements Serializable {
    /**
     * 浏览历史id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 浏览用户id
     */
    @NotNull(message = "浏览用户id不能为空")
    @ApiModelProperty("浏览用户id")
    private String userId;

    /**
     * 浏览文章id
     */
    @NotNull(message = "浏览文章id不能为空")
    @ApiModelProperty("浏览文章id")
    private Integer articleId;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}