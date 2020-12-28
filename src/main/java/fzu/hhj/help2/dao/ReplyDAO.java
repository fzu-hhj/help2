package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.ReplyMapper;
import fzu.hhj.help2.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.entity.Example;

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
}
