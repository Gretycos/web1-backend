package com.web1.dao;

import com.web1.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserDaoInterface extends JpaRepository<User, String> {

}
