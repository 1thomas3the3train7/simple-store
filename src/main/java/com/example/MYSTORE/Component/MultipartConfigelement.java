package com.example.MYSTORE.Component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.util.Properties;

@Configuration
public class MultipartConfigelement {
    @Value("${upload.path}")
    private String upload;
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        multipartConfigFactory.setMaxFileSize(DataSize.ofMegabytes(10));
        multipartConfigFactory.setMaxRequestSize(DataSize.ofMegabytes(10));
        return multipartConfigFactory.createMultipartConfig();
    }
    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("");
        mailSender.setPassword("uqlommzxcxyfgiokk");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
