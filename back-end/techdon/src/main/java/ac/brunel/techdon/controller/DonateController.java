package ac.brunel.techdon.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DonateController {


    @RequestMapping("donate/device/new")
    public String donate(){
        return null;

    }
   
    @RequestMapping("donate/device/getListed")
    public String uploadpictures(){
        return null;

    }
    @RequestMapping("donate/device/load")
    public String products(){
        return null;

    }
   

    
}
