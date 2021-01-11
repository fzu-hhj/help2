package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.ReportMapper;
import fzu.hhj.help2.pojo.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Repository("reportDAO")
public class ReportDAO {
    @Autowired
    ReportMapper reportMapper;

    public void insert(Report report){
        reportMapper.insertSelective(report);
    }

    public List<Report> selectAllNoHandle(){
        Example example = new Example(Report.class);
        example.createCriteria().andEqualTo("isHandled", "0");
        example.setOrderByClause("reportedCategory ASC ,reportedId ASC");
        return reportMapper.selectByExample(example);
    }

    public Report getReportById(Integer reportId){
        Example example = new Example(Report.class);
        example.createCriteria().andEqualTo("isHandled", "0").andEqualTo("id", reportId);
        if(reportMapper.selectByExample(example).size() == 0){
            return null;
        }
        return reportMapper.selectByExample(example).get(0);
    }

    public void updateSelective(Report report){
        reportMapper.updateByPrimaryKeySelective(report);
    }

    public void updateByCategoryAndReportedId(String category, Integer reportedId,Report report){
        Example example = new Example(Report.class);
        example.createCriteria().andEqualTo("reportedCategory", category).
                andEqualTo("reportedId", reportedId);
        reportMapper.updateByExampleSelective(report, example);
    }
}
