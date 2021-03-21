package com.web1.pojo;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="user")
public class User {
    @Id
    @Column(name="id")
    private String username;
    @Column(name = "name")
    private String name;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "email")
    private String email;
    @Column(name = "gender")
    private int gender;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "auth")
    private int auth;
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    @JSONField(serialize = false)
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JSONField(serialize = false)
    private List<RootReply> rootReplies = new ArrayList<>();


    public User() { }

    public User(String id, String name, String nickname, String email, int gender,
                String birthday, int auth, String password) {
        this.username = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.auth = auth;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<RootReply> getRootReplies() {
        return rootReplies;
    }

    public void setRootReplies(List<RootReply> rootReplies) {
        this.rootReplies = rootReplies;
    }

//        @Override
//    public String toString(){
//        return "User{"+
//                "id = "+ this.id +
//                ", name = "+ this.name +
//                ", nickname = "+ this.nickname +
//                ", email = "+ this.email +
//                ", gender = "+ this.gender +
//                ", birthday = "+ this.birthday +
//                ", auth = "+ this.auth +
//                ", password = "+ this.password+"}";
//    }

    @Override
    public String toString() {
        return this.username;
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();

    }
}