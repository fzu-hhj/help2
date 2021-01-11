package fzu.hhj.help2.service;

import com.alibaba.fastjson.JSON;
import fzu.hhj.help2.Util.MessageUtil;
import fzu.hhj.help2.Util.TimeUtil;
import fzu.hhj.help2.dao.*;
import fzu.hhj.help2.pojo.*;
import org.omg.CORBA.OBJ_ADAPTER;
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
        if(report.getReportedCategory().equals(REPORT_TO_USER)){
            reportMap.put("userName", userDAO.selectUserById(report.getReportedId()).getName());
            reportMap.put("userIntroduction", userDAO.selectUserById(report.getReportedId()).getIntroduction());
        }
        else if(report.getReportedCategory().equals(REPORT_TO_TASK)){
            reportMap.put("taskSynopsis", taskDAO.selectNoDelete(report.getReportedId()).getSynopsis());
            reportMap.put("taskContent", taskDAO.selectNoDelete(report.getReportedId()).getContent());
        }
        else if(report.getReportedCategory().equals(REPORT_TO_REPLY)){
            reportMap.put("replyContent", replyDAO.selectById(report.getReportedId()).getContent());
        }
        else if(report.getReportedCategory().equals(REPORT_TO_MESSAGE)){
            reportMap.put("messageContent", messageDAO.selectMessageById(report.getReportedId()).getContent());
        }
        result.put("report", reportMap);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return null;
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
            if(report.getReportedCategory().equals(REPORT_TO_TASK)){
                Task task = taskDAO.selectNoDelete(report.getReportedId());
                task.setIsDeleted("1");
                taskDAO.updateSelective(task);
            }
            else if(report.getReportedCategory().equals(REPORT_TO_REPLY)){
                Reply reply = replyDAO.selectById(report.getReportedId());
                reply.setIsDeleted("1");
                replyDAO.updateSelective(reply);
            }
            else if(report.getReportedCategory().equals(REPORT_TO_MESSAGE)){
                Message message = messageDAO.selectMessageById(report.getReportedId());
                message.setIsDeleted("1");
                messageDAO.update(message);
            }
            User user = userDAO.selectUserById(report.getReportedId());
            user.setSuspendTime(TimeUtil.addTime(user.getSuspendTime(), suspendTime));
            String s;
            if(suspendTime < 0){
                s = "您由于发表违规言论，账号被封禁100年。";
            }
            else{
                s = "您由于发表违规言论，账号被封禁" + suspendTime.toString() + "天";
            }
            messageUtil.newMessage(SYSTEM_PENALTY, user, s);
        }
        report.setIsHandled("1");
        reportDAO.updateSelective(report);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }
}
