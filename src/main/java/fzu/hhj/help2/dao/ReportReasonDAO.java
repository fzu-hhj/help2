package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.ReportReasonMapper;
import fzu.hhj.help2.pojo.ReportReason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reportReasonDAO")
public class ReportReasonDAO {

    @Autowired
    ReportReasonMapper reportReasonMapper;

    public List<ReportReason> listReasons(){
        return reportReasonMapper.selectAll();
    }

}
