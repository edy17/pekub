package org.diehl.pong;

import java.util.Date;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableKafka
@EnableScheduling
public class PongApplication {

  private final Logger logger = LoggerFactory.getLogger(PongApplication.class);

  @Autowired
  private KafkaTemplate<Integer, String> kafkaTemplate;

  public static void main(String[] args) {
    SpringApplication.run(PongApplication.class, args);
  }

  @Scheduled(fixedDelay = 2000)
  public void publishMessage() {
    kafkaTemplate.send("pong-gateway", "Pong at: " + (new Date()).toLocaleString());
  }

  @KafkaListener(topics = "pong", groupId = "pong_consumer")
  public void onReceiveMessage(ConsumerRecord message) {
    logger.info("Pong receiving message: {}", message.value());
  }
}
