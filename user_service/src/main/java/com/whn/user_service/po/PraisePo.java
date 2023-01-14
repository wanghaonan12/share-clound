package com.whn.user_service.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @TableName praise
 */
@TableName(value ="praise")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PraisePo implements Serializable {
    /**
     * 点赞id
     */
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

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}