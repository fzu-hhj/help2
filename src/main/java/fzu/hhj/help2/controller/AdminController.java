package fzu.hhj.help2.controller;

import fzu.hhj.help2.mapper.AdminMapper;
import fzu.hhj.help2.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminController {
    @Autowired
    AdminMapper adminMapper;

    @RequestMapping("/admin/{id}")
    public Admin getAdmin2(@PathVariable("id") Integer id){
        return adminMapper.selectByPrimaryKey(id);

    }


}
