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
}
