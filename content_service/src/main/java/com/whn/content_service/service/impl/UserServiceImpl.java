package com.whn.content_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whn.content_service.domain.User;
import com.whn.content_service.mapper.UserMapper;
import com.whn.content_service.po.UserPo;
import com.whn.content_service.server.WebSocketServer;
import com.whn.content_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

/**
 * @author wangRich
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2022-12-25 21:02:42
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final WebSocketServer webscoketServer;

    @Override
    public User findUserByEmail(String email) {
        QueryWrapper<User> queryMapper = new QueryWrapper<>();
        queryMapper.eq("email", email);
        return getOne(queryMapper);
    }

    @Override
    public User findUserById(String id) {
        return getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changeBounds(String userId, String event, Integer value) throws Exception {
        User user = findUserById(userId);
        Integer bonus = user.getBonus();
        bonus = Integer.parseInt(event) > 0 ? bonus + value : bonus - value;
        if (bonus < 0) {
            throw new Exception("积分不足");
        }
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userId);
        updateWrapper.set("bonus", bonus);
        return update(updateWrapper);
    }
}




