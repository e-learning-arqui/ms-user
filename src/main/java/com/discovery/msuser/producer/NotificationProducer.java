package com.discovery.msuser.producer;

import com.discovery.msuser.dto.NotificationDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class NotificationProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    Logger log = Logger.getLogger(NotificationProducer.class.getName());

    public NotificationProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public String sendNotification(NotificationDto notificationDto, String routingKey) {
        log.info("Sending notification to queue");
        amqpTemplate.convertAndSend("ucbExchange", routingKey, notificationDto);
        return "Notification sent to queue";
    }

}
