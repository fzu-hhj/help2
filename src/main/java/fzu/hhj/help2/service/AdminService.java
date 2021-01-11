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

    public Map<String, Object> noticeUsers(String content);
}
