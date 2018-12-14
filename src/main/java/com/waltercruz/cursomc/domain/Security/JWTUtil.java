package com.waltercruz.cursomc.domain.Security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String userName) {
        return Jwts.builder().
                setSubject(userName).
                setExpiration(new Date(System.currentTimeMillis() + expiration))/*Pegando o tempo de expiracao expiration do properties*/
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())/*Aqui estou criptografando com SignatureAlgorithm.HS512 + secret do properties*/
                .compact();
    }



    public boolean tokenValido(String token){
        Claims claims = getClaims(token);
        if (claims != null){
            String username = claims.getSubject();
            Date expiratinoDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if(username != null && now.before(expiratinoDate)){
                return true;
            }
        }
        return false;
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }



    /* Funcao que recupera os Claims a partir de um token (reivindicações)*/
    private Claims getClaims(String token){
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        }catch (Exception e){
            return null;
        }
    }

}
