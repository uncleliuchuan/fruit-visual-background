package com.jclz.fruit.shiro;


import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ShiroAuthFilter extends FormAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(ShiroAuthFilter.class);


    public ShiroAuthFilter() {
        super();
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //Always return true if the request's method is OPTIONS
        if (request instanceof HttpServletRequest) {
            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        logger.info("SHIROFILTER authc拦截");
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "true");
        res.setContentType("application/json; charset=utf-8");
        res.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = res.getWriter();
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1004);
        map.put("msg", "未登录");
        writer.write(JSON.toJSONString(map));
        writer.close();
        //return false 拦截， true 放行
        return false;
    }
}
