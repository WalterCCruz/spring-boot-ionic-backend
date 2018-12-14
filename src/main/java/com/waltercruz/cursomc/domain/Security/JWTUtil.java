package com.waltercruz.cursomc.domain.Security;


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

}
