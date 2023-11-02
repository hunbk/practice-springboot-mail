package com.hunbk.springbootmail.member.persistence.mybatis;

import com.hunbk.springbootmail.member.domain.Member;
import com.hunbk.springbootmail.member.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MybatisMemberRepositoryImpl implements MemberRepository {

    private final MemberMapper memberMapper;

    @Override
    public Member save(Member member) {
        memberMapper.save(member);
        return member;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberMapper.findByEmail(email);
    }

    @Override
    public List<Member> findAll() {
        return memberMapper.findAll();
    }
}
