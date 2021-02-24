package org.mskcc.cmo;

import org.mskcc.cmo.messaging.Gateway;
import org.mskcc.cmo.messaging.MessageConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.mskcc.cmo.messaging"})
public class NewRequestSubscriber implements CommandLineRunner{
    @Autowired
    Gateway messaging;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(NewRequestSubscriber.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            messaging.connect();
            messaging.subscribe("cmo.new-request", Object.class, new MessageConsumer() {
                @Override
                public void onMessage(Object message) {
                    System.out.printf("Received a message: %s\n", message.toString());                
                }
            }); 
            //messaging.shutdown();
       } catch(Exception e) {
           e.printStackTrace();
       }        
    }    
}
