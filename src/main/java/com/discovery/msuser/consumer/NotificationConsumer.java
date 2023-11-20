package com.discovery.msuser.consumer;

import com.discovery.msuser.dto.NotificationDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @RabbitListener(queues = {"gamification1.queue", "gamification2.queue"})
    public void consume1Notification(NotificationDto message) {
        System.out.println("Notification received: " + message.getMessage());
    }

    @RabbitListener(queues = "reports.queue")
    public void consume2Notification(NotificationDto message) {
        System.out.println("Notification received: " + message.getMessage());
    }
}
