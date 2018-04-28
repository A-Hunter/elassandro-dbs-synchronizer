package com.databases.synchronizer.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * Created by Ghazi Ennacer on 24/04/2018.
 * Email: ghazi.ennacer@gmail.com
 */

@PrimaryKeyClass
@Document(indexName = "addresses", type = "address")
public class Address implements Serializable {

    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String id;

    @PrimaryKeyColumn(name = "name", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String name;

    @PrimaryKeyColumn(name = "street", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    private String street;

    private String district;
    private String city;
    private String postcode;

    @JsonCreator
    public Address(@JsonProperty("id") String id, @JsonProperty("name") String name,
                   @JsonProperty("street") String street, @JsonProperty("district") String district,
                   @JsonProperty("city") String city, @JsonProperty("postcode") String postcode) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.district = district;
        this.city = city;
        this.postcode = postcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
