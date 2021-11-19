package ac.brunel.techdon;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class LoginController {


    @RequestMapping("api/account/login")
    public String login(){
        return null;

    }
    @RequestMapping("api/account/logout")
    public String logout(){
        return null;

    }
    @RequestMapping("api/account/delete")
    public String delete(){
        return null;

    }
    
}
