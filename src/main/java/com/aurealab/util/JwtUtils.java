package com.aurealab.util;


import com.aurealab.util.exceptions.TokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtils {
    //LionsFamily

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    /**
     * Create a JWT TOKEN
     *
     * @param authentication
     * @return a token
     */
    public String createToken(Authentication authentication) {
        try {
            // Algoritmo de firma
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            // Obtén el username desde el principal
            String username;
            if (authentication.getPrincipal() instanceof UserDetails) {
                username = ((UserDetails) authentication.getPrincipal()).getUsername();
            } else {
                username = authentication.getPrincipal().toString();
            }

            // Obtén las autoridades
            String authorities = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            System.out.println("authorities");
            System.out.println(authorities);
            // Crear el token
            String jwtToken = JWT.create()
                    .withIssuer(this.userGenerator)
                    .withSubject(username)
                    .withClaim("authorities", authorities) // Añade las autoridades
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10800000)) // 3 horas de expiración
                    .withJWTId(UUID.randomUUID().toString())
                    .withNotBefore(new Date(System.currentTimeMillis()))
                    .sign(algorithm);

            return jwtToken;

        } catch (JWTCreationException exception) {
            throw new TokenException("Error al generar el token JWT.", exception);
        }
    }


    /**
     * Token Validation
     * @param token
     * @return Token decoded
     */
    public DecodedJWT validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();
            System.out.println("token");
            System.out.println(token);

            DecodedJWT decodedJWT = verifier.verify(token);
            System.out.println("decodedJWT");
            System.out.println(decodedJWT);
            return decodedJWT;

        }catch (JWTVerificationException exception){
            log.error("Error decodificando: ", exception);
            throw new TokenException(constants.errors.tokenValidationError, exception);
        }
    }

    public String stractUserName(DecodedJWT decodedJWT){
        return decodedJWT.getSubject();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    public Map<String, Claim> getAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }
}
