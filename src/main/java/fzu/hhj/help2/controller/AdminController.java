package fzu.hhj.help2.controller;

import fzu.hhj.help2.mapper.AdminMapper;
import fzu.hhj.help2.pojo.Admin;
import fzu.hhj.help2.service.AdminService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    public Map<String, Object> login(@RequestParam String account, @RequestParam String password){
        return adminService.login(account, password);
    }

    @RequestMapping("/noticeUsers")
    public Map<String, Object> noticeUsers(String content){
        return adminService.noticeUsers(content);
    }

    @RequestMapping("/listReports")
    public Map<String, Object> listReports(){
        return adminService.listReports();
    }

    @RequestMapping("/getReportInf")
    public Map<String, Object> getReportInf(@RequestParam Integer reportId){
        return adminService.getReportInf(reportId);
    }

    @RequestMapping("/handleReport")
    public Map<String, Object> handleReport(@RequestParam Integer reportId, @RequestParam Integer isSuspend,
                                            @RequestParam(required = false) Integer suspendTime){
        return adminService.handleReport(reportId, isSuspend, suspendTime);
    }

}
