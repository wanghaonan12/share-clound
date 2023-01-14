package com.whn.content_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whn.content_service.domain.Article;
import com.whn.content_service.domain.BrowsHistory;
import com.whn.content_service.dto.PageQuery;
import com.whn.content_service.po.BrowsHistoryPo;
import com.whn.content_service.service.BrowsHistoryService;
import com.whn.content_service.mapper.BrowsHistoryMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author wangRich
* @description 针对表【brows_history】的数据库操作Service实现
* @createDate 2022-12-26 12:01:41
*/
@Service
public class BrowsHistoryServiceImpl extends ServiceImpl<BrowsHistoryMapper, BrowsHistory>
    implements BrowsHistoryService{

    @Resource
    private BrowsHistoryMapper browsHistoryMapper;
    @Override
    public Boolean createOrUpdateBrowsHistory(BrowsHistoryPo browsHistoryPo) {
        BrowsHistory browsHistory=new BrowsHistory();
        BeanUtils.copyProperties(browsHistoryPo,browsHistory);
        return saveOrUpdate(browsHistory);
    }


    @Override
    public  List<Article> findAllArticleTagsByUserId(PageQuery pageQuery, String userId) {
        List<Article> articles = browsHistoryMapper.selectBrowsHistoryById(userId, pageQuery.getPageSize()*pageQuery.getPageNum(),pageQuery.getPageSize());
        return articles;
    }

    @Override
    public Boolean deleteBrowsHistoryTagByIds(List<Integer> ids) {
        return removeByIds(ids);
    }

    @Override
    public BrowsHistory findByArticleIdAndUserId(String userId,Integer articleId) {
        QueryWrapper<BrowsHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("article_id", articleId);
        return getOne(queryWrapper);
    }
}




