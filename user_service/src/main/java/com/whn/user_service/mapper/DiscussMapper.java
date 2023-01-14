package com.whn.user_service.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whn.user_service.domain.Discuss;
import com.whn.user_service.vo.DiscussVo;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
* @author wangRich
* @description 针对表【discuss】的数据库操作Mapper
* @createDate 2022-12-26 12:01:41
* @Entity com.whn.content_service.domain.Discuss
*/
public interface DiscussMapper extends BaseMapper<Discuss> {
    List<DiscussVo> getDiscussById(@Param("articleId")Integer  articleId,@Param("size")Integer  size ,@Param("num")Integer  num);
}




