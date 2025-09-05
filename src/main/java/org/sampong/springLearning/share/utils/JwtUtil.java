package org.sampong.springLearning.share.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.sampong.springLearning.share.enumerate.RoleStatus;
import org.sampong.springLearning.share.exception.CustomException;
import org.sampong.springLearning.users.controller.dto.response.Context;
import org.sampong.springLearning.users.controller.dto.response.JwtResponse;
import org.sampong.springLearning.users.model.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${jwt.secret:mySecretKey}")
    private String secret;
    @Value("${jwt.expiration:86400}") // 24 hours in seconds
    private Long expiration;

    public String getUserFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getAllClaimsFromToken(token));
    }

    private Claims getAllClaimsFromToken(String token)  {
        try{
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch(ExpiredJwtException e){
            System.out.println(e.getMessage());
            throw new CustomException(HttpStatus.UNAUTHORIZED, "token expired");
        }
    }

    /**
     * Generate token for user (admin)
     */
    public JwtResponse generateToken(Users req) throws JsonProcessingException {
        var context = new Context(req.getId(), req.getUsername(), req.getRoleStatus());
        var subject = new ObjectMapper().writeValueAsString(context);

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", getAuthorities(req.getRoleStatus()));

        return doGenerateToken(claims, subject);
    }

    /**
     * Core token generation logic
     */
    private JwtResponse doGenerateToken(Map<String, Object> claims, String subject) {
        Date created = new Date();
        Date expired = new Date(created.getTime() + (expiration * 60 * 1000));

        String jwtToken = Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expired)
                .signWith(getSigningKey())
                .compact();

        var response = new JwtResponse();
        response.setToken(jwtToken);
        response.setExpiredIn(expired.getTime());
        return response;
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    private Date getExpirationDate(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }



    private List<SimpleGrantedAuthority> getAuthorities(List<RoleStatus> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }
}
