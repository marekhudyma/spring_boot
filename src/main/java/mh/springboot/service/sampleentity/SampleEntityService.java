package mh.springboot.service.sampleentity;

import mh.springboot.model.SampleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface SampleEntityService extends CrudRepository<SampleEntity, Long> {

    // custom methods

    List<SampleEntity> findByUuidIn(List<UUID> uuids);

    @Query("select se from SampleEntity se where se.name like %?1")
    List<SampleEntity> findByNameEndsWith(String name);

    Page<SampleEntity> findAll(Pageable pageable);

}
