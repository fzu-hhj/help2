package fzu.hhj.help2.service;

import fzu.hhj.help2.Util.ServletUtil;
import fzu.hhj.help2.dao.ReplyDAO;
import fzu.hhj.help2.dao.ReportDAO;
import fzu.hhj.help2.dao.ReportReasonDAO;
import fzu.hhj.help2.pojo.Report;
import fzu.hhj.help2.pojo.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static fzu.hhj.help2.Util.ConstantUtil.*;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportReasonDAO reportReasonDAO;
    @Autowired
    ReportDAO reportDAO;

    @Override
    public Map<String, Object> listReasons() {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        if(reportReasonDAO.listReasons().size() == 0){
            result.put(JSON_RETURN_CODE_NAME, NO_CONTENT);
            return result;
        }
        result.put("reasons", reportReasonDAO.listReasons());
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> reportUser(Integer reportedUserId, Integer reasonId, String content) {
        return getStringObjectMap(reportedUserId, reasonId, content, REPORT_TO_USER);
    }

    @Override
    public Map<String, Object> reportTask(Integer reportedTaskId, Integer reasonId, String content) {
        return getStringObjectMap(reportedTaskId, reasonId, content, REPORT_TO_TASK);
    }

    @Override
    public Map<String, Object> reportReply(Integer reportedReplyId, Integer reasonId, String content) {
        return getStringObjectMap(reportedReplyId, reasonId, content, REPORT_TO_REPLY);
    }

    @Override
    public Map<String, Object> reportMessage(Integer reportedMessageId, Integer reasonId, String content) {
        return getStringObjectMap(reportedMessageId, reasonId, content, REPORT_TO_MESSAGE);
    }

    @NotNull
    private Map<String, Object> getStringObjectMap(Integer reportedId, Integer reasonId, String content, String reportCategory) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User) ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, NO_USER);
            return result;
        }
        Report report = new Report();
        report.setReporterId(user.getId());
        report.setTime(new Date());
        report.setReasonId(reasonId);
        if(content != null){
            report.setContent(content);
        }
        report.setReportedCategory(reportCategory);
        report.setReportedId(reportedId);
        reportDAO.insert(report);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }

}
