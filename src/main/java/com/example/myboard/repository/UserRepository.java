package com.example.myboard.repository;

import com.example.myboard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    List<User> findByApprovedFalse(); // 승인 대기 중인 사용자들
}
