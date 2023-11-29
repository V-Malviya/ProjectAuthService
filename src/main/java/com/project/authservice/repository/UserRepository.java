package com.project.authservice.repository;

import com.project.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByEmail(String email);
    User findUserById(Long id);
}
