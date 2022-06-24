package com.nobody.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    void addEntity(T t);

    void removeEntity(Integer id);

    T updateEntity(T t);

    List<T> getAll();

    Optional<T> getEntity(String parameter);
}
