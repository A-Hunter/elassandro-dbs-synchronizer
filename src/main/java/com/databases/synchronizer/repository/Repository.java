package com.databases.synchronizer.repository;

import com.databases.synchronizer.entity.Person;

import java.util.List;

public interface Repository<T> {

    T create(T person);
    T update(T person);
    T getById(Person person);
    List<T> getAll();
    void delete(Person person);
}
