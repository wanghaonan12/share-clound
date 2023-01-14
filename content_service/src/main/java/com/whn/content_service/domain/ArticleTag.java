package com.whn.content_service.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 *
 * @author wangRich
 * @TableName article_tag
 */
@TableName(value ="article_tag")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTag implements Serializable {
    /**
     * 分类id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 分类名称
     */
    @NotNull(message = "分类名称不能为空")
    @ApiModelProperty("分类名")
    private String classify;

    /**
     * 分类描述
     */
    @ApiModelProperty("分类描述")
    private String content;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}