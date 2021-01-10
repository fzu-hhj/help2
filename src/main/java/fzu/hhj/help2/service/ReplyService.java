package fzu.hhj.help2.service;

import java.util.Map;

public interface ReplyService {

    /**
     * 发表回复
     * @param taskId 任务id
     * @param content 回复的内容
     * @return 结果码
     */
    public Map<String, Object> postReply(Integer taskId, String content);

}
