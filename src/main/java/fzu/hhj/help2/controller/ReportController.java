package fzu.hhj.help2.controller;

import fzu.hhj.help2.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @RequestMapping("/listReasons")
    public Map<String, Object> listReasons(){
        return reportService.listReasons();
    }

    @RequestMapping("/reportUser")
    public Map<String, Object> reportUser(@RequestParam Integer reportedUserId, @RequestParam Integer reasonId,
                                          @RequestParam(required = false) String content){
        return reportService.reportUser(reportedUserId, reasonId, content);
    }

    @RequestMapping("/reportTask")
    public Map<String, Object> reportTask(@RequestParam Integer reportedTaskId, @RequestParam Integer reasonId,
                                          @RequestParam(required = false) String content){
        return reportService.reportTask(reportedTaskId, reasonId, content);
    }

    @RequestMapping("/reportReply")
    public Map<String, Object> reportReply(@RequestParam Integer reportedReplyId, @RequestParam Integer reasonId,
                                          @RequestParam(required = false) String content){
        return reportService.reportReply(reportedReplyId, reasonId, content);
    }

    @RequestMapping("/reportMessage")
    public Map<String, Object> reportMessage(@RequestParam Integer reportedMessageId, @RequestParam Integer reasonId,
                                          @RequestParam(required = false) String content){
        return reportService.reportMessage(reportedMessageId, reasonId, content);
    }
}
