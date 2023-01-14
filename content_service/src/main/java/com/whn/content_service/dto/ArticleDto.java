package com.whn.content_service.dto;

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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
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
public class ArticleDto implements Serializable {


    private Integer id;
    /**
     * 文章title
     */
    private String title;

    /**
     * 文章附件
     */
    private MultipartFile file;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章查看金额
     */
    private Integer price;


    /**
     * 文章分类id
     */
    private Integer articleTagId;

    /**
     * true为分享文章，false为提问文章
     */
    private String share;

    /**
     * 回答奖励积分数
     */
    private Integer award;


    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}