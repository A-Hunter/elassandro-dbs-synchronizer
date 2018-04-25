package com.databases.synchronizer.main;


import com.databases.synchronizer.entity.Address;
import com.databases.synchronizer.entity.Person;
import com.databases.synchronizer.repository.implemantation.CassandraRepository;
import com.databases.synchronizer.scheduler.Scheduler;
import com.databases.synchronizer.synchronization.Synchronizer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootApplication(scanBasePackages = {"com.databases.synchronizer"})
public class Main implements CommandLineRunner {

    static Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Autowired
    CassandraRepository cassandraRepository;

    @Autowired
    Synchronizer synchronizer;

    @Autowired
    Scheduler scheduler;

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//1
//        cassandraRepository.create(new Person("1", "Isaac", "Netero", 125, "Hunter"));
//        cassandraRepository.update(new Person("1", "Itachi", "Uchiha", 27, "Shinobi"));
//        cassandraRepository.create(new Person("2", "Takamora", "Mamoro", 29, "Boxer"));
//        cassandraRepository.update(new Person("3", "Gon", "Freecss", 15, "Hunter"));
//2
//        cassandraRepository.update(new Person("4", "Sishui", "Uchiha", 27, "Shinobi"));
//3
//        Person person = (Person) cassandraRepository.getById("id", "1", "person", Person.class);
//        System.out.println(person.getId() + "-" + person.getFirstName() + "-" + person.getLastName() + "-" + person.getAge() + "-" + person.getOccupation());

//        Address address = (Address) cassandraRepository.getById("id", "2", "address", Address.class);
//        System.out.println(address.getId() + "-" + address.getName() + "-" + address.getStreet() + "-" +address.getDistrict() + "-" +address.getCity() + "-" +address.getPostcode());

        Map<String, String> ids = new HashMap<>();
        ids.put("id", "2");
        ids.put("name", "sharingan street");
        ids.put("street", "2bis");
        Address address = (Address) cassandraRepository.getById(ids, "address", Address.class);
        System.out.println(address.getId() + "-" + address.getName() + "-" + address.getStreet() + "-" +address.getDistrict() + "-" +address.getCity() + "-" +address.getPostcode());

//4
//        List<Person> persons = cassandraRepository.getAll("person", Person.class);
//        persons.stream().forEach(person ->{
//            System.out.println(person.getId() + "-" + person.getFirstName() + "-" + person.getLastName() + "-" + person.getAge() + "-" + person.getOccupation());
//        });
//        cassandraRepository.delete("persons", "person", "4", Person.class);

//        cassandraRepository.synchronize("person", Person.class);

//        synchronizer.synchronize("person", Person.class);
//        synchronizer.synchronize("address", Address.class);

//        scheduler.schedule("person", Person.class);
//        scheduler.schedule("address", Address.class);

//        cassandraRepository.create(new Address("1", "hunters street", "1bis", "North disctrict", "York city", "PC-9651"));
//        cassandraRepository.create(new Address("2", "sharingan street", "2bis", "South disctrict", "Konoha village", "PC-1254"));
//        cassandraRepository.update(new Address("2", "mangekyou sharingan street", "3bis", "East disctrict", "Konoha village", "PC-1254"),"id", "1", "person", Person.class);
//        cassandraRepository.update(new Address("2", "sharingan street", "2bis", "East disctrict", "Hidden leaf village", "PC-6547"));

    }
}
