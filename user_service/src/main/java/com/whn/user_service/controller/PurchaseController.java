package com.whn.user_service.controller;

import com.whn.user_service.auth.AuthAspect;
import com.whn.user_service.auth.CheckLogin;
import com.whn.user_service.commons.ResponseResult;
import com.whn.user_service.commons.ResultCode;
import com.whn.user_service.domain.Purchase;
import com.whn.user_service.domain.User;
import com.whn.user_service.po.PurchasePo;
import com.whn.user_service.service.PurchaseService;
import com.whn.user_service.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/12/27 14:22
 */
@Api(tags = {"购买文章模块api"})
@RequestMapping("/purchase")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final AuthAspect authAspect;
    private final UserService userService;

    @CheckLogin
    @PostMapping("")
    @ApiOperation(value = "购买文章")
    public ResponseResult purchase(@RequestBody PurchasePo purchasePo) {
        User userById = userService.findUserById(authAspect.getAttributeId());
        if (userById.getBonus() > purchasePo.getSpendPrice()) {
            try {
                return purchaseService.createPurchase(purchasePo) ? ResponseResult.success("购买成功") : ResponseResult.failure(ResultCode.INTERFACE_REQUEST_TIMEOUT);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ResponseResult(9999,"积分不足");
        }

    }

    @CheckLogin
    @GetMapping("/record")
    @ApiOperation(value = "购买记录")
    public ResponseResult purchaseRecord(@RequestParam Integer userId) {
        List<Purchase> purchaseList = purchaseService.findByUserId(userId);
        return ResponseResult.success(purchaseList);
    }

//    @CheckLogin
//    @GetMapping("/check/{articleId}")
//    public ResponseResult checkStatus(@PathVariable("articleId")Integer articleId){
//        purchaseService.
//    }

}
