package com.bootcamp.transaction.util.kafka;

import com.bootcamp.transaction.account.domain.TAccount;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
  @KafkaListener(
    topics = "movimientos",
    groupId = "group_id",
    containerFactory = "TAccountListener"
  )

  // Imprime desde topico
  public void consume(TAccount tAccount) {
    System.out.println("TAccount Consumer topic = " + tAccount);

  }
}
