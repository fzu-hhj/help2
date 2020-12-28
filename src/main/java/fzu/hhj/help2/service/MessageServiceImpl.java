package fzu.hhj.help2.service;

import fzu.hhj.help2.Util.ServletUtil;
import fzu.hhj.help2.dao.MessageDAO;
import fzu.hhj.help2.pojo.Message;
import fzu.hhj.help2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static fzu.hhj.help2.Util.ConstantUtil.*;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDAO messageDAO;

    @Override
    public Map<String, Object> sendMessage(Integer receiverId, String content) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User) ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, LOGIN_NO_ELIGIBLE_USER);
            return result;
        }
        Message message = new Message();
        message.setReceiverId(receiverId);
        message.setCategory(USER2USER);
        message.setSenderId(user.getId());
        message.setContent(content);
        message.setTime(new Date());
        messageDAO.insert(message);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }
}
