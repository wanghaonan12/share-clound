package com.whn.content_service.controller;

import com.whn.content_service.auth.AuthAspect;
import com.whn.content_service.auth.CheckLogin;
import com.whn.content_service.commons.ResponseResult;
import com.whn.content_service.domain.BrowsHistory;
import com.whn.content_service.dto.PageQuery;
import com.whn.content_service.po.BrowsHistoryPo;
import com.whn.content_service.service.BrowsHistoryService;
import com.whn.content_service.utils.MyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

/**
 * @author : WangRich
 * @description : description
 * @date : 2022/12/31 16:55
 */
@Api(tags = {"文章浏览记录模块api"})
@RequestMapping("/brows")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BrowsHistoryController {
    private final BrowsHistoryService browsHistoryService;
    private final AuthAspect authAspect;


    @PostMapping("/createOrUpdate")
    @ApiOperation(value = "创建浏览记录")
    public ResponseResult createBrowsHistory(@RequestParam Integer articleId) {
        ResponseResult responseResult = new ResponseResult(99999, "未获取用户token创建历史记录");
        String userId = authAspect.getAttributeId();
        if (!userId.isEmpty()) {
            BrowsHistory browsHistory = browsHistoryService.findByArticleIdAndUserId(userId, articleId);
            if (browsHistory != null) {
                boolean status = browsHistoryService.updateById(browsHistory);
                responseResult = MyUtils.customReturn(status);
            } else {
                BrowsHistoryPo browsHistoryPo = new BrowsHistoryPo();
                browsHistoryPo.setUserId(userId);
                browsHistoryPo.setArticleId(articleId);
                Boolean status = browsHistoryService.createOrUpdateBrowsHistory(browsHistoryPo);
                responseResult = MyUtils.customReturn(status);
            }
        }
        return responseResult;
    }

    @CheckLogin
    @GetMapping("/getByUserId")
    @ApiOperation(value = "根据用户id获取浏览历史")
    public ResponseResult getBrowsHistoryByUserId(@PathParam("pageQuery") PageQuery pageQuery) {
        return ResponseResult.success(browsHistoryService.findAllArticleTagsByUserId(pageQuery, authAspect.getAttributeId()));
    }

    @CheckLogin
    @DeleteMapping("/delete")
    @ApiOperation(value = "根据id删除浏览历史")
    public ResponseResult deleteBrowsHistory(@RequestBody List<Integer> ids) {
        Boolean status = browsHistoryService.deleteBrowsHistoryTagByIds(ids);
        return MyUtils.customReturn(status);
    }
}
