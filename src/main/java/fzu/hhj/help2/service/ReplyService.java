package fzu.hhj.help2.service;


import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface ReplyService {

    /**
     * 发表回复
     * @param taskId 任务id
     * @param content 回复的内容
     * @return 结果码
     */
    public Map<String, Object> postReply(Integer taskId, String content);

    /**
     * 点赞回复
     * @param replyId 回复id
     * @return 结果码
     */
    public Map<String, Object> supportReply(Integer replyId);

    /**
     * 反对回复
     * @param replyId 回复id
     * @return 结果码
     */
    public Map<String, Object> objectReply(Integer replyId);

    /**
     * 采纳回复
     * @param replyId 回复id
     * @return 结果码
     */
    public Map<String, Object> adoptReply(Integer replyId);

    /**
     * 取消采纳回复
     * @param replyId 回复id
     * @return 结果码
     */
    public Map<String, Object> cancelAdoptReply(Integer replyId);
}
