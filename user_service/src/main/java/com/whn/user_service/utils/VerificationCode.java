package com.whn.user_service.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author : WangRich
 * @Description : 验证码生成发送
 * @date : 2023/1/1 15:25
 */
@Slf4j
@Component
public class VerificationCode {


    @Autowired
    private RedisOperator redisOperator;

    private final Encrypt encrypt = new Encrypt();


    @Value("${verification.time-out}")
    public long timeOut;

    @Value("${verification.code-size}")
    public int codeSize;
    @Value("${verification.email}")
    public String sendEmail;

    @Value("${verification.password}")
    public String password;

    /**
     * 创建验证码
     *
     * @return 返回验证码
     */
    private String randomCode() {
        String code = "";
        Random random = new Random();
        for (int i = 0; i < codeSize; i++) {
            code += random.nextInt(10);
        }
        return code;
    }

    /**
     * 发送验证码给toEmail并存储验证码在redis中
     *
     * @param toEmail 接受方电子邮箱
     * @return 返回发送状态
     */
    public Boolean sendCode(String toEmail) {
        Boolean status = false;
        String code = randomCode();
        log.info(toEmail + "的验证码是：" + code);
        status = sendReplyEmail(toEmail, code);
        if (status) {
            redisOperator.hSet(toEmail, code, timeOut);
        }
        return status;
    }

    /**
     * 发送邮件
     *
     * @param toEmail 接收方
     * @param code    验证码
     * @return 返回发送状态
     */
    private Boolean sendReplyEmail(String toEmail, String code) {
        String content = "【知识分享】您的验证码是" + code + "，5分钟内有效，请勿泄露";
        String title = "【知识分享】验证码";

        SimpleEmail simpleEmail = new SimpleEmail();
        //开启SSL加密
        //SMTP服务器的端口
        simpleEmail.setSslSmtpPort("465");
        //SMTP服务器的名字
        simpleEmail.setHostName("smtp.163.com");
        //发件人邮箱以及授权码
        simpleEmail.setAuthentication(encrypt.decode(sendEmail), encrypt.decode(password));
        //编码集
        simpleEmail.setCharset("UTF-8");

        try {
            //收件人邮箱
            simpleEmail.addTo(toEmail);
            //发件人邮箱以及发件人名称
            simpleEmail.setFrom(encrypt.decode(sendEmail));
            //邮件主题
            simpleEmail.setSubject(title);
            //邮件内容
            simpleEmail.setMsg(content);
            simpleEmail.send();
        } catch (Exception e) {
            log.error("发送邮件失败", e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 比较验证码是否正确，正确后立即删除key对应的验证码
     *
     * @param key  接收方email
     * @param code 验证码
     * @return 比较哦啊结果
     */
    public Boolean compareCode(String key, String code) {
        String redisSaveCode = redisOperator.get(key);
        System.out.println(key);
        System.out.println(redisSaveCode);
        if (redisSaveCode == null||redisSaveCode.isBlank()) {
            return false;
        }else {
            int i = redisSaveCode.compareTo(code);
            if (0 == i) {
                redisOperator.del(key);
                return true;
            }
        }
        return false;
    }
}
