package com.databases.synchronizer.main;


import com.databases.synchronizer.configuration.CassandraConnector;
import com.databases.synchronizer.configuration.ElasticsearchConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"com.databases.synchronizer.configuration", "com.databases.synchronizer.repository"})
public class Main implements CommandLineRunner{

    @Autowired
    ElasticsearchConnector elasticsearchConnector;

    @Autowired
    CassandraConnector cassandraConnector;

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(Main.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        elasticsearchConnector.client();
        cassandraConnector.cassandraMapping();
    }
}
