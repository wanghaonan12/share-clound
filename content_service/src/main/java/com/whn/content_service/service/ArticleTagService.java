package com.whn.content_service.service;

import com.whn.content_service.domain.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whn.content_service.po.ArticleTagPo;

import java.util.List;

/**
* @author wangRich
* @description 针对表【article_tag】的数据库操作Service
* @createDate 2022-12-26 12:01:41
*/
public interface ArticleTagService extends IService<ArticleTag> {

    /**
     *  创建更新文章标签
     * @param articleTagPo articleTagPo
     * @return 创建更新状态
     */
    Boolean createOrUpdateArticleTag(ArticleTagPo articleTagPo);

    /**
     *  查询所有文章标签
     * @return 文章标签列表
     */
    List<ArticleTag> findAllArticleTags();

    /**
     *  根据id批量删除
     * @param ids id列表
     * @return 删除状态
     */
    Boolean deleteArticleTagByIds(List<Integer> ids);
}
