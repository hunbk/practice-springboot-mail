package com.hunbk.springbootmail.member.persistence.mybatis;

import com.hunbk.springbootmail.member.domain.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    @Insert("INSERT INTO member (email, password, nickname, activated) VALUES (#{email}, #{password}, #{nickname}, #{activated})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Member member);

    @Select("SELECT id, email, password, nickname, activated FROM member WHERE email = #{email}")
    Optional<Member> findByEmail(String email);

    @Select("SELECT id, email, password, nickname, activated FROM member")
    List<Member> findAll();
}
