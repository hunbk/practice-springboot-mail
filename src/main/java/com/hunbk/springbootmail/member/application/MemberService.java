package com.hunbk.springbootmail.member.application;

import com.hunbk.springbootmail.global.exception.CustomException;
import com.hunbk.springbootmail.global.exception.ErrorCode;
import com.hunbk.springbootmail.global.mail.MailService;
import com.hunbk.springbootmail.member.domain.Member;
import com.hunbk.springbootmail.member.dto.MemberCreateRequest;
import com.hunbk.springbootmail.member.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MailService mailService;

    public void createMember(MemberCreateRequest memberCreateRequest) {
        memberRepository.findByEmail(memberCreateRequest.getEmail())
                .ifPresent(member -> {
                            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
                        }
                );

        Member member = Member.builder()
                .email(memberCreateRequest.getEmail())
                .password(memberCreateRequest.getPassword())
                .nickname(memberCreateRequest.getNickname())
                .activated(false)
                .build();

        memberRepository.save(member);
        mailService.sendVerificationEmail(member.getEmail());
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
