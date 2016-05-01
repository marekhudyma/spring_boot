package mh.springboot.controller;

import mh.springboot.model.SampleEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class SampleTemplate {

    private static Map<Long, SampleEntity> map = new HashMap<>();

    static {
        map.put(1l, createSampleEntity(1l));
        map.put(2l, createSampleEntity(2l));
        map.put(3l, createSampleEntity(3l));
        map.put(4l, createSampleEntity(4l));
        map.put(5l, createSampleEntity(5l));
        map.put(6l, createSampleEntity(6l));
    }

    private static SampleEntity createSampleEntity(long i) {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setId(i);
        sampleEntity.setName("name" + i);
        sampleEntity.setUuid(UUID.randomUUID());
        return sampleEntity;
    }

    @RequestMapping("/sampleTemplate")
    public String onLoad(Model model){
        model.addAttribute("sampleEntities", map.values());
        return "sampleTemplate";
    }
}
