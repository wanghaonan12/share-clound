package com.whn.content_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whn.content_service.domain.Answer;
import com.whn.content_service.dto.PageQuery;
import com.whn.content_service.po.AnswerPo;
import com.whn.content_service.service.AnswerService;
import com.whn.content_service.mapper.AnswerMapper;
import com.whn.content_service.utils.MinIoTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangRich
 * @description 针对表【answer】的数据库操作Service实现
 * @createDate 2022-12-26 12:01:41
 */
@Service

public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer>
        implements AnswerService {

    @Resource
    MinIoTemplate minIoTemplate;

    @Override
    public Boolean createOrUpdateAnswer(AnswerPo answerPo) {
        Answer answer = new Answer();
        BeanUtils.copyProperties(answerPo, answer);
        return saveOrUpdate(answer);
    }

    @Override
    public List<Answer> findAnswerById(Integer id) {
        QueryWrapper<Answer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", id);
        return list(queryWrapper);
    }

    @Override
    public Boolean deleteAnswerByIds(List<Integer> ids) {
        QueryWrapper<Answer> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<Answer> answers = list(queryWrapper);
        List<String> answersUrl = new ArrayList<>();
        answers.forEach(answer -> {
            answersUrl.add(answer.getAccessoryUrl());
        });
        minIoTemplate.delete(answersUrl);
        return removeByIds(ids);
    }

    @Override
    public Page<Answer> findAllAnswerByUserId(PageQuery pageQuery,String userId) {
        QueryWrapper<Answer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Page<Answer> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        return page(page,queryWrapper);
    }
}




