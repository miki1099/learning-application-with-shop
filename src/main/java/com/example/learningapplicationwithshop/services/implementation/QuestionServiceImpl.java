package com.example.learningapplicationwithshop.services.implementation;

import com.example.learningapplicationwithshop.exceptions.BadInputException;
import com.example.learningapplicationwithshop.exceptions.QuestionNotFoundException;
import com.example.learningapplicationwithshop.exceptions.UserNotFoundException;
import com.example.learningapplicationwithshop.model.Question;
import com.example.learningapplicationwithshop.model.User;
import com.example.learningapplicationwithshop.model.dto.QuestionDto;
import com.example.learningapplicationwithshop.repositories.QuestionRepository;
import com.example.learningapplicationwithshop.repositories.UserRepository;
import com.example.learningapplicationwithshop.services.QuestionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;


    @Override
    public List<QuestionDto> getSpecificAmountOfQuestions(int amount) {
        long questionAmount = questionRepository.count();
        if (questionAmount < amount) throw new QuestionNotFoundException("this amount: "
                + amount
                + " actual amount: "
                + questionAmount);

        List<Question> questions = questionRepository.getSpecificAmount(amount);
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteQuestionById(int id) {
        if (questionRepository.findById(id).isPresent()) {
            questionRepository.deleteById(id);
        } else {
            throw new QuestionNotFoundException("id: " + id);
        }
    }

    @Override
    @Transactional
    public QuestionDto updateQuestion(int id, QuestionDto updatedQuestion) {
        Question question = getOneSafe(id);
        if (updatedQuestion.getQuestionName() != null) question.setQuestionName(updatedQuestion.getQuestionName());
        if (updatedQuestion.getGoodAnswer() != null) question.setGoodAnswer(updatedQuestion.getGoodAnswer());
        if (updatedQuestion.getBadAnswer1() != null) question.setBadAnswer1(updatedQuestion.getBadAnswer1());
        if (updatedQuestion.getBadAnswer2() != null) question.setBadAnswer2(updatedQuestion.getBadAnswer2());
        if (updatedQuestion.getBadAnswer3() != null) question.setBadAnswer3(updatedQuestion.getBadAnswer3());
        if (updatedQuestion.getPicture() != null) question.setPicture(updatedQuestion.getPicture());
        if (updatedQuestion.getCategory() != null) question.setCategory(updatedQuestion.getCategory());
        return modelMapper.map(question, QuestionDto.class);
    }

    @Override
    @Transactional
    public QuestionDto addQuestion(QuestionDto newQuestion) {
        Question question = modelMapper.map(newQuestion, Question.class);
        question = questionRepository.save(question);
        return modelMapper.map(question, QuestionDto.class);
    }

    @Override
    public List<QuestionDto> getAllQuestions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return questionRepository.findAll(pageable).getContent().stream()
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .collect(Collectors.toList());
    }

    //throws -1 when there is nothing to show
    //count pages from 0
    @Override
    public int getQuestionsPagesCount(int size) {
        long questionsRecord = questionRepository.count();
        return questionsRecord == 0 ? -1 : (int) (questionsRecord - 1) / size;
    }

    @Override
    public int getQuestionsInCategoryCount(String category) {
        return (int) questionRepository.countByCategory(category);
    }

    @Override
    public int getQuestionsInCategoryCount(String category, int size) {
        if (size == 0) throw new BadInputException();
        long questionsRecord = questionRepository.countByCategory(category);
        return questionsRecord == 0 ? -1 : (int) (questionsRecord - 1) / size;
    }

    @Override
    public List<QuestionDto> getQuestionsFromCategoryPage(int page, int size, String category) {
        Pageable pageable = PageRequest.of(page, size);

        return questionRepository.findAllByCategory(category, pageable).getContent().stream()
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> getQuestionsNotLearned(String userLogin, int amount, String category) {
        List<Integer> questionsLearnedIdList = getUserSafe(userLogin).getQuestionsLearned()
                .stream()
                .map(Question::getId)
                .collect(Collectors.toList());
        List<Question> questions;
        if(category == null) {
            questions = questionRepository.findQuestionsNotInIdList(questionsLearnedIdList, amount);
        } else {
            questions = questionRepository.findQuestionsByCategoryNotInIdList(questionsLearnedIdList, amount, category);
        }
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .collect(Collectors.toList());
    }

    private Question getOneSafe(Integer id) {
        if (questionRepository.existsById(id)) {
            return questionRepository.getOne(id);
        } else {
            throw new QuestionNotFoundException("id: " + id);
        }
    }

    private User getUserSafe(String login) {
            return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("id"));
    }
}
