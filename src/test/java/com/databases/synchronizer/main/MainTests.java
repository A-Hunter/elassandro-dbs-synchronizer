package com.databases.synchronizer.main;


import com.databases.synchronizer.configuration.CassandraConnector;
import com.datastax.driver.core.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTests {

    private Session session;

    @Test
    public void contextLoads() {
    }

    @Test
    public void connect(){
        CassandraConnector client = new CassandraConnector();
        client.connect("127.0.0.1", 9042);
        this.session = client.getSession();
    }

}
