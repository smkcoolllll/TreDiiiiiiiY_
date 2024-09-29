package com.Tredy.demo.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    public void sendVerificationOTPEmail(String email,String otp) throws MessagingException {
        MimeMessage mimeMessage= javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,"utf-8");
        String subject="Notification From TrendY_ : Verify Your OTP";
        String text="Hi TrendY_ here, \nPFB your verification OTP \nVerification OTP :  "+otp;
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text);
        mimeMessageHelper.setTo(email);
        try{
            javaMailSender.send(mimeMessage);
        }catch (MailException e){
            throw new MailSendException(e.getMessage());
        }
    }
}
