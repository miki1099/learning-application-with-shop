package com.example.learningapplicationwithshop.services.implementation;

import com.example.learningapplicationwithshop.model.Question;
import com.example.learningapplicationwithshop.model.dto.QuestionDto;
import com.example.learningapplicationwithshop.model.dto.UserDto;
import com.example.learningapplicationwithshop.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;

    @Spy
    private final ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private QuestionServiceImpl questionService;

    Question question1;
    Question question2;
    Question question3;

    @BeforeEach
    void setUp() {
        question1 = new Question();
        question1.setId(1);
        question1.setQuestionName("Question 1 name");
        question1.setGoodAnswer("Good answer");
        question1.setBadAnswer1("Bad answer");

        question2 = new Question();
        question2.setId(2);
        question2.setQuestionName("Question 2 name");
        question2.setGoodAnswer("Good 2 answer");
        question2.setBadAnswer1("Bad 2 answer");

        question3 = new Question();
        question3.setId(3);
        question3.setQuestionName("Question 3 name");
        question3.setGoodAnswer("Good 3 answer");
        question3.setBadAnswer1("Bad 3 answer");
        question3.setBadAnswer2("Optional bad ans");
        question3.setBadAnswer3("Second optional bad ans");

    }

    private boolean compareQuestions(QuestionDto expected, QuestionDto actual) {
        return expected.getQuestionName().equals(actual.getQuestionName()) &&
                expected.getGoodAnswer().equals(actual.getGoodAnswer()) &&
                expected.getBadAnswer1().equals(actual.getBadAnswer1()) &&
                (expected.getBadAnswer2() == null || expected.getBadAnswer2().equals(actual.getBadAnswer2())) &&
                (expected.getBadAnswer3() == null || expected.getBadAnswer3().equals(actual.getBadAnswer3())) &&
                expected.getPicture() == actual.getPicture();
    }

    private boolean compareQuestions(Question expected, QuestionDto actual) {
        return expected.getQuestionName().equals(actual.getQuestionName()) &&
                expected.getGoodAnswer().equals(actual.getGoodAnswer()) &&
                expected.getBadAnswer1().equals(actual.getBadAnswer1()) &&
                (expected.getBadAnswer2() == null || expected.getBadAnswer2().equals(actual.getBadAnswer2())) &&
                (expected.getBadAnswer3() == null || expected.getBadAnswer3().equals(actual.getBadAnswer3())) &&
                expected.getPicture() == actual.getPicture();
    }

    @Test
    void getSpecificAmountOfQuestions() {
        final int AMOUNT = 1;
        List<Question> data = new ArrayList<>();
        data.add(question1);

        QuestionDto expected = modelMapper.map(question1, QuestionDto.class);

        Page<Question> pageResponse = new PageImpl(data);
        lenient().when(questionRepository.findAll(PageRequest.of(0, AMOUNT))).thenReturn(pageResponse);

        List<QuestionDto> result= questionService.getSpecificAmountOfQuestions(AMOUNT);

        assertTrue(compareQuestions(expected, result.get(0)));

    }

    @Test
    void deleteQuestionById() {

        final int id = 1;

        when(questionRepository.findById(id)).thenReturn(Optional.of(question1));
        doNothing().when(questionRepository).deleteById(id);

        questionService.deleteQuestionById(id);

        verify(questionRepository, times(id)).deleteById(id);
        verify(questionRepository, times(id)).findById(id);
    }

    @Test
    void updateQuestion() {
        QuestionDto update = new QuestionDto();
        update.setQuestionName("New question answer");
        final int id = 1;
        when(questionRepository.existsById(id)).thenReturn(true);
        when(questionRepository.getOne(id)).thenReturn(question1);

        QuestionDto result = questionService.updateQuestion(id, update);

        assertEquals(update.getQuestionName(), result.getQuestionName());
    }

    @Test
    void addQuestion() {
        QuestionDto questionCreate = new QuestionDto();
        questionCreate.setId(1);
        questionCreate.setQuestionName("Question 1 name");
        questionCreate.setGoodAnswer("Good answer");
        questionCreate.setBadAnswer1("Bad answer");

        when(questionRepository.save(any())).thenReturn(question1);

        QuestionDto result = questionService.addQuestion(questionCreate);

        assertTrue(compareQuestions(questionCreate, result));
    }

    @Test
    void getAllQuestions() {

        List<Question> data = new ArrayList<>();
        data.add(question1);
        data.add(question2);
        data.add(question3);

        final int size = 3;
        final int page = 0;

        Page<Question> resultPage = new PageImpl<>(data);

        when(questionRepository.findAll(PageRequest.of(page, size)))
                .thenReturn(resultPage);

        List<QuestionDto> resultList = questionService.getAllQuestions(page, size);

        assertEquals(size, resultList.size());
        assertTrue(compareQuestions(question1, resultList.get(0)));
        assertTrue(compareQuestions(question2, resultList.get(1)));
        assertTrue(compareQuestions(question3, resultList.get(2)));
    }

    @Test
    void getQuestionsPagesCountNothingInDb() {
        when(questionRepository.count()).thenReturn(0L);
        int pages = questionService.getQuestionsPagesCount(3);

        assertEquals(-1, pages);
    }

    @Test
    void getQuestionsPagesCountTheSameNumber() {
        when(questionRepository.count()).thenReturn(1000L);
        int pages = questionService.getQuestionsPagesCount(1000);

        assertEquals(0, pages);
    }

    @Test
    void getQuestionsPagesCountWithALittleBigger() {
        when(questionRepository.count()).thenReturn(1001L);
        int pages = questionService.getQuestionsPagesCount(1000);

        assertEquals(1, pages);
    }

}