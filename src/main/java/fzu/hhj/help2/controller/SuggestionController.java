package fzu.hhj.help2.controller;

import fzu.hhj.help2.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/suggest")
public class SuggestionController {
    @Autowired
    SuggestionService suggestionService;

    @RequestMapping("/offerSuggestion")
    public Map<String, Object> suggest(@RequestParam Integer score, @RequestParam String content){
        return suggestionService.suggest(score, content);
    }
}
