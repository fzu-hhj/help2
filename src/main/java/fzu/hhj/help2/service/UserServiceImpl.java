package fzu.hhj.help2.service;

import fzu.hhj.help2.Util.MailUtil;
import fzu.hhj.help2.Util.SecurityUtil;
import fzu.hhj.help2.Util.ServletUtil;
import fzu.hhj.help2.dao.UserDAO;
import fzu.hhj.help2.mapper.UserMapper;
import fzu.hhj.help2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static fzu.hhj.help2.Util.ConstantUtil.*;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserDAO userDAO;
    @Autowired
    MailUtil mailUtil;


    @Override
    public Map<String, Object> loginByPassword(Integer id, String password) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = userMapper.selectByPrimaryKey(id);
        return verifyPassword(password, result, user);
    }

    @Override
    public Map<String, Object> sendVerifyCode(String email) {
        Map<String,Object> resultMap = new HashMap<>(HASH_MAP_NUM);
        String subject = "Help2验证邮件";
        String verifyCode = SecurityUtil.generatorVerifyCode(6);
        resultMap.put("verifyCode",verifyCode);
        String content = "您的验证码是<h1>" +
                verifyCode +
                "</h1>请在15分钟内完成验证";
        String result;
        try {
            mailUtil.sendMail(email,subject,content);
        } catch (Exception e) {
            result = "{\"head\":\"发送失败\",\"body\":\"服务器异常\"}";
            resultMap.put("result",result);
            e.printStackTrace();
            return resultMap;
        }
        result = "{\"head\":\"发送成功\",\"body\":\"请进入邮箱查看验证码\"}";
        resultMap.put("result",result);
        return resultMap;
    }

    @Override
    public Map<String, Object> logon(String username, String password, String email, String verifyCode) {
        Map<String,Object> resultMap = new HashMap<>(MIN_HASH_MAP_NUM);

        HttpSession session = ServletUtil.getRequest().getSession();

        String sessionVerifyCode = (String) session.getAttribute(VERIFY_CODE);
        String sessionEmail = (String)session.getAttribute(EMAIL);
        session.setAttribute(VERIFY_CODE, null);
        session.setAttribute(EMAIL, null);

        if (!verifyCode.equals(sessionVerifyCode)) {
            resultMap.put("response", LOGON_WRONG_VERIFY_CODE);
            return resultMap;
        } else if (!email.equals(sessionEmail)) {
            resultMap.put("response", LOGON_WRONG_EMAIL);
            return resultMap;
        }


        User user = new User();
        if (userDAO.hasUserName(username)) {
            resultMap.put("response", LOGON_EXIST_USERNAME);
        }
        if (userDAO.hasUserEmail(email)) {
            resultMap.put("response",LOGON_EXIST_EMAIL);
            return resultMap;
        }
        user.setName(username);
        //使用md5加密
        user.setPasswd(SecurityUtil.md5(password));
        user.setEmail(email);
        /*默认信息*/
        //注册时间为当前系统时间
//        user.setDate( new Timestamp(System.currentTimeMillis()));
        //初始徽章为0
//        user.setBadgeNum(0);
        //初始经验为1
        user.setExp(1);
        //初始性别为保密
        user.setGender("未知");
        //初始用户无身份认证信息
//        user.setIsAttest((byte) 0);
//        Userstate userstate = new Userstate();
//        userstate.setId(1);
        //初始用户状态为正常
//        user.setUserstateByStateId(userstate);
        //初始头像
        user.setHead("head.jpg");
//        user.setReportedTimes(0);
        userMapper.insertSelective(user);

        resultMap.put("response", SUCCESS);
        session.setAttribute(LOGIN_USER_SESSION_NAME,user);

        return resultMap;
    }

    @Override
    public Map<String, Object> loginByNamePassword(String name, String password) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = userDAO.selectUserByName(name);
        return verifyPassword(password, result, user);
    }

    private Map<String, Object> verifyPassword(String password, Map<String, Object> result, User user) {
        if (user != null && ( password.equals(user.getPasswd()) ||
                SecurityUtil.md5Compare(password, user.getPasswd()))) {



//            if (user.getUserstateByStateId().getId().equals(userStateUtil.getBanState().getId())) {
//                Date now = new Date();
//                if (user.getLastClosureTime().after(now)) {
//                    result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.JSON_RESULT_CODE_BAN);
//                    return result;
//                } else {
//                    user.setUserstateByStateId(userStateUtil.getStateByName(Userstate.NORMAL));
//                }
//            }
            //判断用户是否处于封禁
            if(user.getIsSuspend().equals("1")){
                result.put(JSON_RETURN_CODE_NAME, JSON_RESULT_CODE_BAN);
                    return result;
            }

            result.put(JSON_RETURN_CODE_NAME, SUCCESS);
            result.put("email", user.getEmail());
            result.put("password", user.getPasswd());
            HttpServletRequest request= ServletUtil.getRequest();
            request.getSession().setAttribute("user",user);
        }
        else {
            result.put(JSON_RETURN_CODE_NAME,WRONG_PASSWORD);
        }
        return result;
    }
}
