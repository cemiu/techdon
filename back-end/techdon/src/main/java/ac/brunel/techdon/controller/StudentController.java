package ac.brunel.techdon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    @RequestMapping("student/devices/loadPreferences")
    public String studentDevicesLoadPreferences(){
        return null;
//        end point to register an account - check documentation for more info on inputs / outputs expected
    }
    @RequestMapping("student/devices/setPreferences")
    public String studentDevicesSetPreferences(){
        return null;
//        end point to set preferences for a device - check documentation for more info on inputs / outputs expected
    }
    @GetMapping ("student/devices/offeredDevices")
    public String studentDevicesOfferedDevices(){
        return null;
//        end point to set to all devices offered to a student - check documentation for more info on inputs / outputs expected
    }
    @GetMapping ("student/devices/load")
    public String studentDevicesLoad(){
        return null;
//        end point to set to all Load student Devices that is offered - check documentation for more info on inputs / outputs expected
    }
    @GetMapping("student/devices/claim")
    public String studentDevicesClaim(){
        return null;
//        end point to set to allow a student to claim -  check documentation for more info on inputs / outputs expected
    }
    @GetMapping("student/devices/decline")
    public String studentDevicesDecline(){
//       end point to decline a device -  check documentation for more info on inputs / outputs expected
        return null;
    }



}
    

