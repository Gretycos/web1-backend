package com.web1.service;

import com.web1.pojo.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ServiceInterface {

    int saveUser(Map<String, String> person);
    int updateUserInfo(Map<String, String> person);
    int updateUserPassword(Map<String, String> person);
    Optional<User> findUserById(String id);
    Optional<User> findUserByIdhasP(String id);
    List<User> findAllUser();
    List<Message> findMessagesByUserId(String id);

    List<CourseWare> findAllCourseWare();

    List<Video> findAllVideo();

    int saveMessage(Map<String,Object> msg);
//    int updateMessageReplyCount(int id);
    int updateMessageClickCount(int id);
    int updateMessageLastRtimeandRplCount(int id, String last_rtime);
    int deleteMessage(int id);
    Optional<Message> findMessageById(int id);
    List<Message> findAllMessage();
    List<RootReply> findAllRootReplyByMid(int mid);

    int saveRootReply(Map<String,Object> rr);
    int updateRootReplyReplyCount(int id);
    int deleteRootReply(int id);


}
