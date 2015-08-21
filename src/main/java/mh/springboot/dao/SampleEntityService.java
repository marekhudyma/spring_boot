package mh.springboot.dao;

import mh.springboot.model.SampleEntity;
import org.springframework.data.repository.CrudRepository;

public interface SampleEntityService extends CrudRepository<SampleEntity, Long> {

}
