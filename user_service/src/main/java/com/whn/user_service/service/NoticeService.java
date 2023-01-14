package com.whn.user_service.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.whn.user_service.domain.Notice;
import com.whn.user_service.po.NoticePo;
import com.whn.user_service.vo.NoticeVo;

import java.util.List;

/**
* @author wangRich
* @description 针对表【notice】的数据库操作Service
* @createDate 2022-12-26 12:01:41
*/
public interface NoticeService extends IService<Notice> {

    /**
     *  创建通知
     * @param noticePo noticePo
     * @return 创建状态
     */
    Boolean createNotice(NoticePo noticePo);

    /**
     *  根据发送者id查找通知
     * @param sentUserId 发送者id
     * @return 通知列表
     */
    List<Notice> findNoticeListBySentUserId(String sentUserId);

    /**
     *  根据发送者id查找通知
     * @param receiveUserId 接收者id
     * @return 通知列表
     */
    List<Notice> findNoticeListByReceiveUserId(String receiveUserId);

    /**
     *  根据id删除通知
     * @param noticeId noticeId
     * @return 删除状态
     */
    Boolean deleteNoticeById(Integer noticeId);

    /**
     *  获取没有发送的通知
     * @param userId noticeId
     * @return 删除状态
     */
    List<NoticeVo> findNoReceiveNoticeByReceiveUserId(String userId);
}
