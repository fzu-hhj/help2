package fzu.hhj.help2.service;

import java.util.Map;

public interface UserService {

    Map<String, Object> loginByPassword(Integer id, String password);

    /**
     *发送验证码到邮箱
     * @param email 邮箱
     * @return 结果集合
     */
    public Map<String,Object> sendVerifyCode(String email);

    /**
     * 用户注册
     * @param username 用户名
     * @param password 用户密码
     * @param email 注册用邮箱
     * @param verifyCode 用户输入的验证码
     * @return 结果集
     */
    public Map<String,Object> logon(String username, String password, String email, String verifyCode);
}
