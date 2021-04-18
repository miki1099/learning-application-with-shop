package com.example.learningapplicationwithshop.controllers;

import com.example.learningapplicationwithshop.model.dto.QuestionDto;
import com.example.learningapplicationwithshop.services.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
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

    @RequestMapping(value = "/admin/question/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<QuestionDto> deleteQuestionById(@PathVariable int id) {
        questionService.deleteQuestionById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/admin/question/create", method = RequestMethod.POST)
    public QuestionDto createQuestion(@Valid @RequestBody QuestionDto newQuestion) {
        return questionService.addQuestion(newQuestion);
    }

    @RequestMapping(value = "/admin/question/update/{id}", method = RequestMethod.PUT)
    public QuestionDto updateQuestion(@Valid @RequestBody QuestionDto updateQuestion, @PathVariable int id) {
        return questionService.updateQuestion(id, updateQuestion);
    }

    @RequestMapping(value = "/question/getCountByCategory/{category}", method = RequestMethod.GET)
    public int getQuestionsCountByCategory(@PathVariable String category, @Param("size") Integer size) {
        if (size != null) return questionService.getQuestionsInCategoryCount(category, size);
        else return questionService.getQuestionsInCategoryCount(category);
    }

    @RequestMapping(value = "/question/getByCategory/{category}", method = RequestMethod.GET)
    public List<QuestionDto> getQuestionsByCategory(@PathVariable String category,
                                                    @RequestParam("size") int size,
                                                    @RequestParam("page") int page) {
        return questionService.getQuestionsFromCategoryPage(page, size, category);
    }

}
