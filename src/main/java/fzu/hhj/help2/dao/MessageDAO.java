package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.MessageMapper;
import fzu.hhj.help2.pojo.Message;
import fzu.hhj.help2.pojo.MessageCategory;
import fzu.hhj.help2.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Repository("messageDAO")
public class MessageDAO {
    @Autowired
    MessageMapper messageMapper;

    /**
     * 获取用户收到的消息
     * @param userId 用户id
     * @return 消息列表
     */
    public List<Message> listReceivedMessage(Integer userId){
        Example example = new Example(Message.class);
        example.createCriteria().andEqualTo("receiverId",userId).andEqualTo("isDeleted","0");
        return messageMapper.selectByExample(example);
    }

    /**
     * 获取用户未读的消息
     * @param userId 用户id
     * @return 消息列表
     */
    public List<Message> listUnreadMessage(Integer userId){
        Example example = new Example(Message.class);
        example.createCriteria().andEqualTo("receiverId",userId).andEqualTo("isRead","0").
                andEqualTo("isDeleted","0");
        return messageMapper.selectByExample(example);
    }

    public void insert(Message message){
        messageMapper.insertSelective(message);
    }

    public Message selectMessageById(Integer messageId){
        Example example = new Example(Message.class);
        example.createCriteria().andEqualTo("id", messageId).andEqualTo("isDeleted","0");
        return messageMapper.selectByExample(example).get(0);
    }

    public void update(Message message){
        messageMapper.updateByPrimaryKeySelective(message);
    }
}
