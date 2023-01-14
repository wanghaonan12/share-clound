package com.whn.user_service.dto;

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
public class DiscussDto implements Serializable {

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


    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}