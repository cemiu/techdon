package ac.brunel.techdon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    /**
     * endpoint to register an account
     * check documentation for more info on inputs / outputs expected
     */
    @RequestMapping("/api/student/devices/loadPreferences")
    public String studentDevicesLoadPreferences(){
        return null;
    }

    /**
     * endpoint to set preferences for which devices the students wants
     * check documentation for more info on inputs / outputs expected
     */
    @RequestMapping("/api/student/devices/setPreferences")
    public String studentDevicesSetPreferences(){
        return null;
    }

    /**
     * endpoint to set to all devices offered to a student
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping ("/api/student/devices/offeredDevices")
    public String studentDevicesOfferedDevices(){
        return null;
    }

    /**
     * endpoint to set to all Load student Devices that is offered
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping ("/api/student/devices/load")
    public String studentDevicesLoad(){
        return null;
    }

    /**
     * endpoint to set to allow a student to claim
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping("/api/student/devices/claim")
    public String studentDevicesClaim(){
        return null;
    }

    /**
     * endpoint to decline an offered device
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping("/api/student/devices/decline")
    public String studentDevicesDecline(){
        return null;
    }

}
    

