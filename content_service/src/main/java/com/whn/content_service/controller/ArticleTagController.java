package com.whn.content_service.controller;

import com.whn.content_service.auth.CheckLogin;
import com.whn.content_service.commons.ResponseResult;
import com.whn.content_service.domain.ArticleTag;
import com.whn.content_service.po.ArticleTagPo;
import com.whn.content_service.service.ArticleTagService;
import com.whn.content_service.utils.MyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @author : WangRich
 * @description : description
 * @date : 2022/12/31 18:00
 */
@Api(tags = {"文章标签api"})
@RequestMapping("/article_tage")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleTagController {
    private final ArticleTagService articleTagService;

    @CheckLogin
    @PostMapping("/create")
    @ApiOperation(value = "创建标签")
    public ResponseResult createArticleTag(@RequestBody ArticleTagPo articleTagPo) {
        Boolean status = articleTagService.createOrUpdateArticleTag(articleTagPo);
        return MyUtils.customReturn(status);
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取所有标签")
    public ResponseResult getAllArticleTags(){
        List<ArticleTag> allArticleTags = articleTagService.findAllArticleTags();
        return ResponseResult.success(allArticleTags);
    }

    @CheckLogin
    @DeleteMapping("/delete/{articleTagId}")
    @ApiOperation(value = "根据标签id删除标签")
    public ResponseResult deleteArticleTag(@PathVariable("articleTagId") Integer articleTagId) {
        Boolean status = articleTagService.deleteArticleTagByIds(Collections.singletonList(articleTagId));
        return MyUtils.customReturn(status);
    }
}
