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
 * @TableName discuss
 */
@TableName(value ="discuss")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Discuss implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 评论者id
     */
    @NotNull(message = "评论者id不能为空")
    @ApiModelProperty("评论者id")
    private String userId;

    /**
     * 文章id
     */
    @NotNull(message = "评论文章的id不能为空")
    @ApiModelProperty("评论文章的id")
    private Integer articleId;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 评论内容
     */
    @NotNull(message = "评论内容不能为空")
    @ApiModelProperty("评论内容")
    private String commentDetails;

    /**
     * 评论点赞数
     */
    @ApiModelProperty("评论点赞数：默认0")
    private Integer praiseCount;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}