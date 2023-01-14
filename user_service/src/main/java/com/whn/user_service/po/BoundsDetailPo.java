package com.whn.user_service.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/12/26 13:12
 */
@Builder
@Data
public class BoundsDetailPo {

    /**
     * 积分userid
     */

    private String userId;

    /**
     * 积分变动时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "创建时间不能为空")
    private Date createTime;

    /**
     * 积分变动金额
     */
    @ApiModelProperty("积分变动金额")
    @NotNull(message = "积分变动金额不能为空")
    private Integer value;

    /**
     * 积分变动状态
     */
    @ApiModelProperty("积分变动状态[大于0增加：小于0减少]")
    @NotNull(message = "积分变动状态不能为空")
    private String event;

    /**
     * 积分变动描述
     */
    @ApiModelProperty("积分变动描述")
    @NotNull(message = "积分变动描述不能为空")
    private String description;
}
