package fzu.hhj.help2.service;

import com.alibaba.fastjson.JSON;
import fzu.hhj.help2.Util.ServletUtil;
import fzu.hhj.help2.Util.TimeUtil;
import fzu.hhj.help2.Util.WordFilterUtil;
import fzu.hhj.help2.dao.ReplyDAO;
import fzu.hhj.help2.dao.TaskCategoryDAO;
import fzu.hhj.help2.dao.TaskDAO;
import fzu.hhj.help2.dao.UserDAO;
import fzu.hhj.help2.pojo.Reply;
import fzu.hhj.help2.pojo.Task;
import fzu.hhj.help2.pojo.TaskCategory;
import fzu.hhj.help2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static fzu.hhj.help2.Util.ConstantUtil.*;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TaskCategoryDAO taskCategoryDAO;
    @Autowired
    ReplyDAO replyDAO;
    @Autowired
    UserDAO userDAO;

    @Override
    public Map<String, Object> publishTask(String synopsis, String content, Integer categoryId) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User) ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, NO_USER);
            return result;
        }
        if(synopsis == null || content == null || categoryId == null){
            result.put(JSON_RETURN_CODE_NAME, MISS_TASK_INF);
            return result;
        }
        if(WordFilterUtil.replaceWords(synopsis).contains("*") || WordFilterUtil.replaceWords(content).contains("*")){
            result.put(JSON_RETURN_CODE_NAME,  JSON_RESULT_CODE_VERIFY_TEXT_FAIL);
            return result;
        }
        Task task = new Task();
        task.setCategoryId(categoryId);
        task.setContent(content);
        task.setSynopsis(synopsis);
        task.setTime(new Date());
        task.setUserId(user.getId());
        taskDAO.insert(task);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        //TODO 获得徽章
        return result;
    }

    @Override
    public Map<String, Object> getTaskCategory() {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        List<TaskCategory> categories = taskCategoryDAO.selectAll();
        result.put("categories", categories);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return  result;
    }

    @Override
    public Map<String, Object> getTaskById(Integer taskId, Integer sort) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User)ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, NO_USER);
            return result;
        }
        Task task = taskDAO.selectNoDelete(taskId);
        if(task == null){
            result.put(JSON_RETURN_CODE_NAME, JSON_RESULT_CODE_NON_EXISTENT);
            return result;
        }
        if(sort == null){
            sort = REPLY_DEFAULT_SORT;
        }
        increaseViews(task);
        if(user.getId().equals(task.getUserId())){
            result.put("viewerIsTaskPublisher", "1");
        }else {
            result.put("viewerIsTaskPublisher", "0");
        }
        Map<String, Object> taskMap= new HashMap<>(MIN_HASH_MAP_NUM);
        taskMap.put("synopsis", task.getSynopsis());
        taskMap.put("content", task.getContent());
        taskMap.put("views", task.getViews());
        taskMap.put("time", TimeUtil.getTime(task.getTime()));
        taskMap.put("category", taskCategoryDAO.selectById(task.getCategoryId()));
        taskMap.put("isCompleted", task.getIsCompeleted());
        //返回任务发布者的信息
        Map<String, Object> publisherMap = new HashMap<>(MIN_HASH_MAP_NUM);
        User publisher = userDAO.selectUserById(task.getUserId());
        publisherMap.put("id", publisher.getId());
        publisherMap.put("name", publisher.getName());
        publisherMap.put("head", publisher.getHead());
        taskMap.put("publisher", publisherMap);
        List<Reply> replies = replyDAO.listReplyByTaskIdSort(taskId, sort);
        //返回任务的全部回复的信息
        if(replies == null){
            taskMap.put("replyCount", 0);
        }
        else {
            taskMap.put("replyCount", replies.size());
            //将被采纳的回复移动到列表顶部
            List<Reply> newReplies = new ArrayList<>();
            if(replyDAO.getAdoptedReplyByTaskId(taskId) != null){
                newReplies.add(replyDAO.getAdoptedReplyByTaskId(taskId));
            }
            for(int i = 0; i < replies.size(); i++){
                if(replies.get(i).getIsAdopted() != "1"){
                    newReplies.add(replies.get(i));
                }
            }
            taskMap.put("replies", newReplies);
        }
        result.put("task", taskMap);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> getSimpleTaskInf(Integer taskId) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User)ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, NO_USER);
            return result;
        }
        Task task = taskDAO.selectNoDelete(taskId);
        if(task == null){
            result.put(JSON_RETURN_CODE_NAME, JSON_RESULT_CODE_NON_EXISTENT);
            return result;
        }
        Map<String, Object> taskMap= new HashMap<>(MIN_HASH_MAP_NUM);
        taskMap.put("synopsis", task.getSynopsis());
        taskMap.put("content", task.getContent());
        taskMap.put("views", task.getViews());
        taskMap.put("time", TimeUtil.getTime(task.getTime()));
        taskMap.put("category", taskCategoryDAO.selectById(task.getCategoryId()));
        taskMap.put("isCompleted", task.getIsCompeleted());
        result.put("task", taskMap);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }


    @Override
    public Map<String, Object> listTasksByUser() {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User)ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, NO_USER);
            return result;
        }
        List<Task> tasks = taskDAO.ListTaskByUserId(user.getId());
        result.put("tasks", tasks);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> cancelTask(Integer taskId) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User)ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, NO_USER);
            return result;
        }
        Task task = taskDAO.selectNoDelete(taskId);
        if(task == null){
            result.put(JSON_RETURN_CODE_NAME, JSON_RESULT_CODE_NON_EXISTENT);
            return result;
        }
        if(user.getId() != task.getUserId()){
            result.put(JSON_RETURN_CODE_NAME, NO_PERMISSION);
            return result;
        }
        task.setIsDeleted("1");
        taskDAO.updateSelective(task);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> finishTask(Integer taskId) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User)ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, NO_USER);
            return result;
        }
        Task task = taskDAO.selectNoDelete(taskId);
        if(task == null){
            result.put(JSON_RETURN_CODE_NAME, JSON_RESULT_CODE_NON_EXISTENT);
            return result;
        }
        if(user.getId() != task.getUserId() || task.getCategoryId() != 2){
            result.put(JSON_RETURN_CODE_NAME, NO_PERMISSION);
            return result;
        }
        task.setIsCompeleted("1");
        taskDAO.updateSelective(task);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> listTasks() {
        return null;
    }

    private void increaseViews(Task task){
        task.setViews(task.getViews()+1);
        taskDAO.updateSelective(task);
    }
}
