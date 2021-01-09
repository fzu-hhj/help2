package fzu.hhj.help2.controller;

import fzu.hhj.help2.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @RequestMapping("publishTask")
    public Map<String, Object> publishTask(@RequestParam String synopsis, @RequestParam String content,
                                           @RequestParam Integer categoryId){
        return taskService.publishTask(synopsis, content, categoryId);
    }

    @RequestMapping("/getCategories")
    public Map<String, Object> getCategories(){
        return taskService.getTaskCategory();
    }

    @RequestMapping("/viewTask")
    public Map<String, Object> getTask(@RequestParam Integer taskId, @RequestParam(required = false) Integer sort){
        return taskService.getTaskById(taskId, sort);
    }
}
