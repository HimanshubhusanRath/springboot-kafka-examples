package com.hr.wordcount.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfigs {


    @Value("${topic.name}")
    private String topicName;

    @Value("${bootstrap.servers}")
    private String bootstrapServers;
    @Value("${acks}")
    private String acksConfig;
    @Value("${linger.ms}")
    private String lingerMsConfig;
    @Value("${retries}")
    private String retriesConfig;
    @Value("${idempotence}")
    private String idempotenceConfig;

    @Bean
    public ProducerFactory<String, Object> producerFactory()
    {
        final Map<String,Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, idempotenceConfig);
        configs.put(ProducerConfig.LINGER_MS_CONFIG, lingerMsConfig);
        configs.put(ProducerConfig.RETRIES_CONFIG, retriesConfig);
        configs.put(ProducerConfig.ACKS_CONFIG, acksConfig);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory)
    {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic topic1()
    {
        return TopicBuilder.name(topicName).partitions(3).replicas(1).build();
    }
}
