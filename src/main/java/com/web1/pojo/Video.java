package com.web1.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="video")
public class Video {
    @Id
    private String id;
    @Column(name="name")
    private String name;
    @Column(name="src")
    private String src;
    @Column(name="scale")
    private String scale;
    @Column(name="time")
    private String time;

    public Video(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString(){
        return "Video{"+
                "id = "+ this.id +
                ", name = "+ this.name +
                ", src = "+ this.src+
                ", scale = "+ this.scale +
                ", time = "+ this.time.substring(0, this.time.length()-3)+"}";
    }
}
