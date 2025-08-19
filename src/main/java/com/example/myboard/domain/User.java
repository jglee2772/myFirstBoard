package com.example.myboard.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="users", uniqueConstraints=@UniqueConstraint(columnNames="email"))
@Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=100)
    private String email;

    @Column(nullable=false, length=60)
    private String password; // BCrypt 해시

    @Column(nullable=false, length=50)
    private String name;

    @Column(nullable=false, length=20)
    private String role; // "USER" or "ADMIN"

    @Column(nullable=false)
    private boolean approved = false; // 승인 상태 (기본값: false)

    private User(String email, String name) {
        this.email = email;
        this.name = name;
        this.password = ""; // OAuth 가입자는 비번 공란 가능
        this.role = "USER";
        this.approved = false;
    }

    public static User create(String email, String name) {
        return new User(email, name);
    }
    
    public boolean isAdmin() {
        return "ADMIN".equals(this.role);
    }
    
    public boolean isApproved() {
        return this.approved;
    }
    
    public void approve() {
        this.approved = true;
    }
}
