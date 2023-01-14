package com.whn.content_service.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whn.content_service.domain.BoundsDetail;
import com.whn.content_service.dto.PageQuery;
import com.whn.content_service.po.BoundsDetailPo;
import org.springframework.transaction.annotation.Transactional;

/**
* @author wangRich
* @description 针对表【bouns_detail】的数据库操作Service
* @createDate 2022-12-25 21:02:42
*/
public interface BoundsDetailService extends IService<BoundsDetail> {

    /**
     *  创建积分消费详情
     * @param bounds BoundsDetailPo
     * @return 返回创建状态
     * @throws Exception 积分不足异常
     */
    @Transactional(rollbackFor = Exception.class)
    Boolean createBounds(BoundsDetailPo bounds) throws Exception;
}
