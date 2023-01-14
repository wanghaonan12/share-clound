package com.whn.user_service.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whn.user_service.auth.AuthAspect;
import com.whn.user_service.auth.CheckLogin;
import com.whn.user_service.commons.ResponseResult;
import com.whn.user_service.commons.ResultCode;
import com.whn.user_service.domain.Praise;
import com.whn.user_service.dto.PageQuery;
import com.whn.user_service.po.PraisePo;
import com.whn.user_service.service.PraiseService;
import com.whn.user_service.utils.MyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * @author : WangRich
 * @description : description
 * @date : 2022/12/31 20:57
 */
@Api(tags = {"点赞模块api"})
@RequestMapping("/praise")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PraiseController {
    private final PraiseService praiseService;
    private final AuthAspect authAspect;

    @CheckLogin
    @PostMapping("/create")
    @ApiOperation(value = "点赞")
    public ResponseResult createPraise(@RequestParam Integer articleId){
        Praise praiseByUserIdAndArticleId = praiseService.findPraiseByUserIdAndArticleId(authAspect.getAttributeId(),articleId);
        if (praiseByUserIdAndArticleId==null) {
            PraisePo praisePo=new PraisePo();
            praisePo.setUserId(authAspect.getAttributeId());
            praisePo.setArticleId(articleId);
            Boolean status = praiseService.createPraise(praisePo);
            return MyUtils.customReturn(status);
        }else{
            Integer id = praiseByUserIdAndArticleId.getId();
            Boolean status = praiseService.deletePraiseById(Collections.singletonList(id));
            return MyUtils.customReturn(status);
        }
    }

    @CheckLogin
    @GetMapping("/get")
    @ApiOperation(value = "根据用户id获取点赞列表")
    public ResponseResult getAllPraise(@RequestBody PageQuery pageQuery){
        Page<Praise> praiseListByUserId = praiseService.findPraiseListByUserId(pageQuery,authAspect.getAttributeId());
        return ResponseResult.success(praiseListByUserId);
    }
}
