package com.hunbk.springbootmail.member.persistence.jpa;

import com.hunbk.springbootmail.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

}
