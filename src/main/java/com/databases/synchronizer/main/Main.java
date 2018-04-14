package com.databases.synchronizer.main;


import com.databases.synchronizer.configuration.CassandraConnector;
import com.databases.synchronizer.configuration.ElasticsearchConnector;
import com.databases.synchronizer.entity.Person;
import com.databases.synchronizer.repository.implemantation.CassandraRepository;
import com.databases.synchronizer.repository.implemantation.ElasticsearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"com.databases.synchronizer.configuration", "com.databases.synchronizer.repository"})
public class Main implements CommandLineRunner{

    @Autowired
    CassandraRepository cassandraRepository;

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        cassandraRepository.create(new Person(1, "Isaac", "Netero", 125, "Hunter"));

    }
}
