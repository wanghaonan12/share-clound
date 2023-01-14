package com.whn.user_service.controller;

import com.whn.user_service.service.AliPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2023/1/5 15:45
 */
@RestController
@RequestMapping("/alipay")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AliPayController {

    public static AliPayService aliPayService;

    @GetMapping("/pay/{total}/{userId}")
    public String pay(@PathVariable String total,@PathVariable String userId) {
        return aliPayService.createAliPay(total,userId);
    }

    @ResponseBody
    @PostMapping("/notify")
    public String payNotify(@RequestParam Map<String, String> map) {
        return aliPayService.aliPayNotify(map);
    }
}
