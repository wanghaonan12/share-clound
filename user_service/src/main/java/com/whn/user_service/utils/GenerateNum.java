package com.whn.user_service.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : WangRich
 * @Description : 生成订单编号
 * @date : 2023/1/5 15:58
 */
public class GenerateNum {

    /**
     * 格式化的时间字符串
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 获取当前时间年月日时分秒毫秒字符串
     * @return
     */
    public static String generateOrder() {
        return sdf.format(new Date());
    }


}
