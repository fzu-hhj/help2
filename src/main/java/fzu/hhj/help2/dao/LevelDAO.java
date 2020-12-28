package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.LevelMapper;
import fzu.hhj.help2.pojo.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("levelDAO")
public class LevelDAO {
    @Autowired
    LevelMapper levelMapper;

    public List<Level> list(){
        return levelMapper.selectAll();
    }
}
