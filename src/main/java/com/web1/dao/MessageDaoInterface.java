package com.web1.dao;

import com.web1.pojo.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDaoInterface extends JpaRepository<Message,Integer> {
}
