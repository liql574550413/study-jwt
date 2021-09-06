package com.li.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.HashMap;

/**
 * @author liql
 * @date 2021/9/4
 */
@Controller
public class TestController {
    private final String SECRET="我是签名";
    @GetMapping("/login")
    @ResponseBody
    public String getToken(String uername,String password,HttpServletResponse response){

        //参数校验 数据库校验用户名和密码 略

        //生成token
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 30); //默认3天过期
        HashMap<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        //header通常由两部分组成：令牌的类型（即JWT）和所使用的签名算法，例如HMAC SHA256或RSA等等。
        String token = JWT.create().withHeader(map)
                .withClaim("username", uername)
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SECRET));

        //设置成cookie
        Cookie cookie = new Cookie("token",token);
        cookie.setMaxAge(30);
        response.addCookie(cookie);

        return "登录成功";
    }


    @GetMapping("/service")
    @ResponseBody
    public String service(){
        return "处理业务成功";
    }

}
