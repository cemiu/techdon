package ac.brunel.techdon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ProfileController {


    @RequestMapping("api/profile")
    public String profile(){
        return null;

    }
    @RequestMapping("api/profile/update")
    public String updateDetails(){
        return null;

    }
    @RequestMapping("api/profile/edit")
    public String editProfile(){
        return null;

    }
    
}
