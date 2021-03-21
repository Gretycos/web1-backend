package com.web1.service;

import com.web1.dao.*;
import com.web1.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ServiceImplement implements ServiceInterface{
    @Autowired
    private UserDaoInterface userDaoInterface;
    @Autowired
    private CourseWareDaoInterface courseWareDaoInterface;
    @Autowired
    private VideoDaoInterface videoDaoInterface;
    @Autowired
    private MessageDaoInterface messageDaoInterface;
    @Autowired
    private RootReplyDaoInterface rootReplyDaoInterface;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //用户
    @Override
    public int saveUser(Map<String, String> person) {
        try{
            if(userDaoInterface.findById(person.get("id")).isEmpty()) {
                System.out.println(person);
                User user = new User(person.get("id"),person.get("name"),person.get("nickname"),
                        person.get("email"),Integer.parseInt(person.get("gender")),person.get("birthday"),
                        1,passwordEncoder.encode(person.get("password")));
                userDaoInterface.saveAndFlush(user);
                System.out.println("new user: "+user);
                return 1;
            }
            else return 2;
        }catch (Exception e)
        {
            System.out.println(e);
            return 0;
        }
    }


    @Override
    public int updateUserInfo(Map<String, String> person) {
        try{
            User user = userDaoInterface.findById(person.get("id")).get();
            user.setName(person.get("name"));
            user.setNickname(person.get("nickname"));
            user.setEmail(person.get("email"));
            user.setGender(Integer.parseInt(person.get("gender")));
            user.setBirthday(person.get("birthday"));
            userDaoInterface.saveAndFlush(user);
            System.out.println("update user: "+user);
            return 1;
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }

    @Override
    public int updateUserPassword(Map<String, String> person) {
        try{
            User user = userDaoInterface.findById(person.get("id")).get();
            if (user.getPassword().equals(passwordEncoder.encode(person.get("oldpassword"))))
            {
                user.setPassword(passwordEncoder.encode(person.get("newpassword")));
                userDaoInterface.saveAndFlush(user);
                System.out.println("update user: "+user);
                return 1;
            }
            else
                return 2;
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }

    @Override
    public Optional<User> findUserById(String id) {
        Optional<User> user = userDaoInterface.findById(id);
        if(user.isPresent()){
            user.get().setPassword(null);
            return user;
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByIdhasP(String id) {
        Optional<User> user = userDaoInterface.findById(id);
        if(user != null){
            return user;
        }
        return Optional.empty();
    }
    @Override
    public List<User> findAllUser() {
        return userDaoInterface.findAll();
    }

    @Override
    public List<Message> findMessagesByUserId(String id){
        Optional<User> user = userDaoInterface.findById(id);
        if(user != null){
            return user.get().getMessages();
        }else {
            return null;
        }
    }


    //课件
    @Override
    public List<CourseWare> findAllCourseWare(){
        return courseWareDaoInterface.findAll();
    }


    //视频
    @Override
    public List<Video> findAllVideo(){return videoDaoInterface.findAll();}


    //留言
    @Override
    public int saveMessage(Map<String, Object> msg) {
        try{
            Message message = new Message(
                    (User) msg.get("user"),
                    (String) msg.get("send_time"),
                    (String) msg.get("title"),
                    (String) msg.get("text"));
            messageDaoInterface.saveAndFlush(message);
            System.out.println("new message: "+message);
            return 1;
        }catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

//    @Override
//    public int updateMessageReplyCount(int id) {
//        try{
//            Message message = messageDaoInterface.findById(id).get();
//            message.setReply_count(message.getReply_count()+1);
//            messageDaoInterface.saveAndFlush(message);
//            System.out.println("update message reply_count: "+message);
//            return 1;
//        }catch (Exception e)
//        {
//            System.out.println(e);
//            return 0;
//        }
//    }

    @Override
    public int updateMessageClickCount(int id) {
        try{
            Message message = messageDaoInterface.findById(id).get();
            message.setClick_count(message.getClick_count()+1);
            messageDaoInterface.saveAndFlush(message);
            System.out.println("update message click_count: "+message);
            return 1;
        }catch (Exception e)
        {
            System.out.println(e);
            return 0;
        }
    }

    @Override
    public int updateMessageLastRtimeandRplCount(int id, String last_rtime) {
        try{
            Message message = messageDaoInterface.findById(id).get();
            message.setReply_count(message.getReply_count()+1);
            message.setLast_rtime(last_rtime);
            messageDaoInterface.saveAndFlush(message);
            System.out.println("update message last_uid&last_rtime&reply_count: "+message);
            return 1;
        }catch (Exception e)
        {
            System.out.println(e);
            return 0;
        }
    }

    @Override
    public int deleteMessage(int id) {
        try{
            Message message = messageDaoInterface.findById(id).get();
            message.setState(0);
            messageDaoInterface.saveAndFlush(message);
            System.out.println("delete message: "+message);
            return 1;
        }catch (Exception e)
        {
            System.out.println(e);
            return 0;
        }
    }

    @Override
    public Optional<Message> findMessageById(int id) {
        Optional<Message> message = messageDaoInterface.findById(id);
        if(message != null){
            return message;
        }
        return Optional.empty();
    }

    @Override
    public List<Message> findAllMessage() {
        return messageDaoInterface.findAll();
    }

    @Override
    public List<RootReply> findAllRootReplyByMid(int mid) {
//        System.out.println(rootReplyDaoInterface.findAllByMid(mid));
        return rootReplyDaoInterface.findAllByMid(mid);
    }


    //评论
    //增
    @Override
    public int saveRootReply(Map<String, Object> rr) {
        try{
            RootReply rootReply = new RootReply(
                    (User) rr.get("user"),
                    (Integer) rr.get("mid"),
                    (String) rr.get("text"),
                    (String) rr.get("time"));
            rootReplyDaoInterface.saveAndFlush(rootReply);
            System.out.println("new rootReply: "+rootReply);
            return 1;
        }catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    //改
    @Override
    public int updateRootReplyReplyCount(int id) {
        try{
            RootReply rootReply = rootReplyDaoInterface.findById(id).get();
            rootReply.setReply_count(rootReply.getReply_count()+1);
            rootReplyDaoInterface.saveAndFlush(rootReply);
            System.out.println("update rootReply reply_count: "+rootReply);
            return 1;
        }catch (Exception e)
        {
            System.out.println(e);
            return 0;
        }
    }

    //删
    @Override
    public int deleteRootReply(int id) {
        try{
            RootReply rootReply = rootReplyDaoInterface.findById(id).get();
            rootReply.setState(0);
            rootReplyDaoInterface.saveAndFlush(rootReply);
            System.out.println("delete rootReply: "+rootReply);
            return 1;
        }catch (Exception e)
        {
            System.out.println(e);
            return 0;
        }
    }


}
