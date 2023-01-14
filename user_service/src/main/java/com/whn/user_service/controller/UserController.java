package com.whn.user_service.controller;

import com.whn.user_service.auth.AuthAspect;
import com.whn.user_service.auth.CheckLogin;
import com.whn.user_service.commons.ResponseResult;
import com.whn.user_service.commons.ResultCode;
import com.whn.user_service.domain.User;
import com.whn.user_service.dto.CreateUserDto;
import com.whn.user_service.dto.UpdateUserDto;
import com.whn.user_service.po.UserPo;
import com.whn.user_service.service.UserService;
import com.whn.user_service.utils.JwtOperator;
import com.whn.user_service.utils.MyUtils;
import com.whn.user_service.utils.RedisOperator;
import com.whn.user_service.utils.VerificationCode;
import com.whn.user_service.dto.LoginDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/12/27 14:22
 */
@Api(tags = {"用户模块api"})
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;
    private final JwtOperator jwtOperator;
    private final VerificationCode verificationCode;
    private final RedisOperator redisOperator;


    private final AuthAspect authAspect;

    @PostMapping("/create")
    @ApiOperation(value = "创建账户")
    public ResponseResult createUser(@RequestBody CreateUserDto createUserDto) {
        ResponseResult responseResult = ResponseResult.success("创建成功");
        String code = createUserDto.getCode();
        String email = createUserDto.getEmail();
//        在redis中查找email信息
        Boolean status = verificationCode.compareCode(email, code);
        if (status) {
            UserPo userPo = UserPo.builder().email(createUserDto.getEmail()).password(createUserDto.getPassword()).build();
            //根据email从查找用户判断存在与否
            User userByEmail = userService.findUserByEmail(email);
            if (userByEmail == null) {
                Boolean aBoolean = userService.saveOrUpdateUser(userPo);
                if (!aBoolean) {
                    responseResult = ResponseResult.failure(ResultCode.DATABASE_ERROR);
                }
            } else {
                responseResult = ResponseResult.failure(ResultCode.DATA_ALREADY_EXISTED);
            }
        } else {
            long l = redisOperator.activeTime(email);
            if (0 >= l) {
                responseResult = ResponseResult.failure(ResultCode.USER_CODE_TIMEOUT);
            } else {
                responseResult = ResponseResult.failure(ResultCode.USER_VERIFY_CODE_ERROR);
            }
        }
        return responseResult;
    }

    @PostMapping("/update")
    @CheckLogin
    @ApiOperation(value = "创建账户")
    public ResponseResult updateUser(@RequestBody UpdateUserDto updateUserDto) {
        UserPo userPo=new UserPo();
        BeanUtils.copyProperties(updateUserDto, userPo);
        userPo.setId(authAspect.getAttributeId());
        Boolean status = userService.saveOrUpdateUser(userPo);
        return MyUtils.customReturn(status,"修改成功","修改失败");
    }

    @PostMapping("/forget")
    @ApiOperation(value = "忘记密码")
    public ResponseResult forgetPassword(@RequestBody CreateUserDto createUserDto) {
        ResponseResult responseResult = ResponseResult.success("修改成功");
        String code = createUserDto.getCode();
        String email = createUserDto.getEmail();
//        在redis中查找email信息
        Boolean status = verificationCode.compareCode(email, code);
        if (status) {
            User userByEmail = userService.findUserByEmail(email);
            if (userByEmail!=null) {
                UserPo userPo =new UserPo();
                BeanUtils.copyProperties(userByEmail,userPo);
                userPo.setPassword(createUserDto.getPassword());
                Boolean aBoolean = userService.saveOrUpdateUser(userPo);
//                修改失败返回数据库操作异常
                if (!aBoolean) {
                    responseResult = ResponseResult.failure(ResultCode.DATABASE_ERROR);
                }
            }
        } else {
            long l = redisOperator.activeTime(email);
            if (0 >= l) {
                responseResult = ResponseResult.failure(ResultCode.USER_CODE_TIMEOUT);
            } else {
                responseResult = ResponseResult.failure(ResultCode.USER_VERIFY_CODE_ERROR);
            }
        }
        return responseResult;
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public ResponseResult login(@RequestBody LoginDto loginVo) {
        User user = userService.findUserByEmailAndPassword(loginVo.getEmail(), loginVo.getPassword());
        ResponseResult responseResult;
        if (user != null) {
            Map<String, Object> claims = new HashMap<>(16);
            claims.put("id", user.getId());
            claims.put("email", user.getEmail());
            String token = jwtOperator.generateToken(claims);
            Map<String, Object> response = new HashMap<>(16);
            response.put("token", token);
            response.put("id", user.getId());
            responseResult = ResponseResult.success(response);
        } else {
            responseResult = ResponseResult.failure(ResultCode.USER_PASSWORD_ERROR);
        }
        return responseResult;
    }

    @GetMapping("/verification")
    @ApiOperation(value = "获取验证码")
    public ResponseResult findVerifyCode(@RequestParam String email) {
        Boolean status = verificationCode.sendCode(email);
        return status ? ResponseResult.success("发送成功") : ResponseResult.failure(ResultCode.SMS_ERROR);
    }


}
