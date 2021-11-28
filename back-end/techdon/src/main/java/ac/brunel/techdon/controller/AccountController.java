package ac.brunel.techdon.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class AccountController {



    @GetMapping("account/login")
    public String adminEditAccount(){
        return null;

    }
    @RequestMapping("account/logout")
    public String adminDelete(){
        return null;

    }
    @RequestMapping("account/delete")
    public String adminVerifyID(){
        return null;

    }

    
}
