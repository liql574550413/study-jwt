package com.li;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@SpringBootTest
class StudyJwtApplicationTests {
    private final String SECRET="我是签名,实际开发时设置复杂点";
    @Test
    void contextLoads() {
    }

    @Test
    public void getToken(){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 30); //设置过期时间
//        System.out.println(instance.getTime().getTime());
//        System.out.println(new Date().getTime());
        HashMap<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        //header通常由两部分组成：令牌的类型（即JWT）和所使用的签名算法，例如HMAC SHA256或RSA等等。
        String token = JWT.create()
                .withHeader(map)//设置标题,如果为null，则会使用默认值
                .withClaim("username", "我自横刀向天笑")//有效负载 可设置多个
                .withClaim("usertype", "保洁员")
                .withExpiresAt(instance.getTime())//设置过期时间
                .sign(Algorithm.HMAC256(SECRET));//签名加密
        System.out.println(token);


    }

    @Test
    public void verifier(){
        //解密token成一个DecodedJWT对象  ，具体的信息可从该对象中继续获取
        String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzA3MjA3MzMsInVzZXJuYW1lIjoi5oiR6Ieq5qiq5YiA5ZCR5aSp56yRIn0.Ch33IR53bmGkaFUJN24oiRykp3xJva4oDwZcEuh1lCo";

        /**
         * 验证token  如果token过期或错误 都会抛出相应的异常，可通过捕获异常来设置返回信息
         */
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        //继续获取信息 还有很多api
        System.out.println(verify.getClaim("username").asString());
        System.out.println(verify.getClaim("age").asInt());
        System.out.println();
    }
}
