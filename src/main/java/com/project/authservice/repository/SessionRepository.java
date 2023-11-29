package com.project.authservice.repository;

import com.project.authservice.model.Session;
import com.project.authservice.model.SessionStatus;
import com.project.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session,Long> {
    Session save(Session session);
    Session findByTokenAndUser_IdAndStatus(String token,Long userId,SessionStatus status);
    Session findByTokenAndUser_Id(String token,Long userId);
}
