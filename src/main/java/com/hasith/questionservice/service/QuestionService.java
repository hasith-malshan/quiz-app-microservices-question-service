package com.hasith.questionservice.service;

import com.hasith.questionservice.dao.QuestionDao;
import com.hasith.questionservice.entities.Question;
import com.hasith.questionservice.entities.QuestionWrapper;
import com.hasith.questionservice.entities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {

    private QuestionDao questionDao;

    @Autowired
    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<List<Question>>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> findByCategory(String category) {
        try {
            return new ResponseEntity<List<Question>>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, String numberOfQuestions) {
        List<Integer> questions = questionDao.findRandomQuestionByCategory(categoryName, Integer.parseInt(numberOfQuestions));

        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for (Integer id : questionIds){
            questions.add(questionDao.findById(id).get());
        }

        for (Question question : questions){
            QuestionWrapper questionWrapper = new QuestionWrapper();
            questionWrapper.setId(question.getId());
            questionWrapper.setQuestionTitle(question.getQuestionTitle());
            questionWrapper.setOption1(question.getOption1());
            questionWrapper.setOption2(question.getOption2());
            questionWrapper.setOption3(question.getOption3());
            questionWrapper.setOption4(question.getOption4());

            wrappers.add(questionWrapper);
        }

        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right = 0;
        int i = 0;
        for (Response response : responses) {
           Question question = questionDao.findById(response.getId()).get();

           if (response.getResponse().equals(question.getRightAnswer()))
               right++;
        }

        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
