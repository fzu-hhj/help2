package fzu.hhj.help2.service;

import fzu.hhj.help2.Util.ServletUtil;
import fzu.hhj.help2.dao.ReplyDAO;
import fzu.hhj.help2.dao.TaskDAO;
import fzu.hhj.help2.pojo.Reply;
import fzu.hhj.help2.pojo.Task;
import fzu.hhj.help2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static fzu.hhj.help2.Util.ConstantUtil.*;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    ReplyDAO replyDAO;
    @Autowired
    TaskDAO taskDAO;

    @Override
    public Map<String, Object> postReply(Integer taskId, String content) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User) ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, NO_USER);
            return result;
        }
        if(content == ""){
            result.put(JSON_RETURN_CODE_NAME, EMPTY_STRING);
            return result;
        }
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setTime(new Date());
        reply.setTaskId(taskId);
        reply.setUserId(user.getId());
        replyDAO.insert(reply);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> supportReply(Integer replyId) {
        return TakeAStandOnReply(replyId, SUPPORT_REPLY);
    }

    @Override
    public Map<String, Object> objectReply(Integer replyId) {
        return TakeAStandOnReply(replyId, OBJECT_REPLY);
    }

    private Map<String, Object> TakeAStandOnReply(Integer replyId, boolean attitude){
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User) ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, NO_USER);
            return result;
        }
        Reply reply = replyDAO.selectNoDelete(replyId);
        if(reply == null ){
            result.put(JSON_RETURN_CODE_NAME, NOT_EXIST_REPLY);
            return result;
        }
        //attitude == true表示支持
        if(attitude){
            reply.setLikes(reply.getLikes() + 1);
        }
        else {
            reply.setObjections(reply.getObjections() + 1);
        }
        replyDAO.updateSelective(reply);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> adoptReply(Integer replyId) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User) ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, NO_USER);
            return result;
        }
        Reply reply = replyDAO.selectNoDelete(replyId);
        if(reply == null ){
            result.put(JSON_RETURN_CODE_NAME, NOT_EXIST_REPLY);
            return result;
        }
        Task task = taskDAO.selectNoDelete(reply.getTaskId());
        if(user.getId() != task.getUserId()){
            result.put(JSON_RETURN_CODE_NAME, NO_PERMISSION);
            return result;
        }
        if(task.getIsCompeleted().equals("1")){
            result.put(JSON_RETURN_CODE_NAME, TASK_HAS_ADOPTED);
            return result;
        }
        task.setIsCompeleted("1");
        taskDAO.updateSelective(task);
        reply.setIsAdopted("1");
        replyDAO.updateSelective(reply);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> cancelAdoptReply(Integer replyId) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User) ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, NO_USER);
            return result;
        }
        Reply reply = replyDAO.selectAdoptedReplyById(replyId);
        if(reply == null ){
            result.put(JSON_RETURN_CODE_NAME, NOT_EXIST_REPLY);
            return result;
        }
        Task task = taskDAO.selectNoDelete(reply.getTaskId());
        if(user.getId() != task.getUserId()){
            result.put(JSON_RETURN_CODE_NAME, NO_PERMISSION);
            return result;
        }
        task.setIsCompeleted("0");
        taskDAO.updateSelective(task);
        reply.setIsAdopted("0");
        replyDAO.updateSelective(reply);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }
}
