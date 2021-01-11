package fzu.hhj.help2.service;

import java.util.Map;

public interface AdminService {

    /**
     * 管理员登录
     * @param account 账户
     * @param password 密码
     * @return 结果码
     */
    public Map<String, Object> login(String account, String password);

    /**
     * 通知用户
     * @param content 通知内容
     * @return 结果码
     */
    public Map<String, Object> noticeUsers(String content);

    public Map<String, Object> listReports();

    public Map<String, Object> getReportInf(Integer reportId);

    /**
     * 处理举报
     * @param reportId 举报记录id
     * @param isSuspend 是否封禁 “1”表示封禁，“0”表示不封禁
     * @param suspendTime 封禁时间 “1”表示24h，“7”表示7d，“30”表示30d，“-1”表示永久
     * @return 结果码
     */
    public Map<String, Object> handleReport(Integer reportId, Integer isSuspend, Integer suspendTime);
}
