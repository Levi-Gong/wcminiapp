package org.ln.wechat.miniapp.system.security;

import io.jsonwebtoken.*;
import org.ln.wechat.miniapp.bean.user.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${system.security.jwt.ecret}")
    private String jwtSecret;

    @Value("${system.security.jwt.expirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return this.generateToken(Integer.toString(userPrincipal.getId()));

    }

    public String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        String token = Jwts.builder().setSubject(userId).setIssuedAt(new Date()).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        return token;
    }

    public Integer getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return Integer.parseInt(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {

            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("非法的token！");
        } catch (ExpiredJwtException ex) {
            logger.error("token超时，请重新获取！");
        } catch (UnsupportedJwtException ex) {
            logger.error("不支持的token类型！");
        } catch (IllegalArgumentException ex) {
            logger.error("token认证失败！");
        }
        return false;
    }
}
