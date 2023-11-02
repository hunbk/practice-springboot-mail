package com.hunbk.springbootmail.config;

import com.hunbk.springbootmail.member.persistence.MemberRepository;
import com.hunbk.springbootmail.member.persistence.jpa.JpaMemberRepositoryImpl;
import com.hunbk.springbootmail.member.persistence.jpa.SpringDataJpaMemberRepository;
import com.hunbk.springbootmail.member.persistence.mybatis.MemberMapper;
import com.hunbk.springbootmail.member.persistence.mybatis.MybatisMemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final SpringDataJpaMemberRepository springDataJpaMemberRepository;
    private final MemberMapper memberMapper;

    @Bean
    public MemberRepository memberRepository() {
//        return new JpaMemberRepositoryImpl(springDataJpaMemberRepository); //JPA 구현체
        return new MybatisMemberRepositoryImpl(memberMapper); //Mybatis 구현체
    }
}
