package com.hunbk.springbootmail.member.persistence.jpa;

import com.hunbk.springbootmail.member.domain.Member;
import com.hunbk.springbootmail.member.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaMemberRepositoryImpl implements MemberRepository {

    private final SpringDataJpaMemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
