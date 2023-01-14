package com.whn.content_service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whn.content_service.domain.Article;
import com.whn.content_service.domain.BrowsHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whn.content_service.dto.PageQuery;
import com.whn.content_service.po.BrowsHistoryPo;

import java.util.List;

/**
* @author wangRich
* @description 针对表【brows_history】的数据库操作Service
* @createDate 2022-12-26 12:01:41
*/
public interface BrowsHistoryService extends IService<BrowsHistory> {

    /**
     *  创建更新浏览历史
     * @param browsHistoryPo browsHistoryPo
     * @return 创建更新状态
     */
    Boolean createOrUpdateBrowsHistory(BrowsHistoryPo browsHistoryPo);

    /**
     *  根据使用者id查询所有浏览历史
     * @param pageQuery 分页及id
     * @param userId 用户id
     * @return 文章标签列表
     */
    List<Article> findAllArticleTagsByUserId(PageQuery pageQuery, String userId);

    /**
     *  根据id批量删除
     * @param ids id列表
     * @return 删除状态
     */
    Boolean deleteBrowsHistoryTagByIds(List<Integer> ids);

    /**
     *  根据用户id和w文章id查找
     * @param userId userId
     * @param articleId pageSize
     * @return 浏览记录
     */
    BrowsHistory findByArticleIdAndUserId(String userId,Integer articleId);
}
