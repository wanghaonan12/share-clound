package com.whn.user_service.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whn.user_service.domain.Praise;
import com.whn.user_service.dto.PageQuery;
import com.whn.user_service.po.PraisePo;

import java.util.List;


/**
* @author wangRich
* @description 针对表【praise】的数据库操作Service
* @createDate 2022-12-26 12:01:41
*/
public interface PraiseService extends IService<Praise> {

    /**
     *  创建点赞
     * @param  praisePo praisePo
     * @return 创建状态
     */
    Boolean createPraise(PraisePo praisePo);

    /**
     * 根据用户id分页查询点赞列表
     * @param pageQuery pageQuery
     * @param userId userId
     * @return 点赞列表
     */
    Page<Praise> findPraiseListByUserId(PageQuery pageQuery, String userId);

    /**
     * 根据用户id和文章id查询点在
     * @param userId userId
     * @param articleId articleId
     * @return 点赞列表
     */
    Praise findPraiseListByUserIdAndArticleId(Integer userId,Integer articleId);

    /**
     * 根据id删除点赞记录
     * @param ids id
     * @return 删除状态
     */
    Boolean deletePraiseById(List<Integer> ids);

    /**
     *  根据用户id和文章id进行查找并确认是否点赞
     * @param userId 用户id
     * @param articleId 文章id
     * @return 点赞对象
     */
    Praise findPraiseByUserIdAndArticleId(String userId,Integer articleId);
}
