package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.AdminMapper;
import fzu.hhj.help2.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

@Repository("adminDAO")
public class AdminDAO {
    @Autowired
    AdminMapper adminMapper;

    /**
     * 根据账户名获取管理员
     * @param account 账户名
     * @return 管理员
     */
    public Admin selectByAccount(String account){
        Example example = new Example(Admin.class);
        example.createCriteria().andEqualTo("account", account);
        if(adminMapper.selectCountByExample(example) == 0){
            return null;
        }
        return adminMapper.selectByExample(example).get(0);
    }
}
