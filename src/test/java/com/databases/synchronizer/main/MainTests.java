package com.databases.synchronizer.main;


import com.databases.synchronizer.configuration.CassandraConnector;
import com.databases.synchronizer.configuration.ElasticsearchConnector;
import com.databases.synchronizer.entity.Address;
import com.databases.synchronizer.entity.Person;
import com.databases.synchronizer.repository.implemantation.ElassandroRepository;
import com.databases.synchronizer.synchronization.Synchronizer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Ghazi Ennacer on 14/04/2018.
 * Email: ghazi.ennacer@gmail.com
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTests {

    @Autowired
    ElassandroRepository elassandroRepository;

    @Autowired
    Synchronizer synchronizer;

    @Test
    public void contextLoads() {
    }

    @Test
    public void connectToCassandra() {
        CassandraConnector client = new CassandraConnector();
        try {
            client.cassandraMapping();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void connectToElasticsearch() {
        try {
            System.setProperty("es.set.netty.runtime.available.processors", "false");
            ElasticsearchConnector connector = new ElasticsearchConnector();
            connector.client();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createAndGetByIdTest() {
        elassandroRepository.create(new Person("5", "Zino", "Zoldick", 75, "Assassin"));
        Person person = (Person) elassandroRepository.getById("id", "5", "person", Person.class);
        Assert.assertEquals("5", person.getId());
        Assert.assertEquals("Zino", person.getFirstName());
        Assert.assertEquals("Zoldick", person.getLastName());
        Assert.assertEquals(75, (long) person.getAge());
        Assert.assertEquals("Assassin", person.getOccupation());
    }

    @Test
    public void updateTest() {
        elassandroRepository.update(new Person("5", "Silver", "Zoldick", 50, "Assassin"), "person");
        Person person = (Person) elassandroRepository.getById("id", "5", "person", Person.class);
        Assert.assertEquals("5", person.getId());
        Assert.assertEquals("Silver", person.getFirstName());
        Assert.assertEquals("Zoldick", person.getLastName());
        Assert.assertEquals(50, (long) person.getAge());
        Assert.assertEquals("Assassin", person.getOccupation());
    }

    @Test
    public void getAllTest() {
        List<Person> persons = elassandroRepository.getAll("person", Person.class);
        Assert.assertNotEquals(null, persons.size());
    }

    @Test
    public void synchronizeTest(){
        Assert.assertTrue(synchronizer.synchronize("persons","person", Person.class));
    }

    @Test
    public void deleteTest() {
        elassandroRepository.create(new Person("5", "Zino", "Zoldick", 75, "Assassin"));
        elassandroRepository.delete(new Person("5", "Zino", "Zoldick", 75, "Assassin"));
        Person p = (Person) elassandroRepository.getById("id", "5", "person", Person.class);
        Assert.assertEquals(null, p);
    }

    @Test
    public void createAddressTest(){
        elassandroRepository.create(new Address("1", "hunters street", "1bis", "North disctrict", "York city", "PC-9651"));
    }


}
