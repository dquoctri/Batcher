package com.dqtri.batcher.client;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.StringUtils.delimitedListToStringArray;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSender {
    private final JavaMailSender mailSender;

    public void send(EmailDto mail) {
        log.info("Sending email process...");
        validateArguments(mail);
        try {
            MimeMessage mineMessage = createMineMessage(mail);
            mailSender.send(mineMessage);
            log.info("Email is sent successfully.");
        } catch (MessagingException | MailException e) {
            log.error("Sending email process is failed.", e);
        }
    }

    private MimeMessage createMineMessage(@NonNull EmailDto email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setFrom(email.getFrom());
        messageHelper.setTo(delimitedListToStringArray(email.getTo(),";"));
        messageHelper.setCc(delimitedListToStringArray(email.getCc(),";"));
        messageHelper.setSubject(email.getSubject());
        messageHelper.setText(email.getBody(), email.isHtml());
        return message;
    }

    private void validateArguments(EmailDto mail) {
        notNull(mail, "Email is required");
        notNull(mail.getFrom(), "From address is required");
        notNull(mail.getTo(), "To address is required");
        notNull(mail.getSubject(), "Subject is required");
        notNull(mail.getBody(), "Email body is required");
    }
}
