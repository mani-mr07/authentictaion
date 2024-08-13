package com.loginauthenication.authentication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.random.RandomGenerator;

@Service
public class emailService {

    @Autowired
    private JavaMailSender mailSender;


    private int otpValue;


    public int optGenerator(){
        Random random = new Random();
        otpValue = random.nextInt(1000000);
        return otpValue;

    }


    public void sendEmail(String toEmail,int otp){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("balakannantvl@gmail.com");
        message.setTo(toEmail);

        message.setText("Hi,\n\n"+String.valueOf(otp)+" "+"is your Quest Financial Verification OTP. Please do not share it with anyone.\n\n Team Quest Financial ");
        message.setSubject("OTP Generation for QUEST FINANCIAL");
        mailSender.send(message);
        System.out.println("Mail Send Successfully");
    }
}
