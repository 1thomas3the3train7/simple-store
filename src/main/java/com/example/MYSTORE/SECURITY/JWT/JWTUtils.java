package com.example.MYSTORE.SECURITY.JWT;

import com.example.MYSTORE.SECURITY.Model.Role;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JWTUtils {
    public static JWTAuthentication generate(Claims claims){
        final JWTAuthentication jwtInfoToken = new JWTAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setEmail(claims.getSubject());
        jwtInfoToken.setUsername(claims.get("username", String.class));
        return jwtInfoToken;
    }
    public static JWTAuthentication mazafakaGenerate(){
        final JWTAuthentication jwtAuthentication = new JWTAuthentication();
        jwtAuthentication.setRoles(List.of(new Role("ROLE_ADMIN")));
        jwtAuthentication.setEmail("adminemail");
        jwtAuthentication.setUsername("adminusername");
        return jwtAuthentication;
    }
    private static Collection<Role> getRoles(Claims claims) {
        final List<String> roles = claims.get("roles",List.class);
        return roles.stream().map(Role::new).collect(Collectors.toList());
    }
}
