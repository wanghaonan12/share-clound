package com.whn.user_service.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * @author : WangRich
 * @Description : base64加密工具类
 * @date : 2023/1/1 9:26
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class Encrypt {
    /**
     * 加密拼接字符
     */
    private static final String SALT = "neusoft";
    /**
     * 加密次数
     */
    private static final int REPEAT = 5;

    public  String encode(String string) {
        log.info("对{"+string+"}加密");
        String temp = string + "{" + SALT + "}";
        byte[] data = temp.getBytes();
        for (int i = 0; i < REPEAT; i++) {
            data = Base64.getEncoder().encode(data);
        }
        return new String(data);
    }

    public  String decode(String string) {
        byte[] data = string.getBytes();
        for (int i = 0; i < REPEAT; i++) {
            data = Base64.getDecoder().decode(data);
        }
        return new String(data).replaceAll("\\{\\w+\\}", "");
    }
}
