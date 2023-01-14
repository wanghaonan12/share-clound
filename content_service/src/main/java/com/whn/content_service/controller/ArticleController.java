package com.whn.content_service.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whn.content_service.auth.AuthAspect;
import com.whn.content_service.auth.CheckLogin;
import com.whn.content_service.commons.ResponseResult;
import com.whn.content_service.dto.ArticleTagFindPageQuery;
import com.whn.content_service.domain.Article;
import com.whn.content_service.dto.ArticleDto;
import com.whn.content_service.dto.PageQuery;
import com.whn.content_service.po.ArticlePo;
import com.whn.content_service.service.ArticleService;
import com.whn.content_service.utils.MinIoTemplate;
import com.whn.content_service.utils.MyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Objects;

/**
 * @author : WangRich
 * @description : description
 * @date : 2022/12/31 13:57
 */
@Api(tags = {"文章模块api"})
@RequestMapping("/article")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleController {
    private final ArticleService articleService;
    private final AuthAspect authAspect;
   private  final  MinIoTemplate minIoTemplate;

    @CheckLogin
    @PostMapping("/createOrUpdate")
    @ApiOperation("文章添加")
    public ResponseResult addArticle(ArticleDto articleDto) {
        Boolean status = false;
        MultipartFile file = articleDto.getFile();
        String fileUrl = "";
        if (!Objects.requireNonNull(file.getOriginalFilename()).isBlank()) {
            fileUrl = minIoTemplate.uploadImgFile("", file);
        }
        ArticlePo articlePo = new ArticlePo();
        BeanUtils.copyProperties(articleDto, articlePo);
        articlePo.setUserId(authAspect.getAttributeId());
        status = articleService.createOrUpdateArticle(articlePo);
        return MyUtils.customReturn(status);
    }

    @PostMapping("/getAll")
    @ApiOperation("文章获取及模糊查询")
    public ResponseResult getAllArticle(@RequestBody PageQuery pageQuery) {
        Page<Article> article = pageQuery.getContent().isEmpty() ? articleService.findAllArticle(pageQuery) : articleService.findAllArticleByTitleAndContent(pageQuery);
        return ResponseResult.success(article);
    }

    @PostMapping("/getByTagId")
    @ApiOperation("文章获取及模糊查询")
    public ResponseResult getByTagId(@RequestBody ArticleTagFindPageQuery pageQuery) {
        return ResponseResult.success(articleService.findAllArticleByArticleTagId(pageQuery));
    }

    @CheckLogin
    @DeleteMapping("/delete")
    @ApiOperation("文章删除")
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteArticle(@RequestBody List<Integer> ids) {
        Boolean status = articleService.deleteArticleByIds(ids);
        return MyUtils.customReturn(status);
    }
}
