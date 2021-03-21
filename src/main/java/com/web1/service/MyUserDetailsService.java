package com.web1.service;

import com.web1.dao.UserDaoInterface;
import com.web1.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserDaoInterface userDaoInterface;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
//            System.out.println(id);
            if(username == null) {
                throw new UsernameNotFoundException("用户名不存在");
            }
            User user = userDaoInterface.findById(username).get();
//            PasswordEncoder p = new BCryptPasswordEncoder();
//            System.out.println(p.encode(user.getPassword()));
            if(user == null){
                throw new UsernameNotFoundException("用户不存在");
            }
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (user.getAuth()==0){
                authorities.add(new SimpleGrantedAuthority("Teacher"));
            }else{
//                System.out.println(user.getAuth());
                authorities.add(new SimpleGrantedAuthority("Student"));
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
