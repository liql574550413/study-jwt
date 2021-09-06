package com.li.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.javafx.binding.SelectBinding;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * @author liql
 * @date 2021/9/3
 */
@RestController
public class LoginController {

    private final String SECRET="我是签名";

    @GetMapping("/get")
    public String getToken( HttpServletResponse response,HttpServletRequest request){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 30); //默认3天过期
        System.out.println(instance.getTime().getTime());
        System.out.println(new Date().getTime());
        HashMap<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        //header通常由两部分组成：令牌的类型（即JWT）和所使用的签名算法，例如HMAC SHA256或RSA等等。
        String token = JWT.create().withHeader(map)
                .withClaim("username", "liql")
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SECRET));
       response.setHeader("token", token);

        return token;
    }
    @GetMapping("/get2")
    public void verifier(HttpServletRequest request){
        //解密token成一个DecodedJWT对象  ，具体的细心可从该对象中获取
        String token = request.getHeader("token");
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);

        System.out.println(verify.getClaim("username").asString());
        System.out.println(verify.getPayload().toString());
    }
}
