package fzu.hhj.help2.service;


import fzu.hhj.help2.Util.ServletUtil;
import fzu.hhj.help2.dao.SuggestionDAO;
import fzu.hhj.help2.pojo.Suggestion;
import fzu.hhj.help2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static fzu.hhj.help2.Util.ConstantUtil.*;

@Service
public class SuggestionServiceImpl implements SuggestionService {
    @Autowired
    SuggestionDAO suggestionDAO;
    @Override
    public Map<String, Object> suggest(Integer score, String content) {
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = (User)ServletUtil.getRequest().getSession().getAttribute("user");
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, NO_USER);
            return result;
        }
        Suggestion suggestion = new Suggestion();
        suggestion.setUserId(user.getId());
        suggestion.setScore(score);
        suggestion.setContent(content);
        suggestion.setTime(new Timestamp(System.currentTimeMillis()));
        suggestionDAO.insert(suggestion);
        result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return result;
    }
}
