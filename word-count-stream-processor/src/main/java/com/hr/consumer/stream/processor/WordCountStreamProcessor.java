package com.hr.consumer.stream.processor;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class WordCountStreamProcessor
{
    @Value("${input.topic}")
    private String inputTopicName;

    @Value("${output.topic}")
    private String outputTopicName;

    private static final Serde<String> STRING_SERDE = Serdes.String();
    private static final Serde<Long> LONG_SERDE = Serdes.Long();

    @Autowired
    public void buildPipeline(StreamsBuilder streamsBuilder)
    {
        System.out.println("----- Building the pipeline ----");
        // Read data from input topic as KStream
        final KStream<String, String> inputStream = streamsBuilder.stream(inputTopicName, Consumed.with(STRING_SERDE, STRING_SERDE));

        // Transform and aggregate the data to get a KTable
        final KTable<String, Long> wordCount = inputStream
                .peek((key,value) -> System.out.println("Received >> "+value))
                .mapValues((ValueMapper<String, String>) String::toLowerCase)
                .flatMapValues(lowerCaseText -> Arrays.asList(lowerCaseText.split("\\W+")))
                .groupBy((key,value) -> value, Grouped.with(STRING_SERDE, STRING_SERDE))
                .count();
                //.mapValues((key,val) -> String.valueOf(val));


        // Write this KTable to the output topic
        wordCount.toStream().to(
                outputTopicName, // Topic name
                Produced.with(
                        STRING_SERDE, // Output message's key type
                        LONG_SERDE // Output message's value type
                )
        );

    }
}
