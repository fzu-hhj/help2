package fzu.hhj.help2.service;

import fzu.hhj.help2.Util.*;
import fzu.hhj.help2.dao.*;
import fzu.hhj.help2.pojo.Message;
import fzu.hhj.help2.pojo.User;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fzu.hhj.help2.Util.ConstantUtil.*;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    ReplyDAO replyDAO;
    @Autowired
    BadgeGetDAO badgeGetDAO;
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    MailUtil mailUtil;


    @Override
    public Map<String, Object> loginByPassword(Integer id, String password) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = userDAO.selectUserById(id);
        return verifyPassword(password, result, user);
    }

    @Override
    public Map<String, Object> sendVerifyCode(String email) {
        Map<String, Object> result = new HashMap<>(HASH_MAP_NUM);
        String subject = "Help2验证邮件";
        String verifyCode = SecurityUtil.generatorVerifyCode(6);
        result.put("verifyCode", verifyCode);
        String content = "您的验证码是<h1>" +
                verifyCode +
                "</h1>请在15分钟内完成验证";
        String resultString;
        try {
            mailUtil.sendMail(email, subject, content);
        } catch (Exception e) {
            resultString = "{\"head\":\"发送失败\",\"body\":\"服务器异常\"}";
            result.put("result", resultString);
            e.printStackTrace();
            return result;
        }
        resultString = "{\"head\":\"发送成功\",\"body\":\"请进入邮箱查看验证码\"}";
        HttpSession session = ServletUtil.getRequest().getSession();
        session.setAttribute(EMAIL, email);
        session.setAttribute(VERIFY_CODE, verifyCode);
        result.put("result", resultString);
        return result;
    }

    @Override
    public Map<String, Object> logon(String username, String password, String email, @NotNull String verifyCode) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);

        HttpSession session = ServletUtil.getRequest().getSession();
        String sessionVerifyCode = (String) session.getAttribute(VERIFY_CODE);
        String sessionEmail = (String) session.getAttribute(EMAIL);
        session.setAttribute(VERIFY_CODE, null);
        session.setAttribute(EMAIL, null);

        if (!verifyCode.equals(sessionVerifyCode)) {
            result.put(JSON_RETURN_CODE_NAME, LOGON_WRONG_VERIFY_CODE);
            return result;
        } else if (!email.equals(sessionEmail)) {
            result.put(JSON_RETURN_CODE_NAME, LOGON_WRONG_EMAIL);
            return result;
        }
        User user = new User();
        if (userDAO.hasUserName(username)) {
            result.put(JSON_RETURN_CODE_NAME, LOGON_EXIST_USERNAME);
        }
        if (userDAO.hasUserEmail(email)) {
            result.put(JSON_RETURN_CODE_NAME, LOGON_EXIST_EMAIL);
            return result;
        }
        user.setName(username);
        //使用md5加密
        user.setPasswd(SecurityUtil.md5(password));
        user.setEmail(email);
        /*默认信息*/
        //注册时间为当前系统时间
//        user.setDate( new Timestamp(System.currentTimeMillis()));

        user.setExp(1);
        //初始性别为保密
        user.setGender("未知");
        user.setHead("head1.jpg");
//        user.setReportedTimes(0);
        userDAO.insert(user);

        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        ServletUtil.getRequest().getSession().setAttribute(LOGIN_USER_SESSION_NAME, user);

        return result;
    }

    @Override
    public Map<String, Object> loginByNamePassword(String name, String password) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = userDAO.selectUserByName(name);
        return verifyPassword(password, result, user);
    }

    @Override
    public Map<String, Object> loginByEmailPassword(String email, String password) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = userDAO.selectUserByEmail(email);
        return verifyPassword(password, result, user);
    }

    @Override
    public Map<String, Object> loginByEmailVerifyCode(@NotNull String email, String verifyCode) {
        HttpSession session = ServletUtil.getRequest().getSession();
        String sessionEmail = (String)session.getAttribute(EMAIL);
        String sessionVerifyCode = (String)session.getAttribute(VERIFY_CODE);

        session.setAttribute(VERIFY_CODE, null);

        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        //Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        if (!email.equals(sessionEmail)) {
            result.put(JSON_RETURN_CODE_NAME,LOGIN_WRONG_EMAIL);
        } else if (!verifyCode.equals(sessionVerifyCode)) {
            result.put(JSON_RETURN_CODE_NAME,LOGIN_WRONG_VERIFY_CODE);
        } else {
            User user = userDAO.selectUserByEmail(email);
            if (user == null) {
                result.put(JSON_RETURN_CODE_NAME, LOGIN_NO_ELIGIBLE_USER);
            } else {
                //判断用户是否处于封禁
                if (user.getIsSuspend().equals("1")) {
                result.put(JSON_RETURN_CODE_NAME, JSON_RESULT_CODE_BAN);
                return result;
            }
                result.put(JSON_RETURN_CODE_NAME, 0);
                session.setAttribute("user", user);
                result.put("email", user.getEmail());
                result.put("password", user.getPasswd());
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> getUserInf(Integer userId) {
        if(userId == null){
            userId = ((User)ServletUtil.getRequest().getSession().getAttribute("user")).getId();
        }
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = userDAO.selectUserById(userId);
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, NO_USER);
        }
        else{
            result.put(JSON_RETURN_CODE_NAME, SUCCESS);
            Map<String, Object> userInf = new HashMap<>(MIN_HASH_MAP_NUM);
            userInf.put("id", user.getId());
            userInf.put("name", user.getName());
            userInf.put("head", ImgUtil.changeAvatar(user.getHead()));
            userInf.put("email", user.getEmail());
            userInf.put("gender", user.getGender());
            userInf.put("introduction", user.getIntroduction());
            userInf.put("isVerify", user.getIsVerify());
            if(user.getIsVerify().equals("1")){
                userInf.put("realName", user.getRealname());
                userInf.put("college", user.getCollege());
                userInf.put("classNum", user.getClassNum());
            }
            //TODO 添加等级表和获取经验的工具类
            userInf.put("level", user.getLevel());
            userInf.put("taskNum", taskDAO.getNumOfUserPublishTask(userId));
            userInf.put("replyNum", replyDAO.getNumOfUserSendReply(userId));
            userInf.put("badgeNum", badgeGetDAO.getNumOfUserGetBadge(userId));
            List<Message> messages = messageDAO.listUnreadMessage(userId);
            if(messages.isEmpty()){
                userInf.put("hasNotReadMsg",0);
            }
            else{
                userInf.put("hasNotReadMsg",1);
            }
            result.put("userInf", userInf);
        }
        return result;
    }

    @Override
    public Map<String, Object> editUserInf(String userName, String gender, String introduction) {
        Map<String,Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        User user = (User)ServletUtil.getRequest().getSession().getAttribute("user");
        if (user == null) {
            //未登录和用户名被占用是一样的返回码
            result.put(JSON_RETURN_CODE_NAME, UN_LOGIN);
            return result;
        }
        if (!userName.equals(user.getName()) && userDAO.hasUserName(userName)) {
            result.put(JSON_RETURN_CODE_NAME, 1);
            return result;
        }
        if (TextVerifyUtil.verifyCompliance(userName) || TextVerifyUtil.verifyCompliance(introduction)) {
            result.put(JSON_RETURN_CODE_NAME, JSON_RESULT_CODE_VERIFY_TEXT_FAIL);
            return result;
        }
        user.setGender(gender);
        user.setName(userName);
        user.setIntroduction(introduction);
        userDAO.update(user);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }

    @NotNull
    @Contract("_, _, _ -> param2")
    private Map<String, Object> verifyPassword(String password, Map<String, Object> result, User user) {
        if (user != null && (password.equals(user.getPasswd()) ||
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
            if (user.getIsSuspend().equals("1")) {
                result.put(JSON_RETURN_CODE_NAME, JSON_RESULT_CODE_BAN);
                return result;
            }

            result.put(JSON_RETURN_CODE_NAME, SUCCESS);
            result.put("email", user.getEmail());
            result.put("password", user.getPasswd());
            HttpServletRequest request = ServletUtil.getRequest();
            request.getSession().setAttribute("user", user);
        } else {
            result.put(JSON_RETURN_CODE_NAME, WRONG_PASSWORD);
        }
        return result;
    }
}

