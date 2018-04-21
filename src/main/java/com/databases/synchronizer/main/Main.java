package com.databases.synchronizer.main;


import com.databases.synchronizer.entity.Person;
import com.databases.synchronizer.repository.implemantation.CassandraRepository;
import com.databases.synchronizer.synchronization.Synchronizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication(scanBasePackages = {"com.databases.synchronizer"})
public class Main implements CommandLineRunner {

    @Autowired
    CassandraRepository cassandraRepository;

    @Autowired
    Synchronizer synchronizer;

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//1
//        cassandraRepository.create(new Person("1", "Isaac", "Netero", 125, "Hunter"));
//        cassandraRepository.create(new Person("2", "Takamora", "Mamoro", 29, "Boxer"));
//        cassandraRepository.create(new Person("3", "Gon", "Freecss", 15, "Hunter"));
//2
//        cassandraRepository.update(new Person("4", "Sishui", "Uchiha", 27, "Shinobi"));
//3
//        Person person = (Person) cassandraRepository.getById("id", "1", "person", Person.class);
//        System.out.println(person.getId() + "-" + person.getFirstName() + "-" + person.getLastName() + "-" + person.getAge() + "-" + person.getOccupation());
//4
//        List<Person> persons = cassandraRepository.getAll("person", Person.class);
//        persons.stream().forEach(person ->{
//            System.out.println(person.getId() + "-" + person.getFirstName() + "-" + person.getLastName() + "-" + person.getAge() + "-" + person.getOccupation());
//        });
//        cassandraRepository.delete("persons", "person", "4", Person.class);

//        cassandraRepository.synchronize("person", Person.class);

        synchronizer.synchronize("person", Person.class);
    }
}
