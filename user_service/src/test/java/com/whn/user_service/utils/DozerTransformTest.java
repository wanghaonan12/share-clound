package com.whn.user_service.utils;

import com.whn.user_service.UserServiceApplication;
import com.whn.user_service.domain.User;
import com.whn.user_service.po.UserPo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/12/26 17:32
 */

@SpringBootTest(classes = UserServiceApplication.class)
@RunWith(SpringRunner.class)
class DozerTransformTest {

    @Resource
    Encrypt encrypt;
    @Test
    void transform() {
//        UserPo userPo = UserPo.builder().age(15).name("whn").build();
//        User user = new User();
//        BeanUtils.copyProperties(userPo, user);
//        System.out.println(user);
        System.out.println(encrypt.decode("VjFjd01WWXlUWGxUYTJ4clUwWmFhRll3V21GT2JHeHhVMjFHVGxJeGNGTlZSbEYzVUZFOVBRPT0="));
    }
}