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
 * @author wangrich
 * @TableName answer
 */
@TableName(value ="answer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer implements Serializable {
    /**
     * 回答id
     */
    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 给与回答的用户id
     */
    @NotNull(message = "回答用户的id不能为空")
    @ApiModelProperty("回答用户id")
    private String userId;

    /**
     * 回答的文章id
     */
    @NotNull(message = "文章id不能为空")
    @ApiModelProperty("文章id")
    private Integer articleId;

    /**
     * 回答内容
     */
    @NotNull(message = "回答内容不能为空")
    @ApiModelProperty("回答内容")
    private String content;

    /**
     * 附件Url
     */
    @ApiModelProperty("附件地址")
    private String accessoryUrl;

    /**
     * 是否全部展示（true展示，false不展示）
     */
    @ApiModelProperty("回答是否展示状态默认：false，true展示，false不展示")
    private String showAll;

    /**
     * 是否采用此答案：false，true采用，false不采用
     */
    @TableField(value = "accept")
    @ApiModelProperty("是否采用此答案：false，true采用，false不采用")
    private String accept;

    /**
     * 回答时间
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