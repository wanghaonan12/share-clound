package com.whn.user_service.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2023/1/9 13:43
 */
@Data
@Builder
public class DiscussVo {
    Integer id;
    String content;
    String userId;
    String avatar;
    String praiseCount;
}
