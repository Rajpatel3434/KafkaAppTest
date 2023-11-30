package com.example.kafkaapptest;


import java.io.IOException;
import java.util.Properties;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.*;
import java.nio.file.*;
import java.util.*;


//public class ProducerDemo {
//
////    public static void main(final String[] args) throws Exception {
////        String topic = "testcricket";
////        String key = "key1";
////        String value = "value1";
////
////        Properties props = new Properties();
////        props.put("bootstrap.servers", "pkc-921jm.us-east-2.aws.confluent.cloud:9092");
////        props.put("key-serializer", "org.apache.kafka.common.serialization.StringSerializer");
////        props.put("value,serializer", "org.apache.kafka.common.serialization.StringSerializer");
////
////        Producer<String, String> producer = new KafkaProducer(props);
////
////        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
////        producer.send(record);
////        producer.close();
////        System.out.println("Producer is completed!");
////    }
//
//
//
//}


//public class ProducerDemo extends AsyncTask<String, Void, String> {
//    private TextView textView;
//    @Override
//    protected String doInBackground(String... messages) {
//        Properties props = new Properties();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, MainActivity.BOOTSTRAP_SERVERS);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//
//        Producer<String, String> producer = new KafkaProducer<>(props);
//
//        for (String message : messages) {
//            ProducerRecord<String, String> record = new ProducerRecord<>(MainActivity.TOPIC, message);
//            producer.send(record, (metadata, exception) -> {
//                if (exception == null) {
//                    Log.d("KafkaProducer", "Message sent to partition " + metadata.partition());
//                } else {
//                    Log.e("KafkaProducer", "Error sending message: " + exception.getMessage());
//                }
//            });
//        }
//
//        producer.close();
//        return "Message sent to Kafka!";
//    }
//
//    @Override
//    protected void onPostExecute(String result) {
//        textView.setText(result);
//    }
//}
import org.apache.kafka.clients.producer.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProducerDemo  {



    public ProducerDemo() throws IOException {
    }

    public static Properties loadConfig(final String configFile) throws IOException {
        if (!Files.exists(Paths.get(configFile))) {
            throw new IOException(configFile + " not found.");
        }
        final Properties cfg = new Properties();
        try (InputStream inputStream = new FileInputStream(configFile)) {
            cfg.load(inputStream);
        }
        return cfg;
    }



    public void messageProduce() throws IOException{
        final Properties props = loadConfig("client.properties");
        Producer<String, String> producer = new KafkaProducer<>(props);
        producer.send(new ProducerRecord<>("testcricket", "3KVYU3PUZBC6JVB2", "Hello there! 😉"));
        producer.close();

    }

}