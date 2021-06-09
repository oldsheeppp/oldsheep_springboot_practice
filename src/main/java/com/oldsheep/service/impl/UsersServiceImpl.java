package com.oldsheep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oldsheep.configuration.JwtConfig;
import com.oldsheep.entity.UserRole;
import com.oldsheep.entity.Users;
import com.oldsheep.mapper.UsersMapper;
import com.oldsheep.service.UserRoleService;
import com.oldsheep.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oldsheep
 * @since 2021-05-23
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
    @Lazy
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtConfig jwtConfig;
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsersService usersService;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtConfig.generateToken(userDetails);
        }catch (AuthenticationException e) {

        }
        return token;
    }


    @Override
    public Users register(Users usersParam) {
        Users user = new Users();
        user.setUsername(usersParam.getUsername());
        user.setPassword(usersParam.getPassword());
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        usersService.save(user);
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        UserRole userRole = new UserRole();
        userRole.setUserId(usersService.getOne(queryWrapper).getId());
        userRole.setRoleId(2);
        List<UserRole> list = userRoleService.list();
        if (list.size() == 0) {
            userRole.setId(1);
        }
        else {
            userRole.setId(list.size() + 1);
        }
        userRoleService.save(userRole);
        return user;
    }
}
