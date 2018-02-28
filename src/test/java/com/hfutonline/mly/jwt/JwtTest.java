package com.hfutonline.mly.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hfutonline.mly.common.validator.JwtUtil;
import com.hfutonline.mly.common.validator.Token;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenliangliang
 * @date 2018/2/23
 */
public class JwtTest {


    @Test
    public void testHS256() throws UnsupportedEncodingException {

        Algorithm hmac=Algorithm.HMAC256("chenliangliang");

        String token= JWT.create()
                //.withExpiresAt(new Date(System.currentTimeMillis()+600000))
                .withClaim("uid","25155")
                .withClaim("name","cll")
                .sign(hmac);

        System.out.println(token);
//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.  //头部：{"typ":"JWT","alg":"HS256"}
// eyJ1aWQiOiIyNTE1NSIsIm5hbWUiOiJjbGwiLCJleHAiOjE1MTkzNjA5Mzd9. //payload
// XvHuJGJBjn9NonvR7UnbJYThqCmoMHZUKSool-XvKHE //对header和payload的签名  //signature
    }

    /**
     * 验证token
     */
   @Test
    public void testVerifyToken(){
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIyNTE1NSIsIm5hbWUiOiJjbGwifQ.vhoyG33WYG5d9n-kDU_lOq95ncb_rC05JIJ4htwtkf8";

       try {
           Algorithm algorithm=Algorithm.HMAC256("chenliangliang");
           JWTVerifier verifier=JWT.require(algorithm)
                   .build();
           DecodedJWT decodedJWT = verifier.verify(token);
           Map<String, Claim> claims = decodedJWT.getClaims();
           claims.forEach((s,claim)-> System.out.println("key:"+s+",value:"+claim.asString()));
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }catch (TokenExpiredException tee){
           tee.printStackTrace();
       }
   }


   @Test
    public void test3(){
//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
       HashMap<String, Object> map = new HashMap<>();
       map.put("id","jnvjf");
       map.put("name","dcdc");
       Token token = JwtUtil.generateToken(map);

       System.out.println(token);


       Map<String, Claim> claims = JwtUtil.getClaimsFromToken(token.toJwt());
       claims.forEach((s,claim)-> System.out.println("key:"+s+",value:"+claim.asString()));

   }
}
