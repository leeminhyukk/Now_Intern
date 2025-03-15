package org.example.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.common.entity.Timestamped;
import org.example.domain.user.enums.UserRole;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;


    // 회원탈퇴 유무
    private Boolean isdeleted = false;

    public User(String userName, String nickname, String password, UserRole userRole) {
        this.username = userName;
        this.nickname = nickname;
        this.password = password;
        this.userRole = userRole;
    }

    // 회원 탈퇴 메서드
    public void deletedUser(String username, String password) {
        this.isdeleted = true;
    }

    public void updateUserRole(UserRole newUserRole) {
        this.userRole = newUserRole;
    }
}
