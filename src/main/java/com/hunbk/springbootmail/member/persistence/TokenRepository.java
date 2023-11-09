package com.hunbk.springbootmail.member.persistence;

import com.hunbk.springbootmail.member.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);
    boolean existsByToken(String token);

}
