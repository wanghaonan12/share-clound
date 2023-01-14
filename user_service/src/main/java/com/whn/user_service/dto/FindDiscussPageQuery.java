package com.whn.user_service.dto;

import lombok.Data;

/**
 * @author : WangRich
 * @description : description
 * @date : 2022/12/31 14:24
 */
@Data
public class FindDiscussPageQuery {

    private int pageNum = 1;

    private int pageSize = 20;

    private Integer articleId;
}
