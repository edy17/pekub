package org.diehl.gateway;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@EnableKafka
public class GatewayApplication {

  private final Logger logger = LoggerFactory.getLogger(GatewayApplication.class);

  @Autowired
  private KafkaTemplate<Integer, String> kafkaTemplate;

  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }

  @KafkaListener(topics = "pong-gateway", groupId = "pong-gateway-consumer")
  public void onReceivePongMessage(ConsumerRecord message) {
    logger.info("Gateway receiving pong message: {}", message.value());
    kafkaTemplate.send("ping", message.value().toString());
  }

  @KafkaListener(topics = "ping-gateway", groupId = "ping-gateway-consumer")
  public void onReceivePingMessage(ConsumerRecord message) {
    logger.info("Gateway receiving ping message: {}", message.value());
    kafkaTemplate.send("pong", message.value().toString());
  }
}
