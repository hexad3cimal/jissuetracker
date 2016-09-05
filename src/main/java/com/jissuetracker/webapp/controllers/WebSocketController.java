package com.jissuetracker.webapp.controllers;


import com.jissuetracker.webapp.models.User;
import com.jissuetracker.webapp.services.UserService;
import com.jissuetracker.webapp.utils.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Created by jovin on 27/6/16.
 */

@Controller
public class WebSocketController {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    UserService userService;


    @MessageMapping("/notifications")
    public void notify(@Payload Notification notification) throws Exception {

        User users = userService.getUserById(notification.getToId());
        template.convertAndSendToUser(users.getEmail(), "/queue/notifications", notification);
    }

}
