package com.whn.user_service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whn.user_service.domain.BoundsDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whn.user_service.dto.PageQuery;
import com.whn.user_service.po.BoundsDetailPo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     *  根据用户id获取消费详情
     * @param userId 用户id
     * @param pageQuery 分页数据
     * @return 积分详情变动详情列表
     */
    Page<BoundsDetail> findBoundsDetailByUserId(PageQuery pageQuery,String userId);
}
