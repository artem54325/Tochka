package com.example.test.data;

import com.example.test.model.Subscriber;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface SubscriberRepository extends CrudRepository<Subscriber, UUID> {
}
