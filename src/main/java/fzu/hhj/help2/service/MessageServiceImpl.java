package fzu.hhj.help2.service;

import fzu.hhj.help2.Util.ServletUtil;
import fzu.hhj.help2.dao.MessageDAO;
import fzu.hhj.help2.dao.UserDAO;
import fzu.hhj.help2.pojo.Message;
import fzu.hhj.help2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import static fzu.hhj.help2.Util.ConstantUtil.*;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    UserDAO userDAO;

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

    @Override
    public Map<String, Object> getMessage(Integer messageId) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User) ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, LOGIN_NO_ELIGIBLE_USER);
            return result;
        }
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        Message message = messageDAO.selectMessageById(messageId);
        Map<String, Object> messageInf = new HashMap<>(MIN_HASH_MAP_NUM);
        messageInf.put("id", message.getId());
        messageInf.put("category", message.getCategory());
        messageInf.put("content", message.getContent());
        if(message.getCategory() == 0){
            messageInf.put("senderId", userDAO.selectUserById(message.getSenderId()).getId());
            messageInf.put("senderName", userDAO.selectUserById(message.getSenderId()).getName());
        }
        messageInf.put("time", message.getTime());
        result.put("message", messageInf);
        return result;
    }

    @Override
    public Map<String, Object> listMessages() {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User) ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, LOGIN_NO_ELIGIBLE_USER);
            return result;
        }
        Integer userId = user.getId();
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        List<Message> messages = messageDAO.listReceivedMessage(userId);
        List<Map<String, Object>> messageMaps = new ArrayList<>();
        for(Message message1 : messages){
            Map<String, Object> messageMap = new HashMap<>(MIN_HASH_MAP_NUM);
            messageMap.put("id", message1.getId());
            messageMap.put("category", message1.getCategory());
            messageMap.put("content", message1.getContent());
            if(message1.getCategory() == 0){
                messageMap.put("senderId", userDAO.selectUserById(message1.getSenderId()).getId());
                messageMap.put("senderName", userDAO.selectUserById(message1.getSenderId()).getName());
            }
            messageMap.put("time", message1.getTime());
            messageMaps.add(messageMap);
        }
        result.put("messages", messages);
        return result;
    }
}
