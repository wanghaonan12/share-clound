package com.whn.user_service.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whn.user_service.domain.Notice;
import com.whn.user_service.vo.NoticeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author wangRich
* @description 针对表【notice】的数据库操作Mapper
* @createDate 2022-12-26 12:01:41
* @Entity com.whn.content_service.domain.Notice
*/
public interface NoticeMapper extends BaseMapper<Notice> {
List<NoticeVo> findList(@Param("receiveUserId") String  receiveUserId);
}




