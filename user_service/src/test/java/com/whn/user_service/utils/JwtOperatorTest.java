package com.whn.user_service.utils;

import com.whn.user_service.UserServiceApplication;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/12/26 17:10
 */
@SpringBootTest(classes = UserServiceApplication.class)
@RunWith(SpringRunner.class)
class JwtOperatorTest {

    @Resource
    private JwtOperator jwtOperator;

    @Test
    void getClaimsFromToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", "wangrich");
        claims.put("age", "123");
        String s = jwtOperator.generateToken(claims);
        System.out.println(s);
        Claims claimsFromToken = jwtOperator.getClaimsFromToken(s);
        String name = (String) claimsFromToken.get("name");
        String age = (String) claimsFromToken.get("age");
        System.out.println("name:" + name + "\nage:" + age);
    }

}