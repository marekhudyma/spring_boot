package mh.springboot.controller;

import mh.springboot.dao.EntityService;
import mh.springboot.model.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/entity")
public class EntityController {

    @Autowired
    EntityService entityService;

    @RequestMapping(method= RequestMethod.GET)
    public Collection<Entity> getAll() {
        return entityService.findAll();
    }

    @RequestMapping(method=RequestMethod.GET, value="{id}")
    public Entity getById(@PathVariable Long id) {
        return entityService.findById(id);
    }

    @RequestMapping(method=RequestMethod.POST)
    @ResponseStatus(value= HttpStatus.CREATED)
    public Entity create(@RequestBody Entity entity) {
        return entityService.create(entity);
    }

    @RequestMapping(method=RequestMethod.PUT)
    @ResponseStatus(value= HttpStatus.OK)
    public Entity update(@RequestBody Entity entity) {
        return entityService.update(entity);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        entityService.delete(id);
    }



}
