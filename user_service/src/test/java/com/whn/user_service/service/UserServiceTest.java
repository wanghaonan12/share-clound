package com.whn.user_service.service;

import com.whn.user_service.UserServiceApplication;
import com.whn.user_service.domain.Praise;
import com.whn.user_service.po.BoundsDetailPo;
import com.whn.user_service.po.UserPo;
import com.whn.user_service.utils.Encrypt;
import com.whn.user_service.utils.VerificationCode;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/12/26 18:02
 */
@SpringBootTest(classes = UserServiceApplication.class)
@RunWith(SpringRunner.class)
class UserServiceTest {

    @Resource
    private BoundsDetailService boundsDetailService;
    @Resource
    private PraiseService praiseService;
    @Test
    void saveOrUpdateUser() throws Exception {
//        UserPo userPo= UserPo.builder().password("111111").email("1470918223@qq.com").build();
////        System.out.println(userService.saveOrUpdateUser(userPo));
//        String num = "100.00";
//        num=num.substring(0,num.lastIndexOf("."));
//        System.out.println(num);
//        boundsDetailService.createBounds(BoundsDetailPo.builder().userId("a4c7826b20bd4ed1baecf11494a96273").value(1000).event("1").description("购买积分").build());
        Praise praiseByUserIdAndArticleId = praiseService.findPraiseByUserIdAndArticleId("23123",12 );
        System.out.println(praiseByUserIdAndArticleId);
    }

    @Resource
    private RedisTemplate<String,String> redisTemplate;
    private VerificationCode verification;
    @Resource
    private  Encrypt encrypt;

    @Test
    void contextLoads() {
//        try {
//            verification.sendReplyEmail("1470918223@qq.com","123456");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        String email = encrypt.encode("15262171552@163.com");
        String password = encrypt.encode("SIVLJIEBIJLYCRQJ");

        System.out.println(email);
        System.out.println(password);
    }

}