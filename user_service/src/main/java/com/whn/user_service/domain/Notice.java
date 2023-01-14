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
 * @author wangRich
 * @TableName notice
 */
@TableName(value ="notice")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice implements Serializable {
    /**
     * 通知id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 发送用户id
     */
    @NotNull(message = "发送用户id不能为空")
    @ApiModelProperty("发送用户id")
    private String sentUserId;

    /**
     * 接收用户id
     */
    @NotNull(message = "接收用户的id不能为空")
    @ApiModelProperty("接收用户id")
    private String receiveUserId;

    /**
     * 发送内容
     */
    @NotNull(message = "发送内容")
    @ApiModelProperty("发送内容")
    private String content;

    /**
     * 发送状态
     */
    @ApiModelProperty("发送状态")
    private String status;

    /**
     * 发送时间
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