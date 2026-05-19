package extensao.backend.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${JWT_SECRET}")
    private String secretKey;
    
    private long tempoExpiracao = 86400000;

    private SecretKey getSignInKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    public String generateToken(String email){
        long currentTimeMillis = System.currentTimeMillis();
        Date dataEmissao = new Date(currentTimeMillis);
        Date dataExpiracao = new Date(currentTimeMillis + tempoExpiracao);
        String jwt = Jwts.builder()
                        .subject(email)
                        .issuedAt(dataEmissao)
                        .expiration(dataExpiracao)
                        .signWith(getSignInKey())
                        .compact();
        return jwt;
    };

    public String extractEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token, String email){
        final String userEmail = extractEmail(token);
        return (userEmail.equals(email) && !isTokenExpired(token));
    }


}