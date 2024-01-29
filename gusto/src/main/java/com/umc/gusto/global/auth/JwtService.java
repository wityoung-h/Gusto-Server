package com.umc.gusto.global.auth;

import com.umc.gusto.global.auth.model.Tokens;
import com.umc.gusto.global.config.secret.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtService implements InitializingBean {
    private static final String UUID = "uuid";
    private static SecretKey secretKey;

    private final UserDetailsService userDetailService;

    @Override
    public void afterPropertiesSet() throws Exception {
        // jwt 시크릿 키 설정
        secretKey = JwtConfig.RANDOM_SECRET_KEY;
    }

    public Tokens createToken(String userUUID) {
        Date now = new Date();

        String accessToken = Jwts.builder()
                .expiration(new Date(now.getTime() + JwtConfig.ACCESS_TOKEN_VALID_TIME))
                .subject("access-token")
                .claim(UUID, userUUID)
                .issuedAt(now)
                .signWith(secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .expiration(new Date(now.getTime() + JwtConfig.REFRESH_TOKEN_VALID_TIME))
                .subject("refresh-token")
                .claim(UUID, userUUID)
                .issuedAt(now)
                .signWith(secretKey)
                .compact();

        Tokens tokens = new Tokens(accessToken, refreshToken);

        return tokens;
    }

    public Claims getClaims(String token) {
        return (Claims) Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parse(token)
                .getPayload();
    }

    public boolean checkValidationToken(String token) {
        try {
            getClaims(token);
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Authentication getAuthentication(String token) {
        String uuid = (String) getClaims(token).get(UUID);
        UserDetails user = userDetailService.loadUserByUsername(uuid);
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }
}
