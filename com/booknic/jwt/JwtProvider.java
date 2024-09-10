package com.booknic.jwt;

import com.booknic.entity.Admin;
import com.booknic.entity.JwtSubject;
import com.booknic.entity.User;
import com.booknic.repository.AdminRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.file.InvalidPathException;
import java.util.Date;
import java.util.Objects;

@Service
public class JwtProvider {
    private final JwtProperties jwtProperty;
    private static SecretKey secretKey;

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    @Autowired
    private AdminRepository adminRepository;

    public JwtProvider(JwtProperties jwtProperty) {
        this.jwtProperty = jwtProperty;
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtProperty.getSecretKey()));
    }
    public static String extractToken(String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)){
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
    public String generateToken(JwtSubject subject, Long expiration, String role){
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(secretKey, Jwts.SIG.HS256)
                .issuer(jwtProperty.getIssuer())
                .subject(subject.getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .claim("id", subject.getId())
                .claim("name", subject.getName())
                .claim("role", role);
        if(role.equals("staff")){
            String library = adminRepository.findLibraryByIdAndName(subject.getId(), subject.getName());
            jwtBuilder.claim("library", library);
        }
        return jwtBuilder.compact();
    }
    public String generateAccessToken(JwtSubject subject, String role){
        return generateToken(subject, jwtProperty.getAccessExpiration(), role);
    }
    public String generateRefreshToken(JwtSubject subject, String role){
        return generateToken(subject, jwtProperty.getRefreshExpiration(), role);
    }
    public boolean verifyToken(String token){
        try{
            parseClaims(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    private static Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String refreshAccessToken(String accessToken, String refreshToken){
        String fromAccessToken = getId(accessToken, false);
        String fromRefreshToken = getId(refreshToken, true);
        String role = extractRole(accessToken);
        if(!Objects.equals(fromRefreshToken, fromAccessToken)){
            throw new IllegalArgumentException("Invalid path detected.");
        }
        return generateAccessToken(extractUserInfo(refreshToken), role);
    }
    public static String getId(String token, boolean expirationCheck){
        try{
            return getId(parseClaims(token));
        }catch (ExpiredJwtException e){
            if (expirationCheck) throw new IllegalArgumentException("Invalid or expired refresh token.");
            else return getId(e.getClaims());
        }
    }
    private static String getId(Claims payload){
        return (String) payload.get("id");
    }
    public static String extractRole(String token) {
        Claims payload = parseClaims(token);
        return (String) payload.get("role");
    }
    public User extractUserInfo(String token){
        Claims payload = parseClaims(token);
        return User.builder()
                .id(getId(payload))
                .name((String) payload.get("name"))
                .build();

    }
    public Admin extractAdminInfo(String token){
        Claims payload = parseClaims(token);
        return Admin.builder()
                .id(getId(payload))
                .name((String) payload.get("name"))
                .build();

    }
}
