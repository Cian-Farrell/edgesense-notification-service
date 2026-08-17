package com.edgesense.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/anomaly")
    public ResponseEntity<String> sendAnomalyAlert(@RequestBody AnomalyAlert alert) {
        System.out.println("Received anomaly alert request: temp=" + alert.getTemp() + ", humidity=" + alert.getHumidity());
        try {
            notificationService.sendAnomalyAlert(alert.getTemp(), alert.getHumidity(), alert.getTimeStamp());
            System.out.println("Anomaly alert sent successfully");
            return ResponseEntity.ok("Anomaly alert sent successfully");
        } catch (Exception e) {
            System.out.println("Failed to send anomaly alert: " + e.getMessage());
            throw e;
        }
    }
}