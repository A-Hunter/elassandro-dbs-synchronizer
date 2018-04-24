package com.databases.synchronizer.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "persons", type = "person")
public class Person implements Comparable<Person> {

    @PrimaryKey("id")
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private Integer age;

    private String occupation;

    @JsonCreator
    public Person(@JsonProperty(value = "id", required = true) String id,
                  @JsonProperty(value = "firstName") String firstName,
                  @JsonProperty(value = "lastName") String lastName,
                  @JsonProperty(value = "age") Integer age,
                  @JsonProperty(value = "occupation") String occupation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.occupation = occupation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    @Override
    public int compareTo(Person person) {
        return  id.compareTo(person.getId()) +
                firstName.compareTo(person.getFirstName()) +
                lastName.compareTo(person.getLastName()) +
                age.compareTo(person.getAge()) +
                occupation.compareTo(person.getOccupation());
    }
}
