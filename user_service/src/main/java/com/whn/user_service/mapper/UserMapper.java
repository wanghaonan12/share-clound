package com.whn.user_service.mapper;

import com.whn.user_service.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wangRich
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-12-25 21:02:42
* @Entity com.whn.user_service.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




