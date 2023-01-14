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
 * @description : description
 * @date : 2022/12/26 12:55
 */
@Builder
@Data
public class PurchasePo {
    /**
     * 购买文章id
     */
    @ApiModelProperty("购买文章id")
    @NotNull(message = "购买文章id不能为空")
    private Integer articleId;

    /**
     * 购买花费
     */
    @ApiModelProperty("购买花费")
    @NotNull(message = "购买花费不能为空")
    private Integer spendPrice;

}
