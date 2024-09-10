package com.booknic.repository;

import com.booknic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(String id);
    User findUserByName(String name);
    boolean existsByEmail(String email);
    boolean existsById(String id);
}
