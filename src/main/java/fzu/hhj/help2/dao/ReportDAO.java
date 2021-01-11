package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.ReportMapper;
import fzu.hhj.help2.pojo.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("reportDAO")
public class ReportDAO {
    @Autowired
    ReportMapper reportMapper;

    public void insert(Report report){
        reportMapper.insertSelective(report);
    }
}
