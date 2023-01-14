package com.whn.user_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whn.user_service.auth.AuthAspect;
import com.whn.user_service.server.WebSocketServer;
import com.whn.user_service.domain.Purchase;
import com.whn.user_service.po.BoundsDetailPo;
import com.whn.user_service.po.PurchasePo;
import com.whn.user_service.service.ArticleService;
import com.whn.user_service.service.BoundsDetailService;
import com.whn.user_service.service.PurchaseService;
import com.whn.user_service.mapper.PurchaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangRich
 * @description 针对表【purchase】的数据库操作Service实现
 * @createDate 2022-12-26 12:00:40
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase>
        implements PurchaseService {

    private final PurchaseMapper purchaseMapper;
    private final ArticleService articleService;

    private final WebSocketServer webscoketServer;
    private final BoundsDetailService boundsDetailService;
    private final AuthAspect authAspect;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createPurchase(PurchasePo purchasePo) throws Exception {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchasePo, purchase);
        purchase.setUserId(authAspect.getAttributeId());
        String articleTitle = articleService.findArticleById(purchasePo.getArticleId()).getTitle();
        String description="购买文章:"+articleTitle+"花费"+purchasePo.getSpendPrice()+"积分";
        BoundsDetailPo boundsDetailPo = BoundsDetailPo.builder().userId(authAspect.getAttributeId()).event("-1").value(purchasePo.getSpendPrice()).description(description).build();
        try {
            boundsDetailService.createBounds(boundsDetailPo);
            webscoketServer.sendMessage(description,authAspect.getAttributeId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return save(purchase);
    }

    @Override
    public List<Purchase> findByUserId(Integer userId) {
        QueryWrapper<Purchase> queryWrapper = new QueryWrapper<Purchase>();
        queryWrapper.eq("user_id", userId);
        return purchaseMapper.selectList(queryWrapper);
    }

    @Override
    public Purchase findByUserIdAndArticleId(Integer id, Integer articleId) {
        return null;
    }
}




