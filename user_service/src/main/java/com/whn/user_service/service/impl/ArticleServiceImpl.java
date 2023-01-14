package com.whn.user_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whn.user_service.domain.Article;
import com.whn.user_service.mapper.ArticleMapper;
import com.whn.user_service.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author wangRich
 * @description 针对表【article】的数据库操作Service实现
 * @createDate 2022-12-26 12:01:41
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Override
    public Article findArticleById(Integer id) {
        return getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateArticleById(Article article) {
        return updateById(article);
    }
}




