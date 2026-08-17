package com.edgesense.notification;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.time.Duration;

@Service
public class NotificationService {

    private final SnsClient snsClient;
    private static final String TOPIC_ARN = "arn:aws:sns:eu-west-1:302388404494:edgesense-anomaly-alerts";

    public NotificationService(){
        this.snsClient = SnsClient.builder()
                .region(Region.EU_WEST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .overrideConfiguration(ClientOverrideConfiguration.builder()
                        .apiCallTimeout(Duration.ofSeconds(3))
                        .apiCallAttemptTimeout(Duration.ofSeconds(2))
                        .build())
                .build();
    }

    public void sendAnomalyAlert(double temp, double humidity, double timeStamp) {
        String message = String.format("ANOMALY DETECTED!\nTemperature: %.2f°C\nHumidity: %.2f%%\nTimestamp: %.0f",
                temp, humidity, timeStamp);

        PublishRequest request = PublishRequest.builder()
                .topicArn(TOPIC_ARN)
                .message(message)
                .subject("EdgeSense Anomaly Alert")
                .build();

        PublishResponse response = snsClient.publish(request);
        System.out.println("Alert sent with message ID: " + response.messageId());
    }
}