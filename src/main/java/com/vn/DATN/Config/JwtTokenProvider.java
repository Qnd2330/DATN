package com.vn.DATN.Config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;
    private SecretKey key;

    @PostConstruct
    public void init() {
        // Kiểm tra xem secretKey có độ dài ít nhất 512 bits không
        if (secretKey != null && secretKey.length() >= 64) {
            this.key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        } else {
            // Tạo key an toàn cho HS512 nếu secretKey không hợp lệ
            this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        }
    }
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("authorities", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(key)
                .compact();
    }

    // Trích xuất thông tin từ token
    public String getUsernameFromToken(String token) {
        Claims claims = getClaims(token);
        return claims != null ? claims.getSubject() : null;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims == null || claims.getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("JWT token is null or empty");
        }
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}
