package com.whn.user_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whn.user_service.domain.Article;
import com.whn.user_service.domain.Discuss;
import com.whn.user_service.domain.User;
import com.whn.user_service.dto.FindDiscussPageQuery;
import com.whn.user_service.dto.PageQuery;
import com.whn.user_service.mapper.DiscussMapper;
import com.whn.user_service.po.DiscussPo;
import com.whn.user_service.server.WebSocketServer;
import com.whn.user_service.service.ArticleService;
import com.whn.user_service.service.DiscussService;
import com.whn.user_service.service.UserService;
import com.whn.user_service.vo.DiscussVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author wangRich
* @description 针对表【discuss】的数据库操作Service实现
* @createDate 2022-12-26 12:01:41
*/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DiscussServiceImpl extends ServiceImpl<DiscussMapper, Discuss>
    implements DiscussService {

    private final WebSocketServer websocketServer;

    private final ArticleService articleService;
    private final UserService userService;
    private final DiscussMapper discussMapper;
    @Override
    public Boolean createOrUpdateArticleTag(DiscussPo discussPo) {
        Discuss discuss=new Discuss();
        BeanUtils.copyProperties(discussPo, discuss);
//        获取信息发送通知
        Article article = articleService.findArticleById(discussPo.getArticleId());
        String toUserId = article.getUserId();
        User userById = userService.findUserById(discuss.getUserId());
        String content=userById.getName()+ "对你的文章"+article.getTitle()+"评论了:"+discussPo.getCommentDetails();
        websocketServer.sendMessage(discuss.getUserId(), content,toUserId);
//        更新文章评论数量
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", article.getId());
        updateWrapper.setSql("discuss_count=discuss_count+1");
        articleService.update(updateWrapper);
        return saveOrUpdate(discuss);
    }

    @Override
    public List<DiscussVo> findAllDiscussByArticleId(FindDiscussPageQuery findDiscussPageQuery) {
        List<DiscussVo> discussById = discussMapper.getDiscussById(findDiscussPageQuery.getArticleId(), findDiscussPageQuery.getPageSize() , findDiscussPageQuery.getPageSize()* (findDiscussPageQuery.getPageNum()-1));
        return discussById;
    }

    @Override
    public Boolean deleteDiscussByIds(List<Integer> ids) {
        QueryWrapper<Discuss> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<Discuss> list = list(queryWrapper);
        List<Integer> articleIds = new ArrayList<>();
        list.forEach(discuss -> {
            articleIds.add(discuss.getArticleId());
        });
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", articleIds);
        updateWrapper.setSql("discuss_count=discuss_count-1");
        articleService.update(updateWrapper);
        return removeByIds(ids);
    }
}




