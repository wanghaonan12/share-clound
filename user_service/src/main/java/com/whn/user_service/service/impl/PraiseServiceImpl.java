package com.whn.user_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whn.user_service.server.WebSocketServer;
import com.whn.user_service.domain.Article;
import com.whn.user_service.domain.Praise;
import com.whn.user_service.dto.PageQuery;
import com.whn.user_service.mapper.PraiseMapper;
import com.whn.user_service.po.PraisePo;
import com.whn.user_service.service.ArticleService;
import com.whn.user_service.service.PraiseService;
import com.whn.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangRich
 * @description 针对表【praise】的数据库操作Service实现
 * @createDate 2022-12-26 12:01:41
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PraiseServiceImpl extends ServiceImpl<PraiseMapper, Praise> implements PraiseService {

    private final WebSocketServer websocketServer;
    private final UserService userService;
    private final ArticleService articleService;


    @Override
    public Boolean createPraise(PraisePo praisePo) {
        Praise praise = new Praise();
        BeanUtils.copyProperties(praisePo, praise);
//        获取文章点赞的用户名提供消息通知服务
        String praiseUser = userService.findUserById(praisePo.getUserId()).getName();
        Article articleById = articleService.findArticleById(praisePo.getArticleId());
        String content = praiseUser + "很是欣赏您的文章，并对" + articleById.getTitle() + "点赞";
//        消息通知
        websocketServer.sendMessage(praisePo.getUserId(), content, articleById.getUserId());
//        改变点赞数
        articleById.setPraiseCount(articleById.getPraiseCount()+1);
        articleService.updateArticleById(articleById);
        return save(praise);
    }

    @Override
    public Page<Praise> findPraiseListByUserId(PageQuery pageQuery, String userId) {
        QueryWrapper<Praise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Page<Praise> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        return page(page, queryWrapper);
    }

    @Override
    public Praise findPraiseListByUserIdAndArticleId(Integer userId, Integer articleId) {
        QueryWrapper<Praise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("article_id", articleId);
        return getOne(queryWrapper);
    }

    @Override
    public Boolean deletePraiseById(List<Integer> ids) {
        QueryWrapper<Praise> praiseQueryWrapper = new QueryWrapper<>();
        praiseQueryWrapper.in("id", ids);
        List<Praise> list = list(praiseQueryWrapper);
        List<Integer> articleIds = new ArrayList<>();
        list.forEach(praise -> {
            articleIds.add(praise.getArticleId());
        });
        UpdateWrapper<Article> articleWrapper = new UpdateWrapper<>();
        articleWrapper.in("id",articleIds);
        articleWrapper.setSql("praise_count=praise_count - 1");
        articleService.update(articleWrapper);
        return removeByIds(ids);
    }

    @Override
    public Praise findPraiseByUserIdAndArticleId(String userId, Integer articleId) {
        QueryWrapper<Praise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("article_id", articleId);
        return getOne(queryWrapper);
    }
}




