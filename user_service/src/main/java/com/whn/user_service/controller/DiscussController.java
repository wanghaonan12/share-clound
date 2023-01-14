package com.whn.user_service.controller;


import com.whn.user_service.auth.AuthAspect;
import com.whn.user_service.auth.CheckLogin;
import com.whn.user_service.commons.ResponseResult;
import com.whn.user_service.dto.DiscussDto;
import com.whn.user_service.dto.FindDiscussPageQuery;
import com.whn.user_service.dto.PageQuery;
import com.whn.user_service.po.DiscussPo;
import com.whn.user_service.service.DiscussService;
import com.whn.user_service.utils.MyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/12/31 17:24
 */
@Api(tags = {"文章评论模块api"})
@RequestMapping("/discuss")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DiscussController {

    private final DiscussService discussService;
    private final AuthAspect authAspect;

    @CheckLogin
    @PostMapping("/createOrUpdate")
    @ApiOperation(value = "创建或更新评论")
    public ResponseResult createOrUpDataDiscuss(@RequestBody DiscussDto discussDto) {
        DiscussPo discussPo = new DiscussPo();
        BeanUtils.copyProperties(discussDto, discussPo);
        discussPo.setUserId(authAspect.getAttributeId());
        Boolean status = discussService.createOrUpdateArticleTag(discussPo);
        return MyUtils.customReturn(status);
    }

    @PostMapping("/get")
    @ApiOperation(value = "根据文章di获取评论")
    public ResponseResult getDiscuss(@RequestBody FindDiscussPageQuery findDiscussPageQuery) {
        return ResponseResult.success(discussService.findAllDiscussByArticleId(findDiscussPageQuery));
    }

    @CheckLogin
    @DeleteMapping("/delete")
    @ApiOperation(value = "根据id删除评论")
    public ResponseResult deleteDiscuss(@RequestBody List<Integer> discussIds) {
        Boolean status = discussService.deleteDiscussByIds(discussIds);
        return MyUtils.customReturn(status);
    }
}
