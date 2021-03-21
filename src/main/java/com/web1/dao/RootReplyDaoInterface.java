package com.web1.dao;

import com.web1.pojo.RootReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RootReplyDaoInterface extends JpaRepository<RootReply,Integer> {
    List<RootReply> findAllByMid(int mid);
}
