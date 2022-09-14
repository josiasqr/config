package com.bootcamp.transaction.notifications.service;

import com.bootcamp.transaction.notifications.domain.Notification;
import com.bootcamp.transaction.notifications.domain.NotificationService;
import com.bootcamp.transaction.notifications.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InNotificationService implements NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;

  public InNotificationService(NotificationRepository notificationRepository) {
    this.notificationRepository = notificationRepository;
  }

  @Override
  public List<Notification> listNotifications() {
    return notificationRepository.findAll();
  }

  @Override
  public Notification createNotification(Notification notification) {
    return notificationRepository.save(notification);
  }
}
