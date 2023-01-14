package com.whn.user_service.service;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whn.user_service.domain.Discuss;
import com.whn.user_service.dto.FindDiscussPageQuery;
import com.whn.user_service.dto.PageQuery;
import com.whn.user_service.po.DiscussPo;
import com.whn.user_service.vo.DiscussVo;

import java.util.List;

/**
* @author wangRich
* @description 针对表【discuss】的数据库操作Service
* @createDate 2022-12-26 12:01:41
*/
public interface DiscussService extends IService<Discuss> {
    /**
     *  创建更新评论
     * @param discussPo discussPo
     * @return 创建更新状态
     */
    Boolean createOrUpdateArticleTag(DiscussPo discussPo);

    /**
     *  根据文章id查询所有文章评论
     * @param findDiscussPageQuery articleId
     * @return 文章标签列表
     */
    List<DiscussVo> findAllDiscussByArticleId(FindDiscussPageQuery findDiscussPageQuery);

    /**
     *  根据id批量删除
     * @param ids 评论id列表
     * @return 删除状态
     */
    Boolean deleteDiscussByIds(List<Integer> ids);
}
