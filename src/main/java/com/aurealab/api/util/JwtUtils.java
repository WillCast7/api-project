package com.aurealab.api.util;


import com.aurealab.api.util.exceptions.TokenException;
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
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
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
    public String createToken (Authentication authentication){
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            String username = authentication.getPrincipal().toString();
            String authorities = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            String jwtToken = JWT.create()
                    .withIssuer(this.userGenerator)
                    .withSubject(username)
                    .withClaim("authorities", authorities)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis()+10800000))
                    .withJWTId(UUID.randomUUID().toString())
                    .withNotBefore(new Date(System.currentTimeMillis()))
                    .sign(algorithm);

            return jwtToken;

        } catch (JWTCreationException exception){
            throw new TokenException("Error al generar el token JWT.", exception) {
            };
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

            DecodedJWT decodedJWT = verifier.verify(token);

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
