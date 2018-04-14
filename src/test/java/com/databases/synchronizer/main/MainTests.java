package com.databases.synchronizer.main;


import com.databases.synchronizer.configuration.CassandraConnector;
import com.databases.synchronizer.configuration.ElasticsearchConnector;
import com.datastax.driver.core.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void connectToCassandra(){
        CassandraConnector client = new CassandraConnector();
//        client.connect("127.0.0.1", 9042);
//        this.session = client.getSession();

        System.out.println(client.cluster());
        System.out.println(client.getKeyspaceName());
    }

    @Test
    public void connectToElasticsearch(){
        try {
            System.setProperty("es.set.netty.runtime.available.processors", "false");
            ElasticsearchConnector connector = new ElasticsearchConnector();
            connector.client();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
