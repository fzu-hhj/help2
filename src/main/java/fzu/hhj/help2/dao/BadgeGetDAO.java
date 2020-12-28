package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.BadgeGetMaper;
import fzu.hhj.help2.pojo.BadgeGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

@Repository("badgeDAO")
public class BadgeGetDAO {
    @Autowired
    BadgeGetMaper badgeGetMaper;

    public Integer getNumOfUserGetBadge(Integer userId){
        Example example = new Example(BadgeGet.class);
        example.createCriteria().andEqualTo("userId", userId);
        return badgeGetMaper.selectCountByExample(example);
    }
}
