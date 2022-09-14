package com.bootcamp.transaction.notifications.controller;

import com.bootcamp.transaction.notifications.domain.Notification;
import com.bootcamp.transaction.notifications.domain.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
  @Autowired
  private NotificationService notificationService;

  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @GetMapping
  public ResponseEntity<List<Notification>> listsNotifications() {
    return ResponseEntity.ok(notificationService.listNotifications());
  }

  @PostMapping
  public ResponseEntity<Notification> create(@Valid @RequestBody Notification notification, BindingResult errors) {
    if (errors.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.getFieldError().getDefaultMessage());
    }

    return ResponseEntity.status(HttpStatus.CREATED)
      .body(notificationService.createNotification(notification));
  }
}
