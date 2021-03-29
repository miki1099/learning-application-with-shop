package com.example.learningapplicationwithshop.controllers;

import com.example.learningapplicationwithshop.model.dto.QuestionDto;
import com.example.learningapplicationwithshop.services.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @RequestMapping(value = "/question/getPage", params = {"page", "size"}, method = RequestMethod.GET)
    public List<QuestionDto> getQuestionPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return questionService.getAllQuestions(page, size);
    }

    @RequestMapping(value = "/question/getAmount", params = {"amount"}, method = RequestMethod.GET)
    public List<QuestionDto> getSomeQuestions(@RequestParam int amount) {
        return questionService.getSpecificAmountOfQuestions(amount);
    }

    @RequestMapping(value = "/question/number", params = {"size"}, method = RequestMethod.GET)
    public int getQuestionPageCount(@RequestParam("size") int size) {
        return questionService.getQuestionsPagesCount(size);
    }

    @RequestMapping(value = "/question/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<QuestionDto> deleteQuestionById(@PathVariable int id) {
        questionService.deleteQuestionById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/question/create", method = RequestMethod.POST)
    public QuestionDto createQuestion(@Valid @RequestBody QuestionDto newQuestion) {
        return questionService.addQuestion(newQuestion);
    }

    @RequestMapping(value = "/question/update/{id}", method = RequestMethod.PUT)
    public QuestionDto updateQuestion(@Valid @RequestBody QuestionDto updateQuestion, @PathVariable int id) {
        return questionService.updateQuestion(id, updateQuestion);
    }

}
