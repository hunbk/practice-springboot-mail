package com.hunbk.springbootmail.member.persistence;

import com.hunbk.springbootmail.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findByEmail(String email);

    List<Member> findAll();

}
