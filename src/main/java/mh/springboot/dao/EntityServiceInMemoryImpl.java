package mh.springboot.dao;

import mh.springboot.model.Entity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class EntityServiceInMemoryImpl implements EntityService {

    private final Map<Long, Entity> entities = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public EntityServiceInMemoryImpl() {
        create(new Entity.Builder().withId(1l).withName("entity1").build());
        create(new Entity.Builder().withId(2l).withName("entity2").build());
        create(new Entity.Builder().withId(3l).withName("entity3").build());
    }

    public Entity create(Entity entity) {
        Long id = counter.incrementAndGet();
        entity.setId(id);
        entities.put(id, entity);
        return entity;
    }

    public Entity update(Entity entity) {
        entities.put(entity.getId(), entity);
        return entity;
    }

    public void delete(Long id) {
        if(entities.containsKey(id)) {
            entities.remove(id);
        }
    }

    public void deleteAll() {
        entities.clear();
    }

    public Entity findById(Long id) {
        return entities.get(id);
    }

    public Collection<Entity> findAll() {
        return entities.values();
    }

}
