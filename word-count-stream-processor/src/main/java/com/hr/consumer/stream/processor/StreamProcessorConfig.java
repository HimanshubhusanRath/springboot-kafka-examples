package com.hr.consumer.stream.processor;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
public class StreamProcessorConfig {

    @Value("${bootstrap.servers}")
    private String bootstrapServers;

    @Value("${application.id}")
    private String applicationId;


    @Value("${input.topic}")
    private String inputTopicName;

    @Value("${output.topic}")
    private String outputTopicName;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs()
    {
        return new KafkaStreamsConfiguration(Map.of(
                StreamsConfig.APPLICATION_ID_CONFIG, applicationId,
                StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG,1,
                StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName(),
                StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName()
        ));
    }

    @Bean
    public NewTopic topic1()
    {
        return TopicBuilder.name(inputTopicName).partitions(3).replicas(1).build();
    }


    @Bean
    public NewTopic topic2()
    {
        return TopicBuilder.name(outputTopicName).partitions(3).replicas(1).build();
    }



}
