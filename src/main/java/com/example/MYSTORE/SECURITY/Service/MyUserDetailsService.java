package com.example.MYSTORE.SECURITY.Service;

import com.example.MYSTORE.SECURITY.Model.Role;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomUserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private CustomUserRepositoryImpl customUserRepository;

    private Collection<? extends GrantedAuthority> ToGrantedAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole_name())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = customUserRepository.getUserByEmail(email);
        if (user != null && user.getMyEnabled()){
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(), ToGrantedAuthorities(user.getRoles()));
        } throw new UsernameNotFoundException(String.format("User by email not found or user by email not enabled"));
    }
}
