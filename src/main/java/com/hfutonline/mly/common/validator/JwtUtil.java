package com.hfutonline.mly.common.validator;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author chenliangliang
 * @date 2018/2/27
 */
public class JwtUtil {

    private static Algorithm algorithm;

    public static final String TOKEN_HEADER = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9";

    static {
        try {
            algorithm = Algorithm.HMAC256("chenliangliang");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public static Token generateToken(Map<String, Object> claims) {

        JWTCreator.Builder jwtBuilder = JWT.create();
        claims.forEach((key, value) -> jwtBuilder.withClaim(key, value.toString()));
        String jwtToken = jwtBuilder.sign(algorithm);
        return new Token(jwtToken);
    }

    public static Map<String, Claim> getClaimsFromToken(String jwtToken) {
        Map<String, Claim> claims;
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
            claims = decodedJWT.getClaims();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public static Map<String, Claim> getClaimsFromToken(String payload, String signature) {
        String token = TOKEN_HEADER + "." + payload + "." + signature;
        return getClaimsFromToken(token);
    }


}
