package org.diehl.ping;

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

@SpringBootApplication
@EnableKafka
public class PingApplication {

  private final Logger logger = LoggerFactory.getLogger(PingApplication.class);

  @Autowired
  private KafkaTemplate<Integer, String> kafkaTemplate;

  public static void main(String[] args) {
    SpringApplication.run(PingApplication.class, args);
  }

  @KafkaListener(topics = "ping", groupId = "ping-consumer")
  public void onReceiveMessage(ConsumerRecord message) {
    logger.info("Ping receiving message: {}, ", message.value());
    kafkaTemplate.send("ping-gateway", "Ping at: " + (new Date()).toLocaleString());
  }
}
