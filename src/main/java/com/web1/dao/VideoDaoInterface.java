package com.web1.dao;


import com.web1.pojo.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoDaoInterface extends JpaRepository<Video,String> {
}
