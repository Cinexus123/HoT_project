package com.api.services.impl;

import com.api.config.properties.IMAPEmailServerProperties;
import com.api.config.properties.SMTPEmailServerProperties;
import com.api.controllers.RocketResponseController;
import com.api.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.regex.Pattern;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender sender;
    private final RocketResponseController controller;
    private final ImapMailReceiver receiver;
    private final IMAPEmailServerProperties receiverProperties;
    private final SMTPEmailServerProperties senderProperties;

    private Thread receiverThread;
    private Thread serverThread = new Thread();
    private Boolean serverEnable;

    public EmailServiceImpl(JavaMailSender sender, ImapMailReceiver receiver, RocketResponseController controller, IMAPEmailServerProperties receiverProperties, SMTPEmailServerProperties properties) {
        this.sender = sender;
        this.controller = controller;
        this.receiverProperties = receiverProperties;
        this.senderProperties = properties;
        this.serverEnable = false;
        this.receiver = receiver;

        this.receiverThread = new Thread(() -> {
            while (serverEnable) {
                try {
                    receiver.waitForNewMessages();
                    if (serverEnable)
                        for (var msg : receiver.receive())
                            processReceivedMessage(msg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    serverEnable = false;
                    return;
                }
            }
            log.info("Receiving server set to offline.");
        });

        if(receiverProperties.isEnable())
            startReceiverServer();
    }

    @Override
    public void send(String to, String subject, String body) {
        var msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(body);
        msg.setFrom(senderProperties.getFrom());

        log.info("Sending email to {}", to);
        sender.send(msg);
        log.info("Email with subject: {} sent to {}", subject, to);
    }

    @Override
    public boolean isReceiverRunning() {
        log.info("State of thread: : {}", serverThread.getState());
        return serverThread.isAlive() | serverEnable;
    }

    @Override
    public void startReceiverServer() {
        if (!isReceiverRunning()) {
            serverThread = new Thread(receiverThread);
            serverEnable = true;
            serverThread.start();
            log.info("State of thread: : {}", serverThread.getState());
            log.info("Server started with id: {}", serverThread.getId());
        } else
            log.info("Server not started. It might be already running.");
    }

    @Override
    public void stopReceiverServer() {
        serverThread.interrupt();
        serverEnable = false;
        log.info("Interrupted receiver server thread. Server should shutdown in few minutes.");
        log.info("State of thread: : {}", serverThread.getState());
    }

    @Override
    public void processReceivedMessage(Message message) throws MessagingException {
        log.info("Received email message from: {} with subject: {}", message.getFrom()[0], message.getSubject());
        var from = "";
        try {
            from = getOnlyAddressEmail(message.getFrom()[0].toString());
        }catch(Exception ex){
            ex.printStackTrace();
            log.error("Something went wrong while parsing email. Ignoring received mail.");
            return;
        }

        log.info("Parsed email message from: {}", from);

        if (message.getSubject().matches("^[Ww]hereis \\w+$")) {
            String command = message.getSubject();
            String username = command.substring(command.indexOf(' ')).trim();
            log.info("Sending mail with {}'s location", username);
            this.send(from, "Location of " + username, controller.defaultResponseWhereIsUser(username).getBody());
        }
    }

    private String getOnlyAddressEmail(String fromAddress) {
        Pattern pattern = Pattern.compile("([\\w.-]+@[\\w.-]+)",Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(fromAddress);
        if(matcher.find())
            return matcher.group(1);
        else
            throw new IllegalArgumentException(fromAddress);
    }
}
