package com.elusivemel.orderservice.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SqsMessageSender {

    @Autowired
    private final AmazonSQS amazonSQS;

    private final String queueUrl;

    public SqsMessageSender(AmazonSQS amazonSQS,
            @Value("${cloud.aws.sqs.orders.queue.url}") String queueUrl) {
        this.amazonSQS = amazonSQS;
        this.queueUrl = queueUrl;
    }

    public void sendMessage(String messageBody) {
        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(messageBody);
        amazonSQS.sendMessage(request);
    }
}
