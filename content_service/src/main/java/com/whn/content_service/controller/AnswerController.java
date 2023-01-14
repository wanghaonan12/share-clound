package com.whn.content_service.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whn.content_service.auth.AuthAspect;
import com.whn.content_service.auth.CheckLogin;
import com.whn.content_service.commons.ResponseResult;
import com.whn.content_service.domain.Answer;
import com.whn.content_service.domain.Article;
import com.whn.content_service.dto.AcceptAnswerDto;
import com.whn.content_service.dto.AnswerDto;
import com.whn.content_service.dto.PageQuery;
import com.whn.content_service.po.AnswerPo;
import com.whn.content_service.po.BoundsDetailPo;
import com.whn.content_service.server.WebSocketServer;
import com.whn.content_service.service.AnswerService;
import com.whn.content_service.service.ArticleService;
import com.whn.content_service.service.BoundsDetailService;
import com.whn.content_service.service.UserService;
import com.whn.content_service.utils.MinIoTemplate;
import com.whn.content_service.utils.MyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * @author : WangRich
 * @description : description
 * @date : 2022/12/31 16:09
 */
@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = {"问题回答api"})
public class AnswerController {
    private final AnswerService answerService;
    private final MinIoTemplate minIoTemplate;
    private final AuthAspect authAspect;
    private final ArticleService articleService;
    private final UserService userService;
    private final BoundsDetailService boundsDetailService;
    private final WebSocketServer webSocketServer;

    @CheckLogin
    @PostMapping("/createOrUpdate")
    @ApiOperation("回答问题")
    public ResponseResult createAnswer(AnswerDto answerDto) {
        MultipartFile file = answerDto.getFile();
        String fileUrl = "";
        if (!Objects.requireNonNull(file.getOriginalFilename()).isBlank()) {
            fileUrl = minIoTemplate.uploadImgFile("", file);
        }
        AnswerPo answerPo = new AnswerPo();
        answerPo.setAccessoryUrl(fileUrl);
        BeanUtils.copyProperties(answerDto, answerPo);
        answerPo.setUserId(authAspect.getAttributeId());
        Boolean status = answerService.createOrUpdateAnswer(answerPo);
        return MyUtils.customReturn(status);
    }

    @CheckLogin
    @GetMapping("/get/{articleId}")
    @ApiOperation("根据文章id获取回答内容")
    public ResponseResult getAnswer(@PathVariable Integer articleId) {
        List<Answer> answers = answerService.findAnswerById(articleId);
        return ResponseResult.success(answers);
    }

    @CheckLogin
    @DeleteMapping("/delete")
    @ApiOperation("删除回答")
    public ResponseResult deleteAnswer(@RequestBody List<Integer> answerIds) {
        Boolean status = answerService.deleteAnswerByIds(answerIds);
        return MyUtils.customReturn(status);
    }

    @CheckLogin
    @PostMapping("/accept")
    @ApiOperation(value = "采用回答")
    public ResponseResult acceptAnswer(@RequestBody AcceptAnswerDto acceptAnswerDto) {
        Article article = articleService.findArticleById(acceptAnswerDto.getArticleId());
        String name = userService.findUserById(acceptAnswerDto.getAnswerUserId()).getName();
        //TODO:采用回答流程---->确认采用--->发送通知给文章发布者--->自动扣除相应的奖励积分
        Integer award = article.getAward();
        BoundsDetailPo boss = BoundsDetailPo.builder().event("-1").userId(authAspect.getAttributeId()).value(award).description(article.getTitle()+"采用"+name+"回答。").build();
        BoundsDetailPo employee = BoundsDetailPo.builder().event("1").userId(acceptAnswerDto.getAnswerUserId()).description(article.getTitle()+"采用了你的回答。").value(award).build();
        Boolean status = false;
        try {
            Boolean aBoolean = boundsDetailService.createBounds(boss);
            Boolean aBoolean1 = boundsDetailService.createBounds(employee);
            status = aBoolean && aBoolean1;
            if (status) {
                String notice = "你的文章采用了" + name + "的回答，扣除积分" + award;
                webSocketServer.sendMessage(notice, authAspect.getAttributeId());
                String agratitude = "我的文章【" + article.getTitle() + "】采用了你的回答！" + award + "积分不成敬意，请你收下！";
                webSocketServer.sendMessage(authAspect.getAttributeId(), agratitude, acceptAnswerDto.getAnswerUserId());
                UpdateWrapper<Answer> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", acceptAnswerDto.getAnswerId());
                updateWrapper.set("accept", "true");
                status = answerService.update(updateWrapper);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //TODO: 短信发送，积分扣除
        return MyUtils.customReturn(status);
    }

    @CheckLogin
    @GetMapping("/get_myAnswer")
    @ApiOperation(value = "采用回答")
    public ResponseResult getMyAnswer(@RequestBody PageQuery pageQuery) {
        Page<Answer> allAnswerByUserId = answerService.findAllAnswerByUserId(pageQuery, authAspect.getAttributeId());
        return ResponseResult.success(allAnswerByUserId);
    }
}
