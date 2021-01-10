package fzu.hhj.help2.controller;

import fzu.hhj.help2.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    @Autowired
    ReplyService replyService;

    @RequestMapping("/postReply")
    public Map<String, Object> postReply(@RequestParam Integer taskId, @RequestParam String content){
        return replyService.postReply(taskId, content);
    }
}
