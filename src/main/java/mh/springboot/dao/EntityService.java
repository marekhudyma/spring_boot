package mh.springboot.dao;

import mh.springboot.model.Entity;

import java.util.Collection;

public interface EntityService {

    Entity create(Entity entity);

    Entity update(Entity entity);

    void delete(Long id);

    void deleteAll();

    Entity findById(Long id);

    Collection<Entity> findAll();

}
