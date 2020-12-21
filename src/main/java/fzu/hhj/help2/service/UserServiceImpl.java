package fzu.hhj.help2.service;

import fzu.hhj.help2.Util.ConstantUtil;
import fzu.hhj.help2.Util.SecurityUtil;
import fzu.hhj.help2.mapper.UserMapper;
import fzu.hhj.help2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public Map<String, Object> loginByPassword(Integer id, String password) {
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null && ( password.equals(user.getPasswd()) ||
                SecurityUtil.md5Compare(password, user.getPasswd()))) {

//            //判断用户是否处于封禁
//            if (user.getUserstateByStateId().getId().equals(userStateUtil.getBanState().getId())) {
//                Date now = new Date();
//                if (user.getLastClosureTime().after(now)) {
//                    result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.JSON_RESULT_CODE_BAN);
//                    return result;
//                } else {
//                    user.setUserstateByStateId(userStateUtil.getStateByName(Userstate.NORMAL));
//                }
//            }
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            result.put("email", user.getEmail());
            result.put("password", user.getPasswd());
            HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            request.getSession().setAttribute("user",user);
        }
        else {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.WRONG_PASSWORD);
        }
        return result;
    }
}
