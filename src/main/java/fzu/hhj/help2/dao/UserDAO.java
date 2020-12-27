package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.UserMapper;
import fzu.hhj.help2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userDAO")
public class UserDAO {
    @Autowired
    UserMapper userMapper;

    /**
     * 查询数据库是否存在此用户名
     * @param username 用户名
     * @return 是否存在，true表示存在，false表示不存在
     */
    public boolean hasUserName(String username){
        User user = new User();
        user.setName(username);
        if(userMapper.select(user).isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * 查询数据库中是否存在此邮箱
     * @param email 邮箱
     * @return 是否存在，true表示存在，false表示不存在
     */
    public boolean hasUserEmail(String email){
        User user = new User();
        user.setEmail(email);
        if(userMapper.select(user).isEmpty()){
            return false;
        }
        return true;
    }
}
