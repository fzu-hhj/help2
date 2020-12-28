package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.TaskMapper;
import fzu.hhj.help2.mapper.UserMapper;
import fzu.hhj.help2.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

@Repository("taskDAO")
public class TaskDAO {
    @Autowired
    TaskMapper taskMapper;

    /**
     * 查询用户发布的任务数
     * @param userId 用户id
     * @return 任务数
     */
    public Integer getNumOfUserPublishTask(Integer userId){
        Example example = new Example(Task.class);
        example.createCriteria().andEqualTo("userId",userId);
        return taskMapper.selectCountByExample(example);
    }
}
