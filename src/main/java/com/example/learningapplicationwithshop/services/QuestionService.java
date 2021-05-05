package com.example.learningapplicationwithshop.services;

import com.example.learningapplicationwithshop.model.dto.QuestionDto;

import java.util.List;

public interface QuestionService {

    List<QuestionDto> getSpecificAmountOfQuestions(int amount);

    void deleteQuestionById(int id);

    QuestionDto updateQuestion(int id, QuestionDto updatedQuestion);

    QuestionDto addQuestion(QuestionDto newQuestion);

    List<QuestionDto> getAllQuestions(int page, int size);

    int getQuestionsPagesCount(int size);

    int getQuestionsInCategoryCount(String category);

    int getQuestionsInCategoryCount(String category, int size);

    List<QuestionDto> getQuestionsFromCategoryPage(int page, int size, String category);

    List<QuestionDto> getQuestionsNotLearned(String userLogin, int amount, String category);
}
