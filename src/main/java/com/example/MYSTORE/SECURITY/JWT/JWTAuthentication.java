package com.example.MYSTORE.SECURITY.JWT;

import com.example.MYSTORE.SECURITY.Model.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
public class JWTAuthentication implements Authentication {
    private String email;
    private String username;
    private boolean authenticated;
    private Collection<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(
                role -> new SimpleGrantedAuthority(role.getRole_name())).collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return this.email;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return username;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }
}
