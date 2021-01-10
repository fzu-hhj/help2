package fzu.hhj.help2.service;

import fzu.hhj.help2.Util.ServletUtil;
import fzu.hhj.help2.dao.ReplyDAO;
import fzu.hhj.help2.pojo.Reply;
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
}
