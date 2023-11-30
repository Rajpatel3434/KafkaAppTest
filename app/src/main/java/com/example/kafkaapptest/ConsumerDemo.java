//package com.example.kafkaapptest;
//
//
//import com.rudderstack.android.sdk.core.RudderClient;
//import com.rudderstack.android.sdk.core.RudderLogger;
//
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.common.serialization.StringDeserializer;
//
//
//import java.time.Duration;
//
//import java.util.Collections;
//import java.util.Properties;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//import java.util.List;
//
//
//public class ConsumerDemo {
//
//    public static void main(String[] args) {
////            consumeMessages();
//    }
//
//    public  void consumeMessages() {
////        Logger logger = LoggerFactory.getLogger(ConsumerDemo.class);
////
////        String bootstrapServer = "192.168.21.129:9021";
////        String groupId = "gfg-consumer-group";
////        List<String> topics = List.of("testcricket","testfootball");
////
////        // Create Consumer Properties
////        Properties properties = new Properties();
////        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
////        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
////        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
////        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
////        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
////
////        // Create Kafka Consumer
////        KafkaConsumer consumer = new KafkaConsumer(properties);
////
////
////        // Subscribe Consumer to Our Topics
////        consumer.subscribe(topics);
////
////
////
////        // Poll the data
////        while (true) {
////
////            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
////
////            for (ConsumerRecord<String, String> record : records) {
////                logger.info("Key: " + record.key() +
////                        " Value: " + record.value() +
////                        " Partition: " + record.partition() +
////                        " Offset: " + record.offset()
////                );
////            }
////        }
////
////    }
//        RudderClient rudderClient = initializeRudderStack();
//
//        // Kafka Consumer Configuration
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "your_kafka_broker_url");
//        props.put("group.id", "your_consumer_group_id");
//        props.put("key.deserializer", StringDeserializer.class.getName());
//        props.put("value.deserializer", StringDeserializer.class.getName());
//
//        Consumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
//        kafkaConsumer.subscribe(Collections.singletonList("your_kafka_topic"));
//
//        while (true) {
//            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
//
//            for (ConsumerRecord<String, String> record : records) {
//                // Track events with RudderStack
//                trackEventWithRudder(rudderClient, record.value());
//            }
//        }
//    }
//
//
//
//    private static RudderClient initializeRudderStack() {
//        RudderClient.Builder builder = new RudderClient.Builder("2XHAo6nu3uh0frGLsvI0MxGhZDd");
//        builder.logLevel(RudderLogger.RudderLogLevel.VERBOSE);
//        return builder.build();
//    }
//
//    private static void trackEventWithRudder(RudderClient rudderClient, String event) {
//        // Track events with RudderStack
//        rudderClient.track("Kafka Event", null);
//    }
//
//
//
//}
//
//
//
