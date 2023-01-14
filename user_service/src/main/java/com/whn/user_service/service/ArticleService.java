package com.whn.user_service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whn.user_service.domain.Article;

/**
* @author wangRich
* @description 针对表【article】的数据库操作Service
* @createDate 2022-12-26 12:01:41
*/
public interface ArticleService extends IService<Article> {

    /**
     *  根据id表查询
     * @param id id
     * @return 列表
     */
    Article findArticleById(Integer id);

    /**
     *  更新文章
     * @param article 文章
     * @return 更新状态
     */
    Boolean updateArticleById(Article article);
}
