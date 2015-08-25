package mh.springboot.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * consider using external services like:
 * http://mailchimp.com/
 * http://www.experian.de/marketing-services/email-marketing-software.html
 *
 * Remember to create unsubscribe functionality
 */
public class EmailSender {

    @Autowired
    JavaMailSender javaMailSender;

    private final String from;

    public EmailSender(String from) {
        this.from = from;
    }

    public void sendSimpleEmail(String to, String subject, String text)
            throws MailException{
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setReplyTo(from);
        mailMessage.setFrom(from);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }

    public void sendHtmlEmail(String to, String subject, String htmlText)
            throws MessagingException, MailException{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setReplyTo(from);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlText, true);
        javaMailSender.send(mimeMessage);
    }

}
