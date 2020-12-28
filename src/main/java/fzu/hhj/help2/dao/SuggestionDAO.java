package fzu.hhj.help2.dao;

import fzu.hhj.help2.mapper.SuggestionMapper;
import fzu.hhj.help2.pojo.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("suggestionDAO")
public class SuggestionDAO {
    @Autowired
    SuggestionMapper suggestionMapper;

    public void insert(Suggestion suggestion){
        suggestionMapper.insertSelective(suggestion);
    }
}
