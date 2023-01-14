package com.whn.user_service.service;

import com.whn.user_service.UserServiceApplication;
import com.whn.user_service.po.BoundsDetailPo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/12/27 12:00
 */
@SpringBootTest(classes = UserServiceApplication.class)
@RunWith(SpringRunner.class)
class BoundsDetailServiceTest {

    @Resource
    private BoundsDetailService boundsDetailService;
    @Test
    void createBounds() {
        BoundsDetailPo boundsDetailPo = BoundsDetailPo.builder().createTime(new Date()).description("测试添加").event("1").value(10).build();
        try {
            System.out.println(boundsDetailService.createBounds(boundsDetailPo));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    void findBoundsDetailByUserId() {
//        System.out.println(boundsDetailService.findBoundsDetailByUserId(1));
    }
}