package com.web1.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
@Table(name = "rootreply")
public class RootReply {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="mid")
    private int mid;
    @Column(name="text")
    private String text;
    @Column(name="reply_count")
    private int reply_count;
    @Column(name="time")
    private String time;
    @Column(name="state")
    private int state;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "uid",referencedColumnName = "id")
    private User user;


    public RootReply(){}

    public RootReply(User user, int mid, String text, String time){
        this.user=user;
        this.mid=mid;
        this.text=text;
        this.time=time;
        this.reply_count=0;
        this.state=1;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString(){
        return "RootReply{"+
                "id: "+this.id+
                ", uid: "+this.user.getUsername()+
                ", mid: "+this.mid+
                ", text: "+this.text+
                ", time: "+this.time+
                ", reply_count: "+this.reply_count+
                ", state: "+this.state+
                "}\n";
    }
}
