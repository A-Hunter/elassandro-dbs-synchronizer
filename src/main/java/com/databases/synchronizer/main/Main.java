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
//1     CREATE
//        cassandraRepository.create(new Person("1", "Isaac", "Netero", 125, "Hunter"));
//        cassandraRepository.create(new Address("1", "hunters street", "1bis", "North disctrict", "York city", "PC-9651"));
//2     UPDATE
//        cassandraRepository.update(new Person("1", "Itachi", "Uchiha", 27, "Shinobi"));
//        cassandraRepository.update(new Address("1", "hunters street", "1bis", "East disctrict", "Konoha village", "PC-1254"));
//3     GETBYID
//        Person person = (Person) cassandraRepository.getById("id", "1", "person", Person.class);
//        System.out.println(person.getId() + "-" + person.getFirstName() + "-" + person.getLastName() + "-" + person.getAge() + "-" + person.getOccupation());
//        Address address = (Address) cassandraRepository.getById("id", "1", "address", Address.class);
//        System.out.println(address.getId() + "-" + address.getName() + "-" + address.getStreet() + "-" +address.getDistrict() + "-" +address.getCity() + "-" +address.getPostcode());
//
//         Map<String, String> ids = new HashMap<>();
//         ids.put("id", "1");
//         ids.put("name", "hunters street");
//         ids.put("street", "1bis");
//         Address addr = (Address) cassandraRepository.getById(ids, "address", Address.class);
//         System.out.println(addr.getId() + "-" + addr.getName() + "-" + addr.getStreet() + "-" +addr.getDistrict() + "-" +addr.getCity() + "-" +addr.getPostcode());
//4       GETALL
//        List<Person> persons = cassandraRepository.getAll("person", Person.class);
//        persons.forEach(person ->{
//            System.out.println(person.getId() + "-" + person.getFirstName() + "-" + person.getLastName() + "-" + person.getAge() + "-" + person.getOccupation());
//        });
//
//
//        List<Address> addresses = cassandraRepository.getAll("address", Address.class);
//        addresses.forEach(address ->{
//            System.out.println(address.getId() + "-" + address.getName() + "-" + address.getStreet() + "-" +address.getDistrict() + "-" +address.getCity() + "-" +address.getPostcode());
//        });
//5     DELETE
//        cassandraRepository.delete(new Address("1", "hunters street", "1bis", "East disctrict", "Konoha village", "PC-1254"));






//        cassandraRepository.create(new Person("2", "Takamora", "Mamoro", 29, "Boxer"));
//        cassandraRepository.update(new Person("3", "Gon", "Freecss", 15, "Hunter"));
//        cassandraRepository.update(new Person("4", "Sishui", "Uchiha", 27, "Shinobi"));
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
