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
 * @TableName article
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePo implements Serializable {
    /**
     * 文章id
     */
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 发布者id
     */
    @NotNull(message = "发布者id不能为空")
    @ApiModelProperty("发布者id")
    private String userId;

    /**
     * 文章title
     */
    @NotNull(message = "文章标题不能为空")
    @ApiModelProperty("文章标题")
    private String title;

    /**
     * 文章附件
     */
    @ApiModelProperty("附件地址")
    private String accessoryUrl;

    /**
     * 文章内容
     */
    @NotNull(message = "文章内容不能为空")
    @ApiModelProperty("文章内容")
    private String content;

    /**
     * 文章查看金额
     */
    @ApiModelProperty("文章查看金额：默认0")
    private Integer price;

    /**
     * 点赞数
     */
    @ApiModelProperty("点赞数：默认0")
    private Integer praiseCount;

    /**
     * 购买数
     */
    @ApiModelProperty("购买数：默认0")
    private Integer buyCount;

    /**
     * 文章分类id
     */
    @ApiModelProperty("文章分类Id")
    @NotNull(message = "文章分类Id不能为空")
    private Integer articleTagId;

    /**
     * 浏览次数
     */
    @ApiModelProperty("浏览次数：默认0")
    private Integer showCount;

    /**
     * true为分享文章，false为提问文章
     */
    @ApiModelProperty("文章是否为分享文章")
    private String share;

    /**
     * 回答奖励积分数
     */
    @ApiModelProperty("回答奖励积分：默认0")
    private Integer award;

    /**
     * 评论数
     */
    @ApiModelProperty("评论数：默认0")
    private Integer discussCount;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}