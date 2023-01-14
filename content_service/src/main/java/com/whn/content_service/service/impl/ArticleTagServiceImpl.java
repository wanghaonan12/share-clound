package com.whn.content_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whn.content_service.domain.ArticleTag;
import com.whn.content_service.po.ArticleTagPo;
import com.whn.content_service.service.ArticleTagService;
import com.whn.content_service.mapper.ArticleTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author wangRich
* @description 针对表【article_tag】的数据库操作Service实现
* @createDate 2022-12-26 12:01:41
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{


    @Override
    public Boolean createOrUpdateArticleTag(ArticleTagPo articleTagPo) {
        ArticleTag articleTag = new ArticleTag();
        BeanUtils.copyProperties(articleTagPo, articleTag);
        return saveOrUpdate(articleTag);
    }

    @Override
    public List<ArticleTag> findAllArticleTags() {
        return list();
    }

    @Override
    public Boolean deleteArticleTagByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}




