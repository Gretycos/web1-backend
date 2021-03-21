package com.web1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private SessionRegistry sessionRegistry;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
            throws IOException, ServletException {

        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println("logoutcontext: " + context);
//        List<Object> o = sessionRegistry.getAllPrincipals();
//        for (Object oj : o) {
//            System.out.println("session中存在的账户: " + oj);
//        }
//        System.out.println("Authentication: " + authentication);
        //退出成功后删除当前用户session
//        for (Object principal : o) {
//            if (principal instanceof User) {
//                System.out.println("principal是User的实例");
//                final User loggedUser = (User) principal;
//                if (authentication.getName().equals(loggedUser.getUsername())) {
//                    List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
//                    if (null != sessionsInfo && sessionsInfo.size() > 0) {
//                        for (SessionInformation sessionInformation : sessionsInfo) {
//                            System.out.println("sessionInformation: "+sessionInformation+" 失效");
//                            sessionInformation.expireNow();
//                        }
//                    }
//                }
//            }
//        }
//        o = sessionRegistry.getAllPrincipals();
//        for (Object oj : o) {
//            System.out.println("清理后, session中存在的账户: " + oj);
//        }

        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        // 允许自定义请求头token(允许head跨域)
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("status", "200");
        map.put("msg", "登出成功");
        map.put("data", authentication);
        // 登录成功后，返回json数据
        httpServletResponse.getWriter().append(objectMapper.writeValueAsString(map));
    }
}
