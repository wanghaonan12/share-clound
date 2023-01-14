package com.whn.user_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whn.user_service.server.WebSocketServer;
import com.whn.user_service.domain.User;
import com.whn.user_service.po.UserPo;
import com.whn.user_service.service.UserService;
import com.whn.user_service.mapper.UserMapper;
import com.whn.user_service.utils.Encrypt;
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

    private final Encrypt encrypt;
    private final WebSocketServer webscoketServer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdateUser(UserPo userPo) {
        User user = new User();
        BeanUtils.copyProperties(userPo, user);
//        如果 id等于空说明是创建需要一个uuid
        if (user.getId() == null) {
            String id = UUID.randomUUID().toString().replaceAll("-", "");
            user.setId(id);
            webscoketServer.sendMessage("恭喜您成功创建知识分享账号，感谢您的信任和使用，祝您体验愉快！", id);
        }

//        如果就会重新对密码加密 空的话就不会
        if (!(user.getPassword()==null)) {
            user.setPassword(encrypt.encode(userPo.getPassword()));
            webscoketServer.sendMessage("你的账号信息被修改，如不是本人操作请尽快重新修改密码！", userPo.getId());
        }
        if (user.getName()==null) {
            user.setName(user.getEmail());
        }
        if (!(user.getAvatar()==null)) {
            int ran2 = new Random(1).nextInt(55);
            user.setAvatar("https://wang-rich.oss-cn-hangzhou.aliyuncs.com/avatar/avatar%20(" + ran2 + ").png");
        }
        return saveOrUpdate(user);
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        QueryWrapper<User> queryMapper = new QueryWrapper<>();
        queryMapper.eq("email", email);
        queryMapper.eq("password", encrypt.encode(password));
        return getOne(queryMapper);
    }

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
        updateWrapper.set("bounds", bonus);
        return update(updateWrapper);
    }
}




