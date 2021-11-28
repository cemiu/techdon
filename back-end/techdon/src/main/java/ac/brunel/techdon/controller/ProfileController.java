package ac.brunel.techdon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("account/verifyEmail")
    public String adminProfile(){
        return null;
    }
    @GetMapping("account/settings/get")
    public String adminEditAccount(){
        return null;
    }
    @RequestMapping("account/settings/update")
    public String adminDelete(){
        return null;
    }
    }
    

