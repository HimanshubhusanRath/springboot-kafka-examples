package com.hr.wordcount.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class WordProducerMain {

    @Value("${topic.name}")
    private String topicName;

    public static void main(String[] args) {
        SpringApplication.run(WordProducerMain.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(final KafkaTemplate<String, Object> kafkaTemplate) {
        return args -> {
            // Send some texts to the Kafka topic
            kafkaTemplate.send(topicName, "Kafka message");
            kafkaTemplate.send(topicName, "New kafka message");
        };
    }

}