package com.microservice.backend_scrapify.utils.jwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    public static final String KEY = "XvXiCcC0TQHXCLWbFhdf/PGXY/BEWKnFsbx0hVeJBcp7LJH8pbKRdIn0aERK0t/e8Oj5+33Nx2b6Uzd+71ge3w==";
    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));

    public String generateToken(CustomUserDetails customUserDetails, Date expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", customUserDetails.getUsername());  // Custom Claims (Add as per need)
        claims.put("expiration", expiration);

        return createToken(claims, customUserDetails.getUsername(), expiration);
    }


    public static String createToken(Map<String, Object> claims, String subject, Date expiration) {
        return Jwts.builder()
                .setSubject(subject) // Add Subject (Username)
                .setClaims(claims)  // Add Custom Claims
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(SECRET_KEY)
                .compact();
    }

    // Validation Methods
    public Boolean validateToken(String token, CustomUserDetails customUserDetails) {
        final String username = extractUsername(token);
        return (username != null && username.equals(customUserDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    // Extraction Methods
    public String extractUsername(String token) {
        String localToken = token.replaceAll("Bearer ", "");
        return extractClaim(localToken, claims -> claims.get("userId", String.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        if (claims != null)
            return claimsResolver.apply(claims);
        else return null;
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date extractExpiration(String token) {
        Date date = extractClaim(token, Claims::getExpiration);
        if (date == null)
            return new Date(new Date().getTime() - 1000);
        else return date;
    }

}
