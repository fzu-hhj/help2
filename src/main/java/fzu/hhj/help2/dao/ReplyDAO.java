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
}
