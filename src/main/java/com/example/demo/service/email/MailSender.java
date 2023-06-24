package com.example.demo.service.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailSender {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    @Value("${spring.mail.username}")
    private String username;

    @SneakyThrows
    public void sendMessageHtml(String to, String subject, String template, Map<String, Object> attributes) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(attributes);
        String htmlBody = thymeleafTemplateEngine.process(template, thymeleafContext);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(username);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        mailSender.send(message);
    }
}

// 1.Checked Exceptions: the compiler requires you to handle them explicitly. IOException, SQLException, ParseException.
// 2.Unchecked Exceptions: also known as runtime exceptions. do not need to be explicitly handled or declared. NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException.
// @SneakyThrows is used to automatically add exception handling to a method, allowing you to throw checked exceptions without explicitly declaring them in the method signature. it automatically catches checked exceptions that are thrown within the method and wraps them in a RuntimeException.
// following checked exceptions might occur in sendMessageHtml: MessagingException by mailSender.createMimeMessage() or mailSender.send(message), UnsupportedEncodingException by MimeMessageHelper

// MimeMessage represents an email message in MIME (Multipurpose Internet Mail Extensions) format and provides methods to set and retrieve various properties of an email message.
// multipart: If set to true, it allows for the inclusion of attachments or embedded content in the email. If set to false, the email will be treated as a simple text/plain message.
// multipart: helper.setText("<html><body><img src='cid:logoImage' alt='Logo' /></body></html>", true); ClassPathResource imageResource = new ClassPathResource("path/to/image/logo.png"); helper.addInline("logoImage", imageResource); ClassPathResource attachment1 = new ClassPathResource("path/to/attachment/file1.pdf"); helper.addAttachment("file1.pdf", attachment1);