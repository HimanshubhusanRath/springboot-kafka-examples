# Word Counter Application using Spring boot + Kafka Streams

## Configure Word-Count-Producer
* This application pushes some text messages to the Kafka input topic (word.input)
* Steps to configure:
  * Configure a ProducerFactory bean using the required producer properties.
  * Configure a KafkaTemplate bean using this above producer factory.
  
  
## Configure Word-Count-Stream-Processor
* This application reads text messages from kafka topic (word.input), calculates the count per word and pushes this count information to another kafka topic (word.out)
* Steps to configure:
  * Configure a KafkaStreamsConfiguration bean (name = defaultKafkaStreamsConfig) using the required stream properties.
  * Define the topology / pipeline for processing by using a StreamsBuilder.  
   * Note: Topology can be visualized in https://zz85.github.io/kafka-streams-viz/ site.
  
  
## Configure Word-Count-Consumer
* This application reads word counts from the Kafka ouput topic (word.out)
* Steps to configure:
  * Configure a ConsumerFactory bean using the required consumer properties.
  * Configure a KafkaListenerContainerFactory bean using the above consumer factory.
  * Define the consume method with @KafkaListener.
    * Note: The default properties / configurations can be overridden for the consumer in the @KafkaListener.
