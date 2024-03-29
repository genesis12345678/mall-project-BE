package com.example.mallapi.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.Map;

import static com.example.mallapi.Const.JwtConst.*;

@Slf4j
public class JWTUtil {

    private static final String key = "5648451321548975465489464564321623118";

    public static String generateToken(Map<String, Object> valueMap, int min) {
        SecretKey key;

        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new CustomJWTException(e.getMessage());
        }

        return Jwts.builder()
                   .setHeader(Map.of("typ", "JWT"))
                   .setClaims(valueMap)
                   .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                   .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                   .signWith(key)
                   .compact();
    }

    public static Map<String, Object> validateToken(String token) {
        Map<String, Object> claim;
        try {
            SecretKey key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes(StandardCharsets.UTF_8));
            claim = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (MalformedJwtException malformedJwtException) {
            throw new CustomJWTException(MALFORMED_EX);

        } catch (ExpiredJwtException expiredJwtException) {
            throw new CustomJWTException(EXPIRED_EX);

        } catch (InvalidClaimException invalidClaimException) {
            throw new CustomJWTException(INVALID_EX);

        } catch (JwtException jwtException) {
            throw new CustomJWTException(JWT_EX);

        } catch (Exception e) {
            throw new CustomJWTException(ERROR);
        }
        return claim;
    }
}
