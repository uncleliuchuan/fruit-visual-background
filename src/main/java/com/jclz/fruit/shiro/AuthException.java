package com.jclz.fruit.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AuthException {
    private static final Logger logger = LoggerFactory.getLogger(AuthException.class);

    @ExceptionHandler(value = UnauthorizedException.class)//处理访问方法时权限不足问题
    public void AuthcErrorHandler(HttpServletResponse res, Exception e) throws IOException {
        logger.info("抛出UnauthorizedException权限异常");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setContentType("application/json; charset=utf-8");
        res.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = res.getWriter();
        Map<String, Object> map = new HashMap<>();
        map.put("status", 3);
        map.put("msg", "权限不足");
        writer.write(JSON.toJSONString(map));
        writer.close();
    }
}
