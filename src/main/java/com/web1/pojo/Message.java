package com.web1.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="message")
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
//    @Column(name="uid")
//    private String uid;
    @Column(name="title")
    private String title;
    @Column(name="text")
    private String text;
    @Column(name="send_time")
    private String send_time;
    @Column(name="reply_count")
    private int reply_count;
    @Column(name="click_count")
    private int click_count;
    @Column(name="state")
    private int state;
    @Column(name="last_rtime")
    private String last_rtime;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "uid",referencedColumnName = "id")
    private User user;

    public Message(){}

    public Message(User user,String send_time, String title, String text){
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.user=user;
        this.title=title;
        this.text=text;
        this.send_time=send_time;
        this.reply_count=0;
        this.click_count=0;
        this.state=1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public int getClick_count() {
        return click_count;
    }

    public void setClick_count(int click_count) {
        this.click_count = click_count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getLast_rtime() {
        return last_rtime;
    }

    public void setLast_rtime(String last_rtime) {
        this.last_rtime = last_rtime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString(){
        return "Message{"+
                "id: "+this.id+
                ", uid: "+this.user.getUsername()+
                ", title: "+this.title+
                ", text: "+this.text+
                ", send_time: "+this.send_time+
                ", reply_count: "+this.reply_count+
                ", click_count: "+this.click_count+
                ", last_rtime: "+this.last_rtime+
                "}";
    }
}
