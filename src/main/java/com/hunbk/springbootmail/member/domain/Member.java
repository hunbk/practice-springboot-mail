package com.hunbk.springbootmail.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private Boolean activated;

    @Builder
    public Member(String email, String password, String nickname, Boolean activated) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.activated = activated;
    }

    public void activate() {
        this.activated = true;
    }
}
