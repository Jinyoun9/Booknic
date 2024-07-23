package com.booknic.jwt;

import com.booknic.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.file.InvalidPathException;
import java.util.Date;

@Service
public class JwtProvider {
    private final JwtProperties jwtProperty;
    private static SecretKey secretKey;

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final String REDIRECT_PATH = "/articles";

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
    public String generateToken(User user, Long expiration){
        return Jwts.builder()
                .signWith(secretKey, Jwts.SIG.HS256)
                .issuer(jwtProperty.getIssuer())
                .subject(user.getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .claim("id", user.getId())
                .claim("name", user.getName())
                .compact();
    }
    public String generateAccessToken(User user){
        return generateToken(user, jwtProperty.getAccessExpiration());
    }
    public String generateRefreshToken(User user){
        return generateToken(user, jwtProperty.getRefreshExpiration());
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

        if(fromRefreshToken != fromAccessToken){
            throw new IllegalArgumentException("Invalid path detected.");
        }
        return generateAccessToken(extractUserInfo(refreshToken));
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
    public User extractUserInfo(String token){
        Claims payload = parseClaims(token);
        return User.builder()
                .id(getId(payload))
                .name((String) payload.get("name"))
                .build();

    }
}
