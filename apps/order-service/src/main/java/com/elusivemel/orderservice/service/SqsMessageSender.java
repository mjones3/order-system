package com.elusivemel.orderservice.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.elusivemel.orderservice.controller.OrderController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SqsMessageSender {

    @Autowired
    private final AmazonSQS amazonSQS;
    private static final Logger logger = LogManager.getLogger(OrderController.class);
    ObjectMapper objectMapper = new ObjectMapper();
    private final String queueUrl;

    private String json = "{}";

    public SqsMessageSender(AmazonSQS amazonSQS,
            @Value("${cloud.aws.sqs.orders.queue.url}") String queueUrl) {
        this.amazonSQS = amazonSQS;
        this.queueUrl = queueUrl;
    }

    public void sendMessage(String messageBody) {

        logger.info("Preparing to send to queue...");

        try {
            json = objectMapper.writeValueAsString(messageBody);
        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }

        sendMessage(json);
        logger.info("Sending message to queue.");

        SendMessageRequest request = new SendMessageRequest().withQueueUrl(queueUrl).withMessageBody(json);

        this.amazonSQS.sendMessage(request);
    }

}
