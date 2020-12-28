package fzu.hhj.help2.Util;


import fzu.hhj.help2.dao.MessageDAO;
import fzu.hhj.help2.pojo.Message;
import fzu.hhj.help2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author erniumo
 */
@Component
public class MessageUtil {
    @Autowired
    private MessageDAO messageDAO;

    /**
     * 系统调用发新建消息
     * @param category 消息类型
     * @param user  用户信息
     * @param content 消息内容
     */
    public void newMessage(Integer category, User user, String content){
        Message message = new Message();
        message.setIsRead("0");
        message.setContent(content);
        message.setTime(new Date());
        message.setReceiverId(user.getId());
        message.setCategory(category);
        messageDAO.insert(message);
    }
}
