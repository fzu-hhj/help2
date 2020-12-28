package fzu.hhj.help2.service;

import java.util.Map;

public interface SuggestionService {

    /**
     * 评分和提出建议
     * @param score 评分
     * @param content 建议内容
     * @return 结果码
     */
    public Map<String, Object> suggest(Integer score, String content);
}
