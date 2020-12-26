package fzu.hhj.help2.controller;


import fzu.hhj.help2.Util.ConstantUtil;
import fzu.hhj.help2.mapper.UserMapper;

import fzu.hhj.help2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
