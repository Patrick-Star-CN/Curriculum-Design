package team.star.healthcodesystem.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;

/**
 * jwt 生成工具
 * @author Patrick_Star
 * @version 1.0
 */
@Slf4j
public class JWT {
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String createJWT(String data) {
        return Jwts.builder().setSubject(data).signWith(KEY).compact();
    }

    public static String verifyJWT(String jws) {
        try {
            return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(jws).getBody().getSubject();
        } catch (JwtException e) {
            e.printStackTrace();
            return null;
        }
    }
}
