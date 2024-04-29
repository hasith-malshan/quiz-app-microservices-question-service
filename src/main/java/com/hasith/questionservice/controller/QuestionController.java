package com.hasith.questionservice.controller;


import com.hasith.questionservice.entities.Question;
import com.hasith.questionservice.entities.QuestionWrapper;
import com.hasith.questionservice.entities.Response;
import com.hasith.questionservice.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {

    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("all-questions")
    public ResponseEntity<List<Question>> quiz() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable("category") String category) {
        String capitalizedCategoryName = category.substring(0, 1).toUpperCase() + category.substring(1);
        return questionService.findByCategory(capitalizedCategoryName);
    }

    @PostMapping("add-question")
    public ResponseEntity addQuestion(@RequestBody Question question) {
        questionService.addQuestion(question);
        return new ResponseEntity<>("Question Added", HttpStatus.CREATED);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String categoryName,
            @RequestParam String numberOfQuestions
    ) {
        return questionService.getQuestionsForQuiz(categoryName, numberOfQuestions);
    }

    @PostMapping("get-questions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(
            @RequestBody List<Integer> questionIds
    ) {
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("get-score")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return questionService.getScore(responses);
    }

}
