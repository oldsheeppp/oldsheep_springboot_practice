package com.oldsheep.configuration;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oldsheep.component.AdminUserDetails;
import com.oldsheep.component.JwtAuthenticationTokenFilter;
import com.oldsheep.component.RestAuthenticationEntryPoint;
import com.oldsheep.component.RestfulAccessDeniedHandler;
import com.oldsheep.entity.Permission;
import com.oldsheep.entity.RolePermission;
import com.oldsheep.entity.UserRole;
import com.oldsheep.entity.Users;
import com.oldsheep.service.PermissionService;
import com.oldsheep.service.RolePermissionService;
import com.oldsheep.service.UserRoleService;
import com.oldsheep.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UsersService usersService;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private PermissionService permissionService;

    public SecurityConfig(UsersService usersService) {
        this.usersService = usersService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()// ??????????????????JWT????????????????????????csrf
                .disable()
                .sessionManagement()// ??????token??????????????????session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, // ????????????????????????????????????????????????
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**"
                )
                .permitAll()
                .antMatchers("/users/login", "/users/register")// ????????????????????????????????????
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)//??????????????????????????????options??????
/*                .permitAll()
                .antMatchers("/**")//???????????????????????????*/
                .permitAll()
                .anyRequest()// ???????????????????????????????????????????????????
                .authenticated();
        // ????????????
        httpSecurity.headers().cacheControl();
        // ??????JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //????????????????????????????????????????????????
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            QueryWrapper<Users> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username);
            Users user = usersService.getOne(wrapper);
            if (user != null) {
                QueryWrapper<UserRole> wrapper1 = new QueryWrapper<>();
                wrapper1.eq("user_id", user.getId());
                UserRole userRole = userRoleService.getOne(wrapper1);
                QueryWrapper<RolePermission> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("role_id", userRole.getRoleId());
                List<RolePermission> rolePermissions = rolePermissionService.list(wrapper2);
                List<Permission> permissionList = new ArrayList<>();
                for (RolePermission rolePermission : rolePermissions) {
                    QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("id", rolePermission.getPermissionId());
                    Permission permission = permissionService.getOne(queryWrapper);
                    permissionList.add(permission);
                }
                return new AdminUserDetails(user, permissionList);
            }
            throw new UsernameNotFoundException("????????????????????????");
        };
    }


    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
