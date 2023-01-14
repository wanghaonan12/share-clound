package com.whn.content_service.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 *
 * @author wangrich
 * @TableName answer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto implements Serializable {

    /**
     * 回答的文章id
     */
    @NotNull(message = "文章id不能为空")
    private Integer articleId;


    /**
     * 回答内容
     */
    @NotNull(message = "回答内容不能为空")
    private String content;

    /**
     * 附件Url
     */
    private MultipartFile file;

    /**
     * 是否全部展示（true展示，false不展示）
     */
    private String showAll;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}