package com.example.atlasrampupandrewbennett;

import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AssetsIngestTestListener {

  ConcurrentLinkedQueue<String> listenerQueue = new ConcurrentLinkedQueue<>();

    @KafkaListener(topics = "integrated-data", groupId = "test")
  public void testListener(String message){
    log.info("Verifier Listener received message:" + message);
    listenerQueue.add(message);
  }

  public ConcurrentLinkedQueue<String> getListenerQueue(){
      return listenerQueue;
  }

}
