package mh.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleTemplate {

    @RequestMapping("/sampleTemplate")
    public String getProductById(){ //Model model){

        //model.addAttribute("product", productService.getProduct(id));

        return "sampleTemplate";
    }
}
