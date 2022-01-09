package ac.brunel.techdon.controller;

import org.springframework.web.bind.annotation.*;
// NOTE
//I HAVE ADDED ALL END POINTS TO THE FRONT END BUT YET THERE IS A FEW
// THINGS LEFT TO DO AS I AM NOT 100% SURE ON A FEW OF THE FRONT END WORK I WILL SPEAK TO AHEMAD
// THANK YOU THOY - PLZ DM OR EMAIL ME FOR MORE INFO

@RestController
public class AccountController {

    //This end point will be all things the user in general will use to
    // communicate with Tech donates website ,
    // this includes registering for an account , making changes to the profile , login , log out and delete .
    // further information can be found in the end point documentation

    /**
     * end point to register an account
     * check documentation for more info on inputs / outputs expected
     */
    @PostMapping ("/api/account/register")
    public String accountRegister(){
        return "register";
    }

    /**
     * Endpoint to logging in
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping("/api/account/login")
    public String accountLogin(){
        return "sa";
    }


    /**
     * Endpoint for logging out
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping("/api/account/logout")
    public String accountLogout(){
        return null;
    }

    /**
     * Endpoint to Delete Account
     * check documentation for more info on inputs / outputs expected
     */
    @DeleteMapping("/api/account/delete")
    public String accountDelete(){
        return null;
    }


    @RequestMapping("/api/account/verifyEmail")
    public String accountVerifyEmail(){
        return "email";
//         Endpoint to VerifyEmail - check documentation for more info on inputs / outputs expected
    }

    /**
     * Endpoint to get a user's account settings
     * check documentation for more info on inputs / outputs expected
     */
    @RequestMapping("/api/account/settings/get")
    public String accountSettingsGet(){
        return null;
    }

    /**
     * Endpoint to update account settings
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping("/api/account/settings/update")
    public String accountSettingsUpdate(){
        return null;
    }
}
