package com.hasith.questionservice.controller;

import com.hasith.quizapp.entities.Question;
import com.hasith.quizapp.service.QuestionService;
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
    public ResponseEntity<List<Question>> quiz(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable("category") String category) {
        String capitalizedCategoryName = category.substring(0,1).toUpperCase() + category.substring(1);
        return questionService.findByCategory(capitalizedCategoryName);
    }

    @PostMapping("add-question")
    public ResponseEntity addQuestion(@RequestBody Question question){
        questionService.addQuestion(question);
        return new ResponseEntity<>("Question Added", HttpStatus.CREATED);
    }

}
