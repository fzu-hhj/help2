package fzu.hhj.help2.controller;


import fzu.hhj.help2.Util.ConstantUtil;
import fzu.hhj.help2.Util.ServletUtil;
import fzu.hhj.help2.mapper.UserMapper;

import fzu.hhj.help2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/loginByPassword")
    public Map<String, Object> loginByPassword(@RequestParam Integer id, @RequestParam String password) {
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        result = userService.loginByPassword(id, password);
        return result;
    }

    public Map<String, Object> logon(@RequestParam String email, @RequestParam String verfy){
        return null;
    }

    @RequestMapping("/sendEmail")
    public Map<String, Object> sendEmail(@RequestParam String email){
        HttpSession session = ServletUtil.getRequest().getSession();
        Map<String, Object> resultMap = userService.sendVerifyCode(email);
        session.setAttribute(ConstantUtil.EMAIL, email);
        session.setAttribute(ConstantUtil.VERIFY_CODE, resultMap.get(ConstantUtil.VERIFY_CODE));
        return resultMap;
    }
}
