package com.hunbk.springbootmail.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),

    NO_SUCH_MEMBER(HttpStatus.BAD_REQUEST, "해당 회원이 존재하지 않습니다."),

    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 인증 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "만료된 토큰입니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
