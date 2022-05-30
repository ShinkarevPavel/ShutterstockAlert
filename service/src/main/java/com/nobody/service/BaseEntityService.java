package com.nobody.service;

import java.util.List;

public interface BaseEntityService<T> {
    void addEntity(T t);
    void removeEntity(T t);
    T updateEntity(T t);
    T getEntity(T t);
    List<T> getAll();
}
