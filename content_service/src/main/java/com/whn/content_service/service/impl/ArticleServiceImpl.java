package com.whn.content_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whn.content_service.dto.ArticleTagFindPageQuery;
import com.whn.content_service.domain.Article;
import com.whn.content_service.dto.PageQuery;
import com.whn.content_service.po.ArticlePo;
import com.whn.content_service.service.ArticleService;
import com.whn.content_service.mapper.ArticleMapper;
import com.whn.content_service.utils.MinIoTemplate;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wangRich
 * @description 针对表【article】的数据库操作Service实现
 * @createDate 2022-12-26 12:01:41
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Resource
    MinIoTemplate minIoTemplate;

    @Override
    public Boolean createOrUpdateArticle(ArticlePo articlePo) {
        if (articlePo.getId() != null) {
            String accessoryUrl = findArticleById(articlePo.getId()).getAccessoryUrl();
            if (!accessoryUrl.isBlank()) {
                minIoTemplate.delete(accessoryUrl);
            }
        }
        Article article = new Article();
        BeanUtils.copyProperties(articlePo, article);
        return saveOrUpdate(article);
    }

    @Override
    public Page<Article> findAllArticleByTitleAndContent(PageQuery pageQuery) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<Article>();
        queryWrapper.like("title", pageQuery.getContent()).or().like("content", pageQuery.getContent());
        Page<Article> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        return page(page, queryWrapper);
    }

    @Override
    public Page<Article> findAllArticle(PageQuery pageQuery) {
        StopWatch started = StopWatch.createStarted();
        Page<Article> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        started.stop();
        System.out.println(started.getTime(TimeUnit.MICROSECONDS));
        return page(page);

    }

    @Override
    public Page<Article> findAllArticleByArticleTagId(ArticleTagFindPageQuery pageQuery) {
        Page<Article> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("article_tag_id", pageQuery.getContent());
        return page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteArticleByIds(List<Integer> ids) {
        List<Article> articleByIds = findArticleById(ids);
        List<String> pathList = new ArrayList<>();
        articleByIds.forEach(
                article -> {
                    pathList.add(article.getAccessoryUrl());
                }
        );
        minIoTemplate.delete(pathList);
        return removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Article> findArticleById(List<Integer> ids) {
        return listByIds(ids);
    }

    @Override
    public Article findArticleById(Integer id) {
        return getById(id);
    }


}




