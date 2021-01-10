package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.ReplyMapper;
import fzu.hhj.help2.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Repository("replyDAO")
public class ReplyDAO {
    @Autowired
    ReplyMapper replyMapper;

    /**
     * 获取用户发布的回复数
     * @param userId 用户id
     * @return 回复数
     */
    public Integer getNumOfUserSendReply(Integer userId){
        Example example = new Example(Reply.class);
        example.createCriteria().andEqualTo("userId", userId);
        return replyMapper.selectCountByExample(example);
    }

    /**
     * 查询用户发布的回复
     * @param userId 用户id
     * @return 回复集合
     */
    public List<Reply> ListReplyByUserId(Integer userId){
        Example example = new Example(Reply.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("isDeleted", "0");
        return replyMapper.selectByExample(example);
    }

    /**
     * 获得任务下的所有回复
     * @param taskId 任务id
     * @param sort 排序方式
     * @return 回复集
     */
    public List<Reply> listReplyByTaskIdSort(Integer taskId, Integer sort){
        Example example = new Example(Reply.class);
        if(sort == 0){
            example.setOrderByClause("likes DESC");
        }
        else {
            example.setOrderByClause("time DESC");
        }
        example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("isDeleted", "0");
        return replyMapper.selectByExample(example);
    }

    /**
     * 根据任务id获取部分任务的回复
     * @param taskId 任务id
     * @param start 开始索引
     * @param length 长度
     * @param sort 排序方法
     * @return 回复集
     */
    public List<Reply> listPartReplyByTaskIdSort(Integer taskId, Integer start, Integer length, Integer sort){
        return listReplyByTaskIdSort(taskId, sort).subList(start, start + length);
    }

    /**
     * 获取被采纳的回复
     * @param taskId 任务id
     * @return 回复
     */
    public Reply getAdoptedReplyByTaskId(Integer taskId){
        Example example = new Example(Reply.class);
        example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("isAdopted", "1");
        return replyMapper.selectByExample(example).get(0);
    }

    public void insert(Reply reply){
        replyMapper.insertSelective(reply);
    }
}
