package com.whn.content_service.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2023/1/8 21:42
 */
@Data
@Builder
public class AcceptAnswerDto {

    Integer answerId;

    String answerUserId;
    Integer articleId;
}
