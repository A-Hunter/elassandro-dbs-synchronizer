package com.databases.synchronizer.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.elasticsearch.annotations.Document;

@Table("person")
@Document(indexName = "persons", type = "person")
public class Person extends Entity{

    private String firstName;

    private String lastName;

    private Integer age;

    private String occupation;

    public Person(String personId, String firstName, String lastName, Integer age, String occupation) {
//        this.personId = personId;
        super(personId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.occupation = occupation;
    }

//    public Integer getPersonId() {
//        return personId;
//    }
//
//    public void setPersonId(Integer personId) {
//        this.personId = personId;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
