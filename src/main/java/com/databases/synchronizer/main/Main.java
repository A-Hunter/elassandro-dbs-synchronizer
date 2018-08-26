package com.databases.synchronizer.main;


import com.databases.synchronizer.entity.Address;
import com.databases.synchronizer.entity.City;
import com.databases.synchronizer.entity.Person;
import com.databases.synchronizer.jms.Messenger;
import com.databases.synchronizer.repository.implemantation.CassandraRepository;
import com.databases.synchronizer.repository.implemantation.ElassandroRepository;
import com.databases.synchronizer.route.JmsRoute;
import com.databases.synchronizer.scheduler.Scheduler;
import com.databases.synchronizer.synchronization.Synchronizer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.ConnectionFactory;

/**
 * Created by Ghazi Ennacer on 14/04/2018.
 * Email: ghazi.ennacer@gmail.com
 */

@SpringBootApplication(scanBasePackages = {"com.databases.synchronizer"})
public class Main implements CommandLineRunner {

    static Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Autowired
    ElassandroRepository elassandroRepository;

    @Autowired
    Synchronizer synchronizer;

    @Autowired
    Scheduler scheduler;

    @Autowired
    Messenger messenger;

    @Autowired
    CassandraRepository cassandraRepository;

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//1     CREATE
//        elassandroRepository.create(new Person("1", "Isaac", "Netero", 125, "Hunter"));
//        elassandroRepository.create(new Address("1", "hunters street", "1bis", "North disctrict", "York city", "PC-9651"));
//2     UPDATE
//        elassandroRepository.update(new Person("2", "Itachi", "Uchiha", 26, "Shinobi"), "person");
//        elassandroRepository.update(new Address("1", "hunters street", "1bis", "East disctrict", "Konoha village", "PC-1254"), "address");
//3     GETBYID
//        Person person = (Person) elassandroRepository.getById("id", "1", "person", Person.class);
//        System.out.println(person.getId() + "-" + person.getFirstName() + "-" + person.getLastName() + "-" + person.getAge() + "-" + person.getOccupation());
//        Address address = (Address) elassandroRepository.getById("id", "1", "address", Address.class);
//        System.out.println(address.getId() + "-" + address.getName() + "-" + address.getStreet() + "-" +address.getDistrict() + "-" +address.getCity() + "-" +address.getPostcode());
//
//         Map<String, String> ids = new HashMap<>();
//         ids.put("id", "1");
//         ids.put("name", "hunters street");
//         ids.put("street", "1bis");
//         Address addr = (Address) elassandroRepository.getById(ids, "address", Address.class);
//         System.out.println(addr.getId() + "-" + addr.getName() + "-" + addr.getStreet() + "-" +addr.getDistrict() + "-" +addr.getCity() + "-" +addr.getPostcode());
//4       GETALL
//        List<Person> persons = elassandroRepository.getAll("person", Person.class);
//        persons.forEach(person ->{
//            System.out.println(person.getId() + "-" + person.getFirstName() + "-" + person.getLastName() + "-" + person.getAge() + "-" + person.getOccupation());
//        });
//
//
//        List<Address> addresses = elassandroRepository.getAll("address", Address.class);
//        addresses.forEach(address ->{
//            System.out.println(address.getId() + "-" + address.getName() + "-" + address.getStreet() + "-" +address.getDistrict() + "-" +address.getCity() + "-" +address.getPostcode());
//        });
//5     DELETE
//        elassandroRepository.delete(new Person("2", "Itachi", "Uchiha", 26, "Shinobi"));
//6     Synchronize
//        synchronizer.synchronize("person", Person.class);
//        synchronizer.synchronize("address", Address.class);
//7     findAllElasticsearchIds
//        System.out.println("From Elasticsearch");
//        List<String> addresses = elassandroRepository.findAllElasticsearchIds("addresses");
//        addresses.forEach(id -> {
//            System.out.println(id);
//        });
//8     getAllCassandraIds
//        System.out.println("From Cassandra");
//        List<String> result = elassandroRepository.getAllCassandraIds("address", Address.class);
//        result.forEach(id -> {
//            System.out.println(id);
//        });
//9
//        synchronizer.synchronize("persons","person", Person.class);
//        synchronizer.synchronize("addresses", "address", Address.class);
//10
//        scheduler.schedule("persons", "person", Person.class);
//        scheduler.schedule("addresses", "address", Address.class);
//        scheduler.schedule("cities", "city", City.class);

//11 JMS
//    messenger.send("INPUTQUEUE.D", new Person("10", "Hisoka","Morow",29, "Hunter").toMap());

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new JmsRoute());
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        ProducerTemplate template = context.createProducerTemplate();
        context.start();
        template.sendBody("activemq:queue:inbound.endpoint", new Person("10", "Hisoka","Morow",29, "Hunter"));
//12
//    cassandraRepository.create(new Person("10", "Hisoka","Morow",29, "Hunter"));

//        elassandroRepository.create(new Person("2", "Takamora", "Mamoro", 29, "Boxer"));
//        elassandroRepository.update(new Person("3", "Gon", "Freecss", 15, "Hunter"));
//        elassandroRepository.update(new Person("4", "Sishui", "Uchiha", 27, "Shinobi"));
//        elassandroRepository.synchronize("person", Person.class);

//        scheduler.schedule("person", Person.class);
//        scheduler.schedule("address", Address.class);
//        elassandroRepository.create(new Address("1", "hunters street", "1bis", "North disctrict", "York city", "PC-9651"));
//        elassandroRepository.create(new Address("2", "sharingan street", "2bis", "South disctrict", "Konoha village", "PC-1254"));
//        elassandroRepository.update(new Address("2", "mangekyou sharingan street", "3bis", "East disctrict", "Konoha village", "PC-1254"),"id", "1", "person", Person.class);
//        elassandroRepository.update(new Address("2", "sharingan street", "2bis", "East disctrict", "Hidden leaf village", "PC-6547"));
    }
}
