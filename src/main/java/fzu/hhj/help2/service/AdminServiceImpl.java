package fzu.hhj.help2.service;

import fzu.hhj.help2.Util.MessageUtil;
import fzu.hhj.help2.dao.AdminDAO;
import fzu.hhj.help2.dao.UserDAO;
import fzu.hhj.help2.pojo.Admin;
import fzu.hhj.help2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fzu.hhj.help2.Util.ConstantUtil.*;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminDAO adminDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    MessageUtil messageUtil;

    @Override
    public Map<String, Object> login(String account, String password) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        Admin admin = adminDAO.selectByAccount(account);
        if(admin == null || !admin.getPasswd().equals(password)){
            result.put(JSON_RETURN_CODE_NAME, ERROR_LOGIN_INF);
            return result;
        }
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> noticeUsers(String content) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        List<User> users = userDAO.selectAllNoDelete();
        for(User user1 : users){
            messageUtil.newMessage(SYSTEM_NOTIFICATION, user1, content);
        }
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }
}
