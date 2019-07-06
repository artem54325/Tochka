package com.example.test.service;



import com.example.test.model.Subscriber;
import com.example.test.data.SubscriberRepository;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Component
@ComponentScan
public class ServiceHold {
    @Autowired
    private SubscriberRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(ServiceHold.class);

    @Scheduled(cron = "* */10 * * * ?")
    public void cleaningHold() {
        logger.info("cleaningHold");

        List<Subscriber> subscribers = Lists.newArrayList(repository.findAll());
        for (int i=0;i<subscribers.size();i++){
            subscribers.get(i).setBalance(subscribers.get(i).getBalance()-subscribers.get(i).getHold());
            subscribers.get(i).setHold(0);
        }
        repository.saveAll(subscribers);
    }
}
