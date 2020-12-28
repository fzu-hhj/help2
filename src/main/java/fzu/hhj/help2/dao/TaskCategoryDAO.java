package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.TaskCategoryMapper;
import fzu.hhj.help2.pojo.TaskCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("/taskCategoryMapper")
public class TaskCategoryDAO {
    @Autowired
    TaskCategoryMapper taskCategoryMapper;

    public TaskCategory selectById(Integer id){
        return taskCategoryMapper.selectByPrimaryKey(id);
    }
}
