package fzu.hhj.help2.controller;

import fzu.hhj.help2.Util.ServletUtil;
import fzu.hhj.help2.pojo.User;
import fzu.hhj.help2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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

    @RequestMapping("/getUserInf")
    public Map<String, Object> getUserInf(@RequestParam(required = false ) Integer userId){
        if(userId == null){
            userId = ((User)ServletUtil.getRequest().getSession().getAttribute("user")).getId();
        }
        return userService.getUserInf(userId);
    }
}
