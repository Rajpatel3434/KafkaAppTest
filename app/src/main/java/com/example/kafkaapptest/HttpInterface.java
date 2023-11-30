package com.example.kafkaapptest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface HttpInterface {

    // Subscribes to a topic.
    void subscribe(String topic) throws IOException;

    // Publishes a message to a topic.
    void publish(String topic, String message) throws IOException;

    // Gets the status of a topic.
    String getStatus(String topic) throws IOException, ExecutionException, InterruptedException;
}
