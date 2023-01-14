package com.whn.content_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whn.content_service.domain.BoundsDetail;
import com.whn.content_service.dto.PageQuery;
import com.whn.content_service.mapper.BoundsDetailMapper;
import com.whn.content_service.po.BoundsDetailPo;
import com.whn.content_service.service.BoundsDetailService;
import com.whn.content_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wangRich
 * @description 针对表【bouns_detail】的数据库操作Service实现
 * @createDate 2022-12-25 21:02:42
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BoundsDetailServiceImpl extends ServiceImpl<BoundsDetailMapper, BoundsDetail>
        implements BoundsDetailService {
    private final UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createBounds(BoundsDetailPo boundsDetailPo) throws Exception {
        BoundsDetail boundsDetail = new BoundsDetail();
        BeanUtils.copyProperties(boundsDetailPo, boundsDetail);
        // 获取该用户积分并进行修改
        Boolean state = userService.changeBounds(boundsDetailPo.getUserId(), boundsDetailPo.getEvent(), boundsDetailPo.getValue());
        return state && save(boundsDetail);
    }
}




