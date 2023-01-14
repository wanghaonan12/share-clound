package com.whn.content_service.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @author wangrich
 * @TableName answer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerPo implements Serializable {
    /**
     * 回答id
     */
    @ApiModelProperty("主键")
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
     * 是否采用此答案：false，true采用，false不采用
     */
    @ApiModelProperty("是否采用此答案：false，true采用，false不采用")
    private String accept;

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
    @ApiModelProperty("文章是否展示状态默认：false，true展示，false不展示")
    private String showAll;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}