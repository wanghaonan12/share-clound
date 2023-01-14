package com.whn.user_service.service;

import com.whn.user_service.UserServiceApplication;
import com.whn.user_service.po.PurchasePo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/12/27 12:00
 */
@SpringBootTest(classes = UserServiceApplication.class)
@RunWith(SpringRunner.class)
class PurchaseServiceTest {

    @Resource
    private PurchaseService purchaseService;

    @Test
    void createPurchase() {
        PurchasePo po = PurchasePo.builder().articleId(1).spendPrice(11).build();
        try {
            System.out.println(purchaseService.createPurchase(po));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findByUserId() {
        System.out.println(purchaseService.findByUserId(1));
    }

    @Test
    void redis() {

    }
}