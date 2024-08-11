package project.login.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Secret key for signing the token
    private static final String SECRET_KEY = "586E3272357538782F413CZARNYF44SANTIAGO28472B4B6250655368566B597033733676397924";

    // Method to generate a JWT token for a user
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    // Private method to generate a JWT token with additional claims
    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername()) // Sets the username as the subject of the token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Sets the issuance date of the token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Sets the expiration date of the token (24 hours)
                .signWith(getKey(), SignatureAlgorithm.HS256) // Signs the token with HMAC algorithm and the secret key
                .compact();
    }

    // Private method to obtain the secret key
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Method to get the username from a JWT token
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    // Method to check if a JWT token is valid for a specific UserDetails
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Private method to get all claims from the token
    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Generic method to get a specific claim from the token
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Private method to get the expiration date of the token
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    // Private method to check if a JWT token has expired
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}

