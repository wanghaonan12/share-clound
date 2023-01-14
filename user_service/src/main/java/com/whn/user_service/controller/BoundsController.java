package com.whn.user_service.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whn.user_service.auth.AuthAspect;
import com.whn.user_service.auth.CheckLogin;
import com.whn.user_service.commons.ResponseResult;
import com.whn.user_service.domain.BoundsDetail;
import com.whn.user_service.dto.PageQuery;
import com.whn.user_service.service.BoundsDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/12/27 14:22
 */
@Api(tags = {"积分模块api"})
@RequestMapping("/bounds")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BoundsController {
    private final BoundsDetailService boundsDetailService;
    private final AuthAspect authAspect;

    @CheckLogin
    @PostMapping("/details")
    @ApiOperation(value = "购物记录")
    public ResponseResult findBoundsDetails(@RequestBody PageQuery pageQuery) {
        Page<BoundsDetail> purchaseList = boundsDetailService.findBoundsDetailByUserId(pageQuery,authAspect.getAttributeId());
        return  ResponseResult.success(purchaseList);
    }
}
