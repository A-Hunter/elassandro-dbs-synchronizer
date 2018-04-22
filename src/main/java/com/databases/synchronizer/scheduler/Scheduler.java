package com.databases.synchronizer.scheduler;

import com.databases.synchronizer.entity.Person;
import com.databases.synchronizer.synchronization.Synchronizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Scheduler {

    @Autowired
    Synchronizer synchronizer;

    private final ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

    public void schedule(String table, Class clazz){

         service.scheduleAtFixedRate(() -> {
         synchronizer.synchronize(table, clazz);
         }, 0, 1, TimeUnit.SECONDS);
    }
}
