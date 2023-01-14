package com.whn.user_service.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author wangRich
 * @TableName order
 */

@TableName(value ="user_order")
@Data
@Builder
public class UserOrder implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * createTime
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     *  支付状态
     */
    private String status;

    /**
     *  订单名称
     */
    private String orderName;

    /**
     *  订单用户
     */
    private String userId;

    /**
     *  订单价格
     */
    private String total;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}