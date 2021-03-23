package com.example.learningapplicationwithshop.services.implementation;

import com.example.learningapplicationwithshop.exceptions.QuestionNotFoundException;
import com.example.learningapplicationwithshop.model.Question;
import com.example.learningapplicationwithshop.model.dto.QuestionDto;
import com.example.learningapplicationwithshop.repositories.QuestionRepository;
import com.example.learningapplicationwithshop.services.QuestionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final ModelMapper modelMapper;


    @Override
    public List<QuestionDto> getSpecificAmountOfQuestions(int amount) {
        long questionAmount = questionRepository.count();

        int page = (int) (Math.random() * questionAmount/amount);

        Page<Question> questionPage = questionRepository.findAll(PageRequest.of(page, amount));

        if(questionPage.hasContent()) {
            return questionPage.getContent().stream()
                    .map(question -> modelMapper.map(question, QuestionDto.class))
                    .collect(Collectors.toList());
        }
        else {
            throw new QuestionNotFoundException("this amount: " + amount);
        }
    }

    @Override
    public void deleteQuestionById(int id) {
        if(questionRepository.findById(id).isPresent()) {
            questionRepository.deleteById(id);
        } else {
            throw new QuestionNotFoundException("id: " + id);
        }
    }

    @Override
    public QuestionDto updateQuestion(int id, QuestionDto updatedQuestion) {
        Question question = getOneSafe(id);
        if(updatedQuestion.getQuestionName() != null) question.setQuestionName(updatedQuestion.getQuestionName());
        if(updatedQuestion.getGoodAnswer() != null) question.setGoodAnswer(updatedQuestion.getGoodAnswer());
        if(updatedQuestion.getBadAnswer1() != null) question.setBadAnswer1(updatedQuestion.getBadAnswer1());
        if(updatedQuestion.getBadAnswer2() != null) question.setBadAnswer2(updatedQuestion.getBadAnswer2());
        if(updatedQuestion.getBadAnswer3() != null) question.setBadAnswer3(updatedQuestion.getBadAnswer3());
        if(updatedQuestion.getPicture() != null) question.setPicture(updatedQuestion.getPicture());

        return modelMapper.map(question, QuestionDto.class);
    }

    @Override
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

        return questionsRecord == 0 ? -1 : (int) ( questionsRecord - 1)/size;
    }

    private Question getOneSafe(Integer id) {
        if (questionRepository.existsById(id)) {
            return questionRepository.getOne(id);
        } else {
            throw new QuestionNotFoundException("id: " + id);
        }
    }

}
