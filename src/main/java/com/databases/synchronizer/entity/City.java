package com.databases.synchronizer.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * Created by Ghazi Naceur on 29/04/2018.
 * Email: ghazi.ennacer@gmail.com
 */

@Document(indexName = "cities", type = "city")
public class City implements Serializable {

    @PrimaryKeyColumn(name = "name", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String name;

    @PrimaryKeyColumn(name = "prefecture", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String prefecture;

    private String country;

    private Long population;

    @JsonCreator
    public City(@JsonProperty("name") String name, @JsonProperty("prefecture") String prefecture,
                @JsonProperty("country") String country, @JsonProperty("population") Long population) {
        this.name = name;
        this.prefecture = prefecture;
        this.country = country;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }
}
