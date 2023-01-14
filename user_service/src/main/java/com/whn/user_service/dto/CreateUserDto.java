package com.whn.user_service.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/12/27 15:35
 */
@Builder
@Data
public class CreateUserDto {
    String email;
    String password;
    String code;
}
