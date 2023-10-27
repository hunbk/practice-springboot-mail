package com.hunbk.springbootmail.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreateRequest {

    private final String email;
    private final String password;
    private final String nickname;

}
