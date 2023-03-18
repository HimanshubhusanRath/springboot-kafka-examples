package com.hr.wordcount.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class WordCountConsumerMain
{
    public static void main(String[] args) {
        SpringApplication.run(WordCountConsumerMain.class, args);
    }
}