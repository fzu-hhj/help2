package fzu.hhj.help2.service;

import java.util.Map;

public interface ReportService {

    /**
     * 获取举报原因内容
     * @return 结果码和举报原因
     */
    public Map<String, Object> listReasons();

    /**
     * 举报用户
     * @param reportedUserId 被举报的用户id
     * @param reasonId 举报原因id
     * @param content 举报内容
     * @return 结果码
     */
    public Map<String, Object> reportUser(Integer reportedUserId, Integer reasonId, String content);

    /**
     * 举报任务
     * @param reportedTaskId 被举报的任务id
     * @param reasonId 举报原因id
     * @param content 举报内容
     * @return 结果码
     */
    public Map<String, Object> reportTask(Integer reportedTaskId, Integer reasonId, String content);

    /**
     * 举报回复
     * @param reportedReplyId 被举报的回复id
     * @param reasonId 举报原因id
     * @param content 举报内容
     * @return 结果码
     */
    public Map<String, Object> reportReply(Integer reportedReplyId, Integer reasonId, String content);

    /**
     * 举报消息
     * @param reportedMessageId 被举报的消息id
     * @param reasonId 举报原因id
     * @param content 举报内容
     * @return 结果码
     */
    public Map<String, Object> reportMessage(Integer reportedMessageId, Integer reasonId, String content);

}
