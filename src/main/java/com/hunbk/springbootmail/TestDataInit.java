package com.hunbk.springbootmail;

import com.hunbk.springbootmail.member.domain.Member;
import com.hunbk.springbootmail.member.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        Member member = Member.builder()
                .email("hunbk1@email.com")
                .password("1234")
                .nickname("hunbk1")
                .activated(true)
                .build();

        memberRepository.save(member);
    }
}
