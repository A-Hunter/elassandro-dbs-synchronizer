package com.databases.synchronizer.scheduler;

import com.databases.synchronizer.synchronization.Synchronizer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Scheduler {

    static Logger LOGGER = Logger.getLogger(Scheduler.class.getName());

    @Autowired
    Synchronizer synchronizer;

    private final ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

    public void schedule(String table, Class clazz) {
        try {
            service.scheduleAtFixedRate(() -> {
                try {
                    synchronizer.synchronize(table, clazz);
                } catch (Exception e) {
                    LOGGER.error("Error when trying to synchronize data :" + e);
                }
            }, 0, 1, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.error("Error when trying to schedule the synchronization for the table '" + table + "' : " + e + " - " + e.getCause());
        }
    }
}
