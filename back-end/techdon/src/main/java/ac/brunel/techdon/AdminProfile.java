package ac.brunel.techdon;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class AdminProfile {


    @RequestMapping("api/admin/profile")
    public String adminProfile(){
        return null;

    }
    @RequestMapping("api/admin/edit")
    public String adminEditAccount(){
        return null;

    }
    @RequestMapping("api/admin/delete")
    public String adminDelete(){
        return null;

    }
    @RequestMapping("api/admin/verifyID")
    public String adminVerifyID(){
        return null;

    }
    
}
