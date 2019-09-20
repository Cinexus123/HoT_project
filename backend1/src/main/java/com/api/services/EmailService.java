package com.api.services;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

    void send(String to,String subject,String body);
    boolean isReceiverRunning();
    void startReceiverServer();
    void stopReceiverServer();
    void processReceivedMessage(Message message) throws MessagingException, IOException;
}
