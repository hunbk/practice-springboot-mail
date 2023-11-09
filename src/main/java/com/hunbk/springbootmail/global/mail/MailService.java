package com.hunbk.springbootmail.global.mail;

import com.hunbk.springbootmail.global.exception.CustomException;
import com.hunbk.springbootmail.global.exception.ErrorCode;
import com.hunbk.springbootmail.member.domain.Member;
import com.hunbk.springbootmail.member.domain.VerificationToken;
import com.hunbk.springbootmail.member.persistence.MemberRepository;
import com.hunbk.springbootmail.member.persistence.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;

    public void sendVerificationEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_MEMBER));

        VerificationToken verificationToken = createUniqueVerificationToken(member);
        tokenRepository.save(verificationToken);

        MimeMessage mimeMessage = createMimeEmail(email, member.getNickname(), verificationToken);
        mailSender.send(mimeMessage);
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

    private MimeMessage createMimeEmail(String email, String nickname, VerificationToken verificationToken) {
        String verificationLink = "http://localhost:8080/api/members/verify/" + verificationToken.getToken();
        String mailContent = createEmailContent(nickname, verificationLink);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
            helper.setSubject("[스프링부트 메일 앱] 회원가입을 환영합니다! 이메일을 인증해 주세요.");
            helper.setText(mailContent, true);

            ClassPathResource resource = new ClassPathResource("static/logo.png");
            helper.addInline("logo", resource);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송에 실패했습니다.", e);
        }
        return mimeMessage;
    }

    private String createEmailContent(String nickname, String verificationLink) {
        Context context = new Context();
        context.setVariable("nickname", nickname);
        context.setVariable("verificationLink", verificationLink);
        return templateEngine.process("verification-email", context);
    }

    /**
     * 중복되지 않는 인증을 토큰 생성합니다.
     * 최대 5번까지 중복되는 토큰이 생성되면, 예외를 발생시킵니다.
     */
    private VerificationToken createUniqueVerificationToken(Member member) {
        int maxAttempts = 5;
        VerificationToken verificationToken = new VerificationToken(member);

        //토큰 UUID 중복 체크
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            if (tokenRepository.existsByToken(verificationToken.getToken())) {
                verificationToken = new VerificationToken(member);
            } else {
                return verificationToken;
            }
        }
        log.error("MailService 인증 토큰 생성 실패: email={}", member.getEmail());
        throw new RuntimeException("인증 토큰 생성 실패");
    }
}
