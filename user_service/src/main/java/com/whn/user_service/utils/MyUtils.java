package com.whn.user_service.utils;

import com.whn.user_service.commons.ResponseResult;
import com.whn.user_service.commons.ResultCode;

/**
 * @author : WangRich
 * @description : 在操作中使用到的小工具
 * @date : 2022/12/31 17:10
 */
public class MyUtils {
    /**
     * 根据传入的操作状态返回不同的返回结果
     * @param aBoolean 操作状态
     * @return 返回ResponseResult
     */
    public static ResponseResult customReturn(Boolean aBoolean) {
        return aBoolean ? ResponseResult.success("操作成功") : ResponseResult.failure(ResultCode.DATABASE_ERROR);
    }

    public static ResponseResult customReturn(Boolean aBoolean, String success) {
        return aBoolean ? ResponseResult.success(success) : ResponseResult.failure(ResultCode.DATABASE_ERROR);
    }

    public static ResponseResult customReturn(Boolean aBoolean, String success, String failure) {
        return aBoolean ? ResponseResult.success(success) : new ResponseResult(99999, failure);
    }
}
