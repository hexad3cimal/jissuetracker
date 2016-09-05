package com.jissuetracker.webapp.utils;


import com.jissuetracker.webapp.models.Email;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by jovin on 1/7/16.
 */

@Component
public class EmailHandler {

    public static void sendWelcomeMail(Email email) throws Exception {

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("no-reply@askavakil.in", "Ask A Vakil"));
        msg.addRecipient(Message.RecipientType.TO,
                new InternetAddress(email.getSendTo()));
        msg.setSubject("Welcome to Ask a vakil");
        msg.setContent(email.getContent(), "text/html; charset=utf-8");
        Transport.send(msg);
    }







}
