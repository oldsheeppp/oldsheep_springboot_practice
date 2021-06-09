package com.oldsheep.service;

import com.oldsheep.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oldsheep
 * @since 2021-05-23
 */
public interface UsersService extends IService<Users> {
    String login(String username, String password);

    Users register(Users usersParam);
}
