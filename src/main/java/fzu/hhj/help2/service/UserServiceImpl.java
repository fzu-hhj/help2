package fzu.hhj.help2.service;

import fzu.hhj.help2.Util.ConstantUtil;
import fzu.hhj.help2.Util.MailUtil;
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
    @Autowired
    MailUtil mailUtil;

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

    @Override
    public Map<String, String> sendVerifyCode(String email) {
        Map<String,String> map = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        String subject = "Help2验证邮件";
        String verifyCode = SecurityUtil.generatorVerifyCode(6);
        map.put("verifyCode",verifyCode);
        String content = "您的验证码是<h1>" +
                verifyCode +
                "</h1>请在15分钟内完成验证";
        String result;
        try {
            mailUtil.sendMail(email,subject,content);
        } catch (Exception e) {
            result = "{\"head\":\"发送失败\",\"body\":\"服务器异常\"}";
            map.put("result",result);
            e.printStackTrace();
            return map;
        }
        result = "{\"head\":\"发送成功\",\"body\":\"请进入邮箱查看验证码\"}";
        map.put("result",result);
        return map;
    }
}
