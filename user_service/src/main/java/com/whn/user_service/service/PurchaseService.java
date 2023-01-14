package com.whn.user_service.service;

import com.whn.user_service.domain.Purchase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whn.user_service.po.PurchasePo;

import java.util.List;

/**
 * @author wangRich
 * @description 针对表【purchase】的数据库操作Service
 * @createDate 2022-12-26 12:00:40
 */
public interface PurchaseService extends IService<Purchase> {
    /**
     *  创建购买记录
     * @param purchase 购买商品信息
     * @return 返回购买状态
     * @throws Exception 积分不足异常抛出
     */
    Boolean createPurchase(PurchasePo purchase) throws Exception;

    /**
     *  根据用户id获取购买记录
     * @param userId 用户id
     * @return List<Purchase>
     */
    List<Purchase> findByUserId(Integer userId);

    /**
     *  根据文章id和用户id查找用户是否购买此文章
     * @param id
     * @param articleId
     * @return
     */
    Purchase findByUserIdAndArticleId(Integer id,Integer articleId);
}
