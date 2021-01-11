package fzu.hhj.help2.service;

import java.util.Map;

public interface MessageService {

    /**
     * 用户向另一个用户发送消息
     * @param receiverId 接收者的用户id
     * @param content 消息内容
     * @return 结果集
     */
    public Map<String, Object> sendMessage(Integer receiverId,String content);

    /**
     * 获取消息信息
     * @param messageId 消息id
     * @return 消息信息
     */
    public Map<String, Object> getMessage(Integer messageId);

    public Map<String, Object> readMessage(Integer messageId);

    public Map<String, Object> listMessages();
}
