package com.example.Spring.BootServer.Configure;


import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

@Component
public class JwtUtil {

    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String SECRET = "mySecretKey";

    public String generateToken(Long userId) {
        Date now = new Date();
        return JWT.create()
                .withClaim("userId", userId)
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build().verify(token);
        return decodedJWT.getClaim("userId").asLong();
    }
}
