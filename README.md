# 스프링부트 메일 앱
스프링부트 이메일 인증

## Gmail 설정
- 구글 - 보안 - 2단계 인증 - 앱 비밀번호 생성
- 생성한 앱 비밀번호를 스프링 메일 서비스에서 사용함(`application.properties` 설정)
- 구글 가이드(https://support.google.com/accounts/answer/185833)

## application.properties 설정
```properties
# h2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# db
spring.datasource.url=jdbc:h2:mem:test
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver

# mail
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=test@gmail.com
spring.mail.password=abcdefghijklmnop
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
- `spring.mail.host`: SMTP 메일 서버의 호스트 이름 또는 IP 주소입니다.
- `spring.mail.port`: SMTP 메일 서버의 포트 번호입니다. 일반적으로 SMTP는 587 포트를 사용하며, SSL을 사용하는 경우 465 포트를 사용합니다.
- `spring.mail.username`: 메일 서버에 로그인하는 데 사용되는 사용자 이름입니다.
- `spring.mail.password`: 메일 서버에 로그인하는 데 사용되는 비밀번호입니다.
- `spring.mail.properties.mail.smtp.auth`: SMTP 인증을 사용하도록 설정합니다. 일반적으로 `true`로 설정합니다.
- `spring.mail.properties.mail.smtp.starttls.enable`: STARTTLS 보안을 사용하도록 설정합니다. 일반적으로 `true`로 설정합니다.

## 참고
- ChatGPT
- baeldung(https://www.baeldung.com/spring-email-templates)