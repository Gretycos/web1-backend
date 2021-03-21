package com.web1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//        extends SavedRequestAwareAuthenticationSuccessHandler
    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private SessionRegistry sessionRegistry;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
//        for(Cookie cookie:httpServletRequest.getCookies()){
//            System.out.println("loginsession——客户端发送的: "+ cookie.getValue());
//        }
//        HttpSession session = httpServletRequest.getSession();
//        System.out.println("loginsession——服务器授予的: "+session.getId()+ " 间隔:"+session.getMaxInactiveInterval());
        SecurityContext context= SecurityContextHolder.getContext();
        System.out.println("logincontext: "+context);
//        List<Object> o = sessionRegistry.getAllPrincipals();
//        for (Object oj : o) {
//            System.out.println("session中存在的账户: " + oj);
//        }
        //允许跨域
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        // 允许自定义请求头token(允许head跨域)
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("status", "200");
        map.put("msg", "登录成功");
        map.put("data",authentication);
        // 登录成功后，返回json数据
        httpServletResponse.getWriter().append(objectMapper.writeValueAsString(map));
    }
}
