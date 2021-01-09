package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.TaskMapper;
import fzu.hhj.help2.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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

    /**
     * 查询用户发布的任务
     * @param userId 用户id
     * @return 任务集
     */
    public List<Task> ListTaskByUserId(Integer userId){
        Example example = new Example(Task.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("isDeleted","0");
        return taskMapper.selectByExample(example);
    }

    public void insert(Task task){
        taskMapper.insertSelective(task);
    }

    public Task selectNoDelete(Integer taskId){
        Example example = new Example(Task.class);
        example.createCriteria().andEqualTo("id", taskId).andEqualTo("isDeleted", "0");
        return taskMapper.selectByExample(example).get(0);
    }

    /**
     * 根据主键更新任务信息
     * @param task 任务
     */
    public void updateSelective(Task task){
        taskMapper.updateByPrimaryKeySelective(task);
    }
}
