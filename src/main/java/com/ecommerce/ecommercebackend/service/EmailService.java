package com.ecommerce.ecommercebackend.service;

import com.ecommerce.ecommercebackend.dataModel.VerificationToken;
import com.ecommerce.ecommercebackend.exception.EmailFailureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${email.from}")
    private String fromAddress;

    @Value("${app.frontend.url}")
    private String url;
    private JavaMailSender javaMailSender;

    public EmailService( JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    private SimpleMailMessage makeMailMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);
        return simpleMailMessage;
    }

    public void sendVerificationEMail(VerificationToken verificationToken) throws EmailFailureException {
        System.out.println(" we are here");
        SimpleMailMessage message = makeMailMessage();
        message.setTo(verificationToken.getUser().getEmail());
        message.setSubject("Verify your email address");
        message.setText(" Please follow the below link to verify. \nff" + url + "/auth/verify?" + verificationToken.getToken());

        try{
            javaMailSender.send(message);
        } catch (MailException ex){
            throw new EmailFailureException();
        }
    }

}
