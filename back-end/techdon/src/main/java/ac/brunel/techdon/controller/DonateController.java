package ac.brunel.techdon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DonateController {

    @PostMapping("donate/device/new")
    public String donateDeviceNew(){
        return "services"; // NOTE - i am not sure yet of what this oes i will ask ahmed for more info , THOY
//        end point to donate a new device - check documentation for more info on inputs / outputs expected
    }
    @PostMapping("/donate/device/listedDevices")
    public String donateDeviceListedDevices(){
        return null;
//        end point to show listed Devices - check documentation for more info on inputs / outputs expected
    }
    @GetMapping ("donate/device/load")
    public String donateDeviceLoad(){
        return null;
//        end point to load all the devices uploaded by the user - check documentation for more info on inputs / outputs expected
    }
    @RequestMapping("donate/device/remove")
    public String donateDeviceRemove(){
        return null;
//        end point to remove a device which has been uploaded - check documentation for more info on inputs / outputs expected
    }
    @RequestMapping("donate/device/load")
    public String donateDeviceUpdate(){
        return null;
//        end point to update information on a device - check documentation for more info on inputs / outputs expected
    }
   

    
}
