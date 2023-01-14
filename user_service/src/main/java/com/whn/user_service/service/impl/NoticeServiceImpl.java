package com.whn.user_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whn.user_service.domain.Notice;
import com.whn.user_service.mapper.NoticeMapper;
import com.whn.user_service.po.NoticePo;
import com.whn.user_service.service.NoticeService;
import com.whn.user_service.vo.NoticeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangRich
 * @description 针对表【notice】的数据库操作Service实现
 * @createDate 2022-12-26 12:01:41
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
        implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public Boolean createNotice(NoticePo noticePo) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticePo, notice);
        return saveOrUpdate(notice);
    }

    @Override
    public List<Notice> findNoticeListBySentUserId(String sentUserId) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sent_user_id", sentUserId);
        return list(queryWrapper);
    }

    @Override
    public List<Notice> findNoticeListByReceiveUserId(String receiveUserId) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receive_user_id", receiveUserId);
        return list(queryWrapper);
    }

    @Override
    public Boolean deleteNoticeById(Integer noticeId) {
        return removeById(noticeId);
    }

    @Override
    public List<NoticeVo> findNoReceiveNoticeByReceiveUserId(String userId) {
        return noticeMapper.findList(userId);
    }
}




