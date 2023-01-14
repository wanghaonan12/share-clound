package com.whn.content_service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whn.content_service.domain.Answer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whn.content_service.dto.PageQuery;
import com.whn.content_service.po.AnswerPo;

import java.util.List;

/**
* @author wangRich
* @description 针对表【answer】的数据库操作Service
* @createDate 2022-12-26 12:01:41
*/
public interface AnswerService extends IService<Answer> {
    /**
     *  创建更新回答
     * @param answerPo answerPo
     * @return  创建更新状态
     */
    Boolean createOrUpdateAnswer(AnswerPo answerPo);

    /**
     * 根据id查询
     * @param id 回答id
     * @return 回答
     */
    List<Answer> findAnswerById(Integer id);

    /**
     *  根据id批量删除
     * @param ids id列表
     * @return 删除状态
     */
    Boolean deleteAnswerByIds(List<Integer> ids);

    /**
     *  获取用户回答的所有问斩
     * @param userId
     * @return
     */
    Page<Answer> findAllAnswerByUserId(PageQuery pageQuery, String userId);
}
