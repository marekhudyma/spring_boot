package mh.springboot;

import com.google.common.collect.ImmutableSet;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sampleentity")
public class SampleEntityController {

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<SampleEntity> getAll() {
        return ImmutableSet.of();
    }

    @RequestMapping(method=RequestMethod.POST)
    @ResponseStatus(value= HttpStatus.CREATED)
    public SampleEntity create(@RequestBody SampleEntity sampleEntity) {
        return sampleEntity;
    }
}
