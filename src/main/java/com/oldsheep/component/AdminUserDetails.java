package com.oldsheep.component;

import com.oldsheep.entity.Permission;
import com.oldsheep.entity.Users;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AdminUserDetails implements UserDetails {
    private Users users;
    private List<Permission> permissionList;

    public AdminUserDetails(Users user, List<Permission> permissionList) {
        this.users = user;
        this.permissionList = permissionList;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> collect = permissionList.stream()
                .filter(permission -> permission.getPermTag() != null)
                .map(permission -> new SimpleGrantedAuthority(permission.getPermTag()))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
