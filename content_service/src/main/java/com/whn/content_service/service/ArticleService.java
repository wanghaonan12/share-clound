package com.whn.content_service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whn.content_service.dto.ArticleTagFindPageQuery;
import com.whn.content_service.domain.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whn.content_service.dto.PageQuery;
import com.whn.content_service.po.ArticlePo;

import java.util.List;

/**
* @author wangRich
* @description 针对表【article】的数据库操作Service
* @createDate 2022-12-26 12:01:41
*/
public interface ArticleService extends IService<Article> {

    /**
     *  创建更新文章
     * @param articlePo articlePo
     * @return 创建更新状态
     */
    Boolean createOrUpdateArticle(ArticlePo articlePo);

    /**
     *  根据文章标题和内容模糊查询所有文章
     * @param  pageQuery 分页查询
     * @return 文章列表
     */
    Page<Article> findAllArticleByTitleAndContent(PageQuery pageQuery);

    /**
     *  根据文章标题和内容模糊查询所有文章
     * @param  pageQuery 分页查询
     * @return 文章列表
     */
    Page<Article> findAllArticle(PageQuery pageQuery);

    /**
     *  根据文章标签文章
     * @param  pageQuery 分页查询
     * @return 文章列表
     */
    Page<Article> findAllArticleByArticleTagId(ArticleTagFindPageQuery pageQuery);

    /**
     *  根据id批量删除文章
     * @param ids id列表
     * @return 删除状态
     */
    Boolean deleteArticleByIds(List<Integer> ids);

    /**
     *  根据id列表查询
     * @param ids ids
     * @return 列表
     */
    List<Article> findArticleById(List<Integer> ids);

    /**
     *  根据id表查询
     * @param id id
     * @return 列表
     */
    Article findArticleById(Integer id);


}
