package com.databases.synchronizer.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Ghazi Naceur on 04/05/2018.
 */
@Component
public class Messenger {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String destination, Object message) {
        jmsTemplate.convertAndSend(destination, message);
    }

    public Object receive(String destination){
        return jmsTemplate.receiveAndConvert(destination);
    }
}
