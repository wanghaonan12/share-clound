package com.whn.user_service.service;

import com.whn.user_service.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whn.user_service.po.UserPo;

/**
* @author wangRich
* @description 针对表【user】的数据库操作Service
* @createDate 2022-12-25 21:02:42
*/
public interface UserService extends IService<User> {

    /**
     *  创建用户
     * @param user UserPo
     * @return 返回boolean确定创建成功
     */
    Boolean saveOrUpdateUser(UserPo user);


    /**
     *  根据用户手机号和密码进行查找
     * @param email 手机号
     * @param password 密码
     * @return 返回查找状态
     */
    User findUserByEmailAndPassword(String email,String password);

    /**
     *  根据用邮箱进行查找
     * @param email 邮箱
     * @return 返回查找状态
     */
    User findUserByEmail(String email);

    /**
     *  根据id查找用户
     * @param id 用户id
     * @return User对象
     */
    User findUserById(String id);

    /**
     * 改变用户积分
     *
     * @param userId 积分变动的用户id
     * @param event 积分变动状态 大于0增加 小于0减少
     * @param value 积分变动的值
     * @return 变动状态
     * @throws Exception 积分不足
     */
    Boolean changeBounds(String userId, String event, Integer value) throws Exception;

}
