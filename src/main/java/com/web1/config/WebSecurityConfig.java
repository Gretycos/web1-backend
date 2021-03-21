package com.web1.config;

import com.web1.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;

@Configuration
//@CrossOrigin
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private MyUserDetailsService myUserDetailsService;

    //登录成功处理
    @Autowired
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    //登录失败处理
    @Autowired
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    //登出成功处理
    @Autowired
    MyLogoutSuccessHandler myLogoutSuccessHandler;

    //权限不足处理
    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;

    //匿名用户
    @Autowired
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    //被挤下线通知
    @Autowired
    MySessionInformationExpiredStrategy mySessionInformationExpiredStrategy;

//    @Autowired
//    SessionRegistry sessionRegistry;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                //跨域
                    .cors()
                .and()
                .csrf().disable()
                //使用权限
                .authorizeRequests()
                //权限规则
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // 这句比较重要，放过 option 请求
                    .antMatchers("/","/register","/video/**").permitAll()
                    .antMatchers("/user/**","/getCourseware/**","/getVideo/**","/message/**","/rootReply").hasAnyAuthority("Student","Teacher")
                    .anyRequest().authenticated() //需要登录才能访问
                .and()
                //登录配置
                    //表单登录
                    .formLogin()
                    //指定表单请求路径
                    .loginProcessingUrl("/login")
                    //登录成功
                    .successHandler(myAuthenticationSuccessHandler)
                    //登录失败
                    .failureHandler(myAuthenticationFailureHandler).permitAll()
                .and()
                //登出配置
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                    .invalidateHttpSession(true) //默认true
                    .clearAuthentication(true) //默认true
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler(myLogoutSuccessHandler)
                .and()
                //错误处理
                    .exceptionHandling()
                    .accessDeniedHandler(myAccessDeniedHandler)
                    .authenticationEntryPoint(myAuthenticationEntryPoint)
//                .and()
//                //记住登录状态
//                    .rememberMe()
//                    .tokenValiditySeconds(30)
                .and()
                    .httpBasic()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .maximumSessions(1)
//                    //当超过指定的最大session并发数量时，阻止后面的登陆（感觉貌似很少会用到这种策略）
//                    .maxSessionsPreventsLogin(true)
                    //通知被挤下线
                    .expiredSessionStrategy(mySessionInformationExpiredStrategy)
                    .sessionRegistry(getSessionRegistry());
//        http.addFilterBefore()
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedHeader("Content-Type, x-requested-with, X-Custom-Header, Authorization");
        configuration.setAllowedHeaders(Arrays.asList("Content-Type","x-requested-with","X-Custom-Header","Authorization"));
        configuration.setAllowCredentials(true);//cookie
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:8081"));
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "OPTIONS", "DELETE"));
        configuration.setMaxAge((long) 3600);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SessionRegistry getSessionRegistry(){
        SessionRegistry sessionRegistry=new SessionRegistryImpl();
        System.out.println("获取了新的sessionRegistry");
        return sessionRegistry;
    }

}
