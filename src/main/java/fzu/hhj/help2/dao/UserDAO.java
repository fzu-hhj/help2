package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.UserMapper;
import fzu.hhj.help2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("name",username);
        return !userMapper.selectByExample(example).isEmpty();
    }

    /**
     * 查询数据库中是否存在此邮箱
     * @param email 邮箱
     * @return 是否存在，true表示存在，false表示不存在
     */
    public boolean hasUserEmail(String email){
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("email", email);
        return !userMapper.selectByExample(example).isEmpty();
    }

    /**
     * 根据用户名查找用户
     * @param name 用户名
     * @return 用户
     */
    public User selectUserByName(String name){
        Example example =new Example(User.class);
        example.createCriteria().andEqualTo("name", name);
        if(userMapper.selectByExample(example).isEmpty()){
            return null;
        }
        if(userMapper.selectByExample(example).size() == 0){
            return null;
        }
        return userMapper.selectByExample(example).get(0);
    }

    /**
     * 根据邮箱查找用户
     * @param email 邮箱
     * @return 用户
     */
    public User selectUserByEmail(String email){
        Example example =new Example(User.class);
        example.createCriteria().andEqualTo("email", email);
        if(userMapper.selectByExample(example).isEmpty()){
            return null;
        }
        if(userMapper.selectByExample(example).size() == 0){
            return null;
        }
        return userMapper.selectByExample(example).get(0);
    }

    /**
     * 根据用户id查询用户
     * @param userId 用户id
     * @return 用户
     */
    public User selectUserById(Integer userId){
        Example example =new Example(User.class);
        example.createCriteria().andEqualTo("id", userId);
        if(userMapper.selectByExample(example).isEmpty()){
            return null;
        }
        return userMapper.selectByExample(example).get(0);
    }

    public void insert(User user){
        userMapper.insertSelective(user);
    }

    public void update(User user){
        userMapper.updateByPrimaryKeySelective(user);
    }

    public List<User> selectAllNoDelete(){
        return userMapper.selectAll();
    }
}
