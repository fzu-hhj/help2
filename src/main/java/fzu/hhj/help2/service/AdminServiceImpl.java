package fzu.hhj.help2.service;

import fzu.hhj.help2.Util.MessageUtil;
import fzu.hhj.help2.Util.TimeUtil;
import fzu.hhj.help2.dao.*;
import fzu.hhj.help2.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Autowired
    ReportDAO reportDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    ReplyDAO replyDAO;
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    ReportReasonDAO reportReasonDAO;

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

    @Override
    public Map<String, Object> listReports() {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        List<Report> reports = reportDAO.selectAllNoHandle();
        //TODO将举报记录分组排序
        result.put("reports", reports);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> getReportInf(Integer reportId) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        Report report = reportDAO.getReportById(reportId);
        if(report == null){
            result.put(JSON_RETURN_CODE_NAME, NO_CONTENT);
            return result;
        }
        Map<String, Object> reportMap = new HashMap<>(MIN_HASH_MAP_NUM);
        reportMap.put("id", report.getId());
        reportMap.put("reason", reportReasonDAO.getReasonContertById(report.getReasonId()));
        reportMap.put("time", report.getTime());
        reportMap.put("reporterId", report.getReporterId());
        reportMap.put("content", report.getContent());
        reportMap.put("category", report.getReportedCategory());
        reportMap.put("isHandled", report.getIsHandled());
        if(report.getReportedCategory().equals(REPORT_TO_USER)){
            reportMap.put("userName", userDAO.selectUserById(report.getReportedId()).getName());
            reportMap.put("userIntroduction", userDAO.selectUserById(report.getReportedId()).getIntroduction());
        }
        else if(report.getReportedCategory().equals(REPORT_TO_TASK)){
            Task task = taskDAO.selectById(report.getReportedId());
            if(task == null){
                result.put(JSON_RETURN_CODE_NAME, NO_CONTENT);
                return result;
            }
            reportMap.put("taskSynopsis", task.getSynopsis());
            reportMap.put("taskContent", task.getContent());
        }
        else if(report.getReportedCategory().equals(REPORT_TO_REPLY)){
            reportMap.put("replyContent", replyDAO.selectById(report.getReportedId()).getContent());
        }
        else if(report.getReportedCategory().equals(REPORT_TO_MESSAGE)){
            reportMap.put("messageContent", messageDAO.selectById(report.getReportedId()).getContent());
        }
        result.put("report", reportMap);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> handleReport(Integer reportId, Integer isSuspend, Integer suspendTime) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        Report report = reportDAO.getReportById(reportId);
        if(report == null){
            result.put(JSON_RETURN_CODE_NAME, NO_CONTENT);
            return result;
        }
        if(isSuspend == 1){
            if(suspendTime == null){
                result.put(JSON_RETURN_CODE_NAME, MISS_SUSPEND_TIME);
                return result;
            }

            Integer userId = 0;
            if(report.getReportedCategory().equals(REPORT_TO_USER)){
                userId = report.getReportedId();
            }
            else if(report.getReportedCategory().equals(REPORT_TO_TASK)){
                Task task = taskDAO.selectById(report.getReportedId());
                if(task != null ){
                    task.setIsDeleted("1");
                    taskDAO.updateSelective(task);
                    userId = task.getUserId();
                }
            }
            else if(report.getReportedCategory().equals(REPORT_TO_REPLY)){
                Reply reply = replyDAO.selectNoDelete(report.getReportedId());
                reply.setIsDeleted("1");
                replyDAO.updateSelective(reply);
                userId = reply.getUserId();
            }
            else{
                Message message = messageDAO.selectNoDelete(report.getReportedId());
                message.setIsDeleted("1");
                messageDAO.update(message);
                userId = message.getSenderId();
            }

            User user = userDAO.selectUserById(userId);
            if(user.getSuspendTime() == null){
                user.setSuspendTime(TimeUtil.addTime(new Date(), suspendTime));
            }
            else {
                user.setSuspendTime(TimeUtil.addTime(user.getSuspendTime(), suspendTime));
            }
            userDAO.update(user);

            String s;
            if(suspendTime < 0){
                s = "您由于发表违规言论，账号被封禁100年。";
            }
            else{
                s = "您由于发表违规言论，账号被封禁" + suspendTime.toString() + "天";
            }
            messageUtil.newMessage(SYSTEM_PENALTY, user, s);
            messageUtil.newMessage(SYSTEM_PENALTY, userDAO.selectUserById(report.getReporterId()), 
                    "您的举报已处理，谢谢您对于和谐环境的维护");
        }
        Report handledReport = new Report();
        handledReport.setIsHandled("1");
        reportDAO.updateByCategoryAndReportedId(report.getReportedCategory(), report.getReportedId(), handledReport);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }
}
