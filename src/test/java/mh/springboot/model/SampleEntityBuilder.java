package mh.springboot.model;

import java.util.UUID;

public class SampleEntityBuilder {

    public static SampleEntity create(Long id, String name, UUID uuid) {
        SampleEntity entity = new SampleEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setUuid(uuid);
        return entity;
    }

    public static SampleEntity create(String name, UUID uuid) {
        SampleEntity entity = new SampleEntity();
        entity.setName(name);
        entity.setUuid(uuid);
        return entity;
    }

}
