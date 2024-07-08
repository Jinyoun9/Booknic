package com.booknic.repository;

import com.booknic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserById(String id);
    List<User> findUsersByBookname(String bookname);
}
