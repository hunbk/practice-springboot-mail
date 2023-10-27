package com.hunbk.springbootmail.global.mail;

import com.hunbk.springbootmail.global.exception.CustomException;
import com.hunbk.springbootmail.global.exception.ErrorCode;
import com.hunbk.springbootmail.member.domain.Member;
import com.hunbk.springbootmail.member.domain.VerificationToken;
import com.hunbk.springbootmail.member.persistence.MemberRepository;
import com.hunbk.springbootmail.member.persistence.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;

    public void sendVerificationEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_MEMBER));

        VerificationToken verificationToken = new VerificationToken(member);
        tokenRepository.save(verificationToken);

        SimpleMailMessage mailMessage = createEmail(verificationToken);
        mailSender.send(mailMessage);
    }

    public void verifyToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        if (verificationToken.isExpired()) {
            //TODO: 토큰 재발급 or 회원가입 취소
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        }

        Member member = verificationToken.getMember();
        member.activate();

        tokenRepository.delete(verificationToken);
    }

    private SimpleMailMessage createEmail(VerificationToken token) {
        String verificationLink = "http://localhost:8080/api/members/verify/" + token.getToken();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(token.getMember().getEmail());
        mailMessage.setSubject("[스프링부트 메일 앱] 회원가입을 환영합니다! 이메일을 인증해 주세요.");
        mailMessage.setText("다음 링크를 클릭하여 회원가입을 완료해주세요.: ");
        mailMessage.setText(verificationLink);
        return mailMessage;
    }
}
