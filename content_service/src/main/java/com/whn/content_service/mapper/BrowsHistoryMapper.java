package com.whn.content_service.mapper;

import com.whn.content_service.domain.Article;
import com.whn.content_service.domain.BrowsHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whn.content_service.dto.PageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author wangRich
* @description 针对表【brows_history】的数据库操作Mapper
* @createDate 2022-12-26 12:01:41
* @Entity com.whn.content_service.domain.BrowsHistory
*/
public interface BrowsHistoryMapper extends BaseMapper<BrowsHistory> {
    List<Article> selectBrowsHistoryById(@Param("userId") String userId, @Param("size")Integer size, @Param("num") Integer num);
}




