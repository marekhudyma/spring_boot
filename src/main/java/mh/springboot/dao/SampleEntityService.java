package mh.springboot.dao;

import mh.springboot.model.SampleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface SampleEntityService extends CrudRepository<SampleEntity, Long> {

    List<SampleEntity> findByUuidIn(List<UUID> uuid);

    @Query("select se from SampleEntity se where se.name like %?1")
    List<SampleEntity> findByNameEndsWith(String name);

}
