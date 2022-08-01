package com.example.MYSTORE.SECURITY.Service;

import com.example.MYSTORE.SECURITY.Model.Role;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private Collection<? extends GrantedAuthority> ToGrantedAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole_name())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getMyEnabled() == true){
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(), ToGrantedAuthorities(user.getRoles()));
        } throw new UsernameNotFoundException(String.format("User by email not found or user by email not enabled"));
    }
}
