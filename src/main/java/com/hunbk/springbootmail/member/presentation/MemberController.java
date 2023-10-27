package com.hunbk.springbootmail.member.presentation;

import com.hunbk.springbootmail.global.mail.MailService;
import com.hunbk.springbootmail.member.application.MemberService;
import com.hunbk.springbootmail.member.domain.Member;
import com.hunbk.springbootmail.member.dto.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;

    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verifyToken(@PathVariable String token) {
        mailService.verifyToken(token);
        return ResponseEntity.ok().body("이메일 인증 완료");
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> create(@RequestBody MemberCreateRequest memberCreateRequest) {
        memberService.createMember(memberCreateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Member>> findAll() {
        return ResponseEntity.ok().body(memberService.findAll());
    }
}
