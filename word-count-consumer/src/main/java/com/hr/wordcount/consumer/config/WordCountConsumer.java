package com.hr.wordcount.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class WordCountConsumer {

    @KafkaListener(topics = "${topic.name}", groupId = "${group.id}")
    public void consumer(final ConsumerRecord<String, Long> record)
    {
        System.out.println(record.key()+ " ----> "+record.value());
    }

}
