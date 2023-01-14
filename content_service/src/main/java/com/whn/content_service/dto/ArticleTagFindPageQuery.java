package com.whn.content_service.dto;

import lombok.Data;

import java.util.List;

/**
 * @author : WangRich
 * @description : description
 * @date : 2022/12/31 14:24
 */
@Data
public class ArticleTagFindPageQuery {

    private int pageNum = 1;

    private int pageSize = 20;

    private List<String> content;
}
