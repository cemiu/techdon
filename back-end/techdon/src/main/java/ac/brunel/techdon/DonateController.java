package ac.brunel.techdon;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class DonateController {


    @RequestMapping("api/donate")
    public String donate(){
        return null;

    }
   
    @RequestMapping("api/donate/upload")
    public String uploadpictures(){
        return null;

    }
    @RequestMapping("api/donate/products")
    public String products(){
        return null;

    }
   

    
}
