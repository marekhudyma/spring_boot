package mh.springboot.controller;

import mh.springboot.controller.error.ErrorCode;
import mh.springboot.controller.exception.BadRequestException;
import mh.springboot.controller.exception.ResourceNotFoundException;
import mh.springboot.dao.SampleEntityService;
import mh.springboot.model.SampleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sampleentity")
public class SampleEntityController {

    @Autowired
    @Qualifier("SampleEntityCachingDecoratorService")
    private SampleEntityService sampleEntityService;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<SampleEntity> getAll() {
        return sampleEntityService.findAll();
    }

    @RequestMapping(method= RequestMethod.GET, value="{page}/{size}")
    public Page<SampleEntity> getAllPagination(@PathVariable Integer page, @PathVariable Integer size) {
        return sampleEntityService.findAll(new PageRequest(page, size));
    }

    @RequestMapping(method=RequestMethod.GET, value="{id}")
    public SampleEntity getById(@PathVariable Long id) {
        if(id < 1) {
            throw new BadRequestException("invalid id", ErrorCode.BAD_REQUEST);
        }
        SampleEntity sampleEntity = sampleEntityService.findOne(id);
        if(sampleEntity == null) {
            throw new ResourceNotFoundException("entity does not exist", ErrorCode.NOT_FOUND);
        }
        return sampleEntity;
    }

    @RequestMapping(method=RequestMethod.POST)
    @ResponseStatus(value= HttpStatus.CREATED)
    public SampleEntity create(@RequestBody SampleEntity sampleEntity) {
        return sampleEntityService.save(sampleEntity);
    }

    @RequestMapping(method=RequestMethod.PUT, value="{id}")
    @ResponseStatus(value= HttpStatus.OK)
    public SampleEntity update(@PathVariable long id, @RequestBody SampleEntity sampleEntity) {
        if(id != sampleEntity.getId()) {
            throw new BadRequestException("wrong id", ErrorCode.BAD_REQUEST);
        }
        return sampleEntityService.save(sampleEntity);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        sampleEntityService.delete(id);
    }

}
