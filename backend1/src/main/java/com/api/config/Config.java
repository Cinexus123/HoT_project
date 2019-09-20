package com.api.config;

import com.api.config.properties.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@Configuration
@EnableConfigurationProperties({
        FileStorageProperties.class,
        NetworkDataProviderProperties.class,
        GoogleCalendarProperties.class,
        ServerConfigProperties.class,
        SMTPEmailServerProperties.class,
        IMAPEmailServerProperties.class
})
@AllArgsConstructor
@EnableScheduling
public class Config {

    @Bean
    public JavaMailSender mailSender(SMTPEmailServerProperties properties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(properties.getHost());
        mailSender.setPort(Integer.parseInt(properties.getPort()));

        mailSender.setUsername(properties.getUsername());
        mailSender.setPassword(properties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", properties.getTransportProtocol());
        props.put("mail.smtp.auth", properties.getSmtpAuth());
        //props.put("mail.smtp.starttls.enable", properties.getSmtpStarttlsEnable());
        props.put("mail.smtp.socketFactory-port", properties.getSocketFactoryPort());
        props.put("mail.smtp.socketFactory-class", properties.getSocketFactoryClass());
        props.put("mail.debug", properties.getDebug());

        return mailSender;
    }

    @Bean
    public ImapMailReceiver mailReceiver(IMAPEmailServerProperties properties) {
        String url = properties.getFullImapAddress();
        var receiver = new ImapMailReceiver(url);
        receiver.setShouldMarkMessagesAsRead(true);
        return receiver;
    }

}
