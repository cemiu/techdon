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

    @PostMapping ("account/register")
    public String accountRegister(){
        return "register";
//        end point to register an account - check documentation for more info on inputs / outputs expected

    }
    @GetMapping("account/login")
    public String accountLogin(){
        return "sa";
//         End point to Login in - check documentation for more info on inputs / outputs expected
    }
    @GetMapping("account/logout")
    public String accountLogout(){
        return null;
//         End point for Log Out - - check documentation for more info on inputs / outputs expected
    }
    @DeleteMapping("account/delete")
    public String accountDelete(){
        return null;
//         End point to Delete Account - check documentation for more info on inputs / outputs expected
    }
    @RequestMapping("account/verifyEmail")
    public String accountVerifyEmail(){
        return "email";
//         End point to VerifyEmail - check documentation for more info on inputs / outputs expected
    }
    @RequestMapping("account/settings/get")
    public String accountSettingsGet(){
        return null;
//         End point to Get account settings - check documentation for more info on inputs / outputs expected
    }
    @GetMapping("account/settings/update")
    public String accountSettingsUpdate(){
        return null;
//        End point to update account settings - check documentation for more info on inputs / outputs expected
    }
}
