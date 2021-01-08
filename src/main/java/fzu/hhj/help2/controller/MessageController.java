package fzu.hhj.help2.controller;

import fzu.hhj.help2.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @RequestMapping("/sendMessage")
    public Map<String, Object> sendEmail(@RequestParam Integer receiverId, @RequestParam String content){
        return messageService.sendMessage(receiverId, content);
    }

    @RequestMapping("/getMessage")
    public  Map<String, Object> getMessage(@RequestParam Integer messageId){
        return messageService.getMessage(messageId);
    }

    @RequestMapping("/listMessages")
    public Map<String, Object> listMessages(){
        return messageService.listMessages();
    }
}
