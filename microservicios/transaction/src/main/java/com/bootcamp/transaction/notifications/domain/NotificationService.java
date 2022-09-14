package com.bootcamp.transaction.notifications.domain;

import java.util.List;

public interface NotificationService {
  List<Notification> listNotifications();

  Notification createNotification(Notification notification);
}
