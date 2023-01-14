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
 * @author wangRich
 * @TableName brows_history
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrowsHistoryPo implements Serializable {
    /**
     * 浏览历史id
     */
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


    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}