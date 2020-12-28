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

    /**
     * 使用用户名密码登录
     * @param name 用户名
     * @param password 密码
     * @return 结果集
     */
    public Map<String, Object> loginByNamePassword(String name, String password);

    /**
     * 使用邮箱密码登录
     * @param email 邮箱
     * @param password 密码
     * @return 结果集
     */
    public Map<String, Object> loginByEmailPassword(String email, String password);

    /**
     * 使用邮箱验证码登录
     * @param email 邮箱
     * @param verifyCode 验证码
     * @return 结果集
     */
    public Map<String, Object> loginByEmailVerifyCode(String email, String verifyCode);

    /**
     * 根据用户id获取用户的信息
     * @param userId 用户id
     * @return 用户信息
     */
    public Map<String, Object> getUserInf(Integer userId);
}