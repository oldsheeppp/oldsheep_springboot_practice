package com.oldsheep.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oldsheep.entity.*;
import com.oldsheep.service.*;
import com.oldsheep.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oldsheep
 * @since 2021-04-17
 */
@RestController
@Api(tags = "UsersController")
public class UsersController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private UserRoleService userRoleService;
    @Value("${config.jwt.head}")
    private String tokenHead;

    @ApiOperation("用户登录")
    @GetMapping("/users/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        RetResult<Map> stringRetResult = null;
        String token = null;
        try{
            token = usersService.login(username, password);
            if (token == null) {
                stringRetResult = RetResponse.makeErrRsp("账号不存在或密码不正确", false, null);
                return JSON.toJSONString(stringRetResult);
            }
        }catch (NullPointerException e) {
            stringRetResult =  RetResponse.makeErrRsp("账号不存在或密码不正确", false, null);
            return JSON.toJSONString(stringRetResult);
        }finally {
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            stringRetResult = RetResponse.makeOKRsp("success", true, tokenMap);
            return JSON.toJSONString(stringRetResult);
        }
    }

    @ApiOperation("用户注册")
    @GetMapping("/users/register")
    public String register(@RequestParam("username") String username,
                                      @RequestParam("password") String password) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Users user = usersService.getOne(wrapper);
        RetResult<String> rsp = null;
        if (user != null) {
            rsp = RetResponse.makeErrRsp("输入不合法", false, null);
        }
        else {
            user = new Users();
            user.setUsername(username);
            user.setPassword(password);
            usersService.register(user);
            rsp = RetResponse.makeOKRsp("success", true, "注册成功");
        }
        String s = JSON.toJSONString(rsp);
        return s;
    }

    @ApiOperation("氪金接口，改变权限")
    @PutMapping("/users/vip")
    public String getVIP(@RequestParam("username") String username) {
        RetResult<String> rsp = null;
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Users user = usersService.getOne(queryWrapper);
        if (user == null) {
            rsp = RetResponse.makeErrRsp("氪金失败", false, null);
        }
        else {
            QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", user.getId());
            UserRole userRole = userRoleService.getOne(wrapper);
            userRole.setRoleId(1);
            userRoleService.updateById(userRole);
            rsp = RetResponse.makeOKRsp("氪金成功", true, null);
        }
        String s = JSON.toJSONString(rsp);
        return s;
    }
}

