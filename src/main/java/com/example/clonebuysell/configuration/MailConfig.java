package com.example.clonebuysell.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value(value = "${spring.mail.host}")
    private String host;

    @Value(value = "${spring.mail.username}")
    private String username;

    @Value(value = "${spring.mail.password}")
    private String password;

    @Value(value = "${spring.mail.port}")
    private int port;

    @Value(value = "${spring.mail.protocol}")
    private String protocol;

    @Value(value = "${mail.debug}")
    private String debug;

    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug);
        //properties.put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }
}
