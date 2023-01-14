package com.whn.content_service;

import com.whn.content_service.domain.Article;
import com.whn.content_service.domain.Notice;
import com.whn.content_service.dto.PageQuery;
import com.whn.content_service.mapper.BrowsHistoryMapper;
import com.whn.content_service.service.BrowsHistoryService;
import com.whn.content_service.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2023/1/2 14:48
 */
@Slf4j
@SpringBootTest
public class test {
    @Resource
    private BrowsHistoryMapper browsHistoryMapper;

    @Test
    public void testFile() throws IOException {
//        byte[] bytes = new byte[]{2,1};
//        ArticleDto articleDto = ArticleDto.builder().articleTagId(2).file(new MockMultipartFile("name",bytes )).build();
//        ArticlePo articlePo = new ArticlePo();
//        BeanUtils.copyProperties(articleDto,articlePo);
//        System.out.println(articlePo);
//        List<Notice> noSentNoticeByReceiveUserId = noticeService.findNoSentNoticeByReceiveUserId("231231");
//        System.out.println(noSentNoticeByReceiveUserId);
//        PageQuery pageQuery=new PageQuery();
//        pageQuery.setPageNum(1);
//        pageQuery.setPageSize(10);
//        List<Article> articles = browsHistoryMapper.selectBrowsHistoryById("79c308ea630d4e6f8d647e8278962bea", pageQuery);
//        System.out.println(articles);
    }
}