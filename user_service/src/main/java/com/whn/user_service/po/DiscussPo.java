package com.whn.user_service.po;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 *
 * @author wangRich
 * @TableName discuss
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscussPo implements Serializable {
    /**
     * 主键
     */
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