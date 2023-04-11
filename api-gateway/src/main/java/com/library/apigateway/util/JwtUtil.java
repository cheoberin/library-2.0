package com.library.apigateway.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.List;

@Component
public class JwtUtil {
    private static final String SECRETE_KEY = "576D5A7134743777217A25432646294A404E635266556A586E3272357538782F413F4428472B4B6150645367566B5970337336763979244226452948404D6351";
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRETE_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void validateToken(String token) {
      Jwts.parserBuilder()
              .setSigningKey(getSignInKey())
              .build()
              .parseClaimsJws(token);
    }

    public List<String> getRoles(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        List<String> roles = claims.get("roles", List.class);
        return roles;
    }

}
