package fzu.hhj.help2.controller;

import fzu.hhj.help2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/loginByNamePassword")
    public Map<String, Object> loginByNamePassword(@RequestParam String name, @RequestParam String password) {
        return userService.loginByNamePassword(name, password);
    }

    @RequestMapping("/loginByEmailPassword")
    public Map<String, Object> loginByEmailPassword(@RequestParam String email, @RequestParam String password){
        return userService.loginByEmailPassword(email, password);
    }

    @RequestMapping("/loginByEmailVerifyCode")
    public Map<String, Object> loginByEmailVerifyCode(@RequestParam String email, @RequestParam String verifyCode){
        return userService.loginByEmailVerifyCode(email, verifyCode);
    }

    @RequestMapping("/logon")
    public Map<String, Object> logon(@RequestParam String email, @RequestParam String verifyCode ,
                                     @RequestParam String username, @RequestParam String password){
        return userService.logon(username,password,email, verifyCode);
    }

    @RequestMapping("/sendEmail")
    public Map<String, Object> sendEmail(@RequestParam String email){
        return userService.sendVerifyCode(email);
    }

    @RequestMapping("/getSimpleUserInf")
    public Map<String, Object> getSimpleUserInf(@RequestParam(required = false ) Integer userId){
        return userService.getSimpleUserInf(userId);
    }

    @RequestMapping("/editUserInf")
    public Map<String, Object> editUserInf(@RequestParam String userName, @RequestParam String gender,
                                           @RequestParam String introduction){
        return userService.editUserInf(userName, gender, introduction);
    }

    @RequestMapping("/resetPassword")
    public Map<String, Object> resetPassword(@RequestParam String verifyCode, @RequestParam String password){
        return userService.resetPassword(verifyCode, password);
    }

    @RequestMapping("/getAllUserInf")
    public Map<String, Object> getAllUserInf(@RequestParam Integer userId){
        return userService.getAllUserInf(userId);
    }

    @RequestMapping("/logout")
    public Map<String, Object> logout(){
        return userService.logout();
    }
}
