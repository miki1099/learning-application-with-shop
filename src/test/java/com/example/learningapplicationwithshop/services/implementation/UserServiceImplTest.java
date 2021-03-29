package com.example.learningapplicationwithshop.services.implementation;

import com.example.learningapplicationwithshop.exceptions.UserNotFoundException;
import com.example.learningapplicationwithshop.model.Question;
import com.example.learningapplicationwithshop.model.Role;
import com.example.learningapplicationwithshop.model.User;
import com.example.learningapplicationwithshop.model.dto.UserDto;
import com.example.learningapplicationwithshop.model.dto.UserSaveDto;
import com.example.learningapplicationwithshop.repositories.QuestionRepository;
import com.example.learningapplicationwithshop.repositories.RoleRepository;
import com.example.learningapplicationwithshop.repositories.UserRepository;
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
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Spy
    private final ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private UserServiceImpl userService;

    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId(1);
        user1.setEmail("plmnbv@wp.pl");
        user1.setLogin("loginUser1");
        user1.setPassword("password");
        user1.setLastName("Kowalski");
        user2 = new User();
        user2.setId(2);
        user2.setEmail("email2@wp.pl");
        user2.setLogin("loginUser2");
        user2.setPassword("password");
        user3 = new User();
        user3.setId(3);
        user3.setEmail("email3@wp.pl");
        user3.setLogin("loginUser3");
        user3.setPassword("password");
    }

    @Test
    void getAllUsersTest() {
        List<User> data = new ArrayList<>();
        data.add(user1);
        data.add(user2);
        data.add(user3);
        Page<User> pagedResponse = new PageImpl(data);
        when(userRepository.findAll(PageRequest.of(0, 10))).thenReturn(pagedResponse);

        List<UserDto> given = userService.getAllUsers(0,10);

        assertEquals(3,given.size());
        assertEquals(user1.getEmail(),given.get(0).getEmail());
        assertEquals(user2.getEmail(),given.get(1).getEmail());
        assertEquals(user3.getEmail(),given.get(2).getEmail());

    }

    @Test
    void deleteUserByIdThatExistInDatabase() {

        final int id = 1;

        when(userRepository.findById(id)).thenReturn(Optional.of(user1));
        doNothing().when(userRepository).deleteById(id);

        userService.deleteUserById(id);

        verify(userRepository, times(1)).deleteById(id);

    }

    @Test
    void deleteUserByIdAndDoesntExistInDB() {
        assertThrows(UserNotFoundException.class, () -> { userService.deleteUserById(999);});
    }

    @Test
    void getUserByLastName() {

        String lastName = user1.getLastName();
        when(userRepository.findFirstByLastName(anyString())).thenReturn(Optional.of(user1));

        UserDto foundUser = userService.getUserByLastName(lastName);

        assertEquals(user1.getId(), foundUser.getId());
        assertEquals(user1.getLastName(), foundUser.getLastName());
        assertEquals(user1.getPassword(), foundUser.getPassword());

        verify(userRepository, times(1)).findFirstByLastName(anyString());

    }

    @Test
    void createUserAndHaveValidValues() {
        UserSaveDto userDto = new UserSaveDto();
        userDto.setEmail("plmnbv@wp.pl");
        userDto.setLogin("loginUser1");
        userDto.setPassword("password");
        userDto.setLastName("Kowalski");

        when(userRepository.save(any())).thenReturn(user1);
        when(roleRepository.getOne(1)).thenReturn(new Role(1, "USER"));

        UserDto savedUser = userService.createUser(userDto);

        assertEquals(userDto.getLogin(), savedUser.getLogin());
        assertEquals(userDto.getLastName(), savedUser.getLastName());
        assertEquals(1, savedUser.getRoles().size());

    }

    @Test
    void updateUser() {
        UserSaveDto userDto = new UserSaveDto();
        userDto.setName("newName");
        lenient().when(userRepository.existsById(any())).thenReturn(true);
        when(userRepository.getOne(any())).thenReturn(user1);

        UserDto userDto1 = userService.updateUser(1, userDto);

        assertEquals(userDto.getName(), userDto1.getName());

    }

    @Test
    void findByLastName() {
        String lastName = user1.getLastName();
        when(userRepository.findByLogin(anyString())).thenReturn(Optional.of(user1));

        UserDto foundUser = userService.findByLogin(lastName);

        assertEquals(user1.getId(), foundUser.getId());
        assertEquals(user1.getLastName(), foundUser.getLastName());
        assertEquals(user1.getPassword(), foundUser.getPassword());

        verify(userRepository, times(1)).findByLogin(anyString());

    }

    @Test
    void findByEmail() {
        String email = user1.getEmail();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user1));

        UserDto foundUser = userService.findByEmail(email);

        assertEquals(user1.getId(), foundUser.getId());
        assertEquals(user1.getLastName(), foundUser.getLastName());
        assertEquals(user1.getPassword(), foundUser.getPassword());

        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void updateQuestionsLearnedTest() {
        List<Integer> questionsIndexes = new ArrayList<>();
        questionsIndexes.add(1);
        questionsIndexes.add(2);

        Question question1 = new Question();
        question1.setId(1);
        question1.setQuestionName("Question 1 name");
        question1.setGoodAnswer("Good answer");
        question1.setBadAnswer1("Bad answer");

        Question question2 = new Question();
        question2.setId(2);
        question2.setQuestionName("Question 2 name");
        question2.setGoodAnswer("Good 2 answer");
        question2.setBadAnswer1("Bad 2 answer");

        when(questionRepository.findAllById(questionsIndexes)).thenReturn(List.of(question1, question2));
        lenient().when(userRepository.existsById(any())).thenReturn(true);
        when(userRepository.getOne(any())).thenReturn(user1);

        UserDto userReturned = userService.updateQuestionsLearned(1, questionsIndexes);

        assertEquals(2, userReturned.getQuestionsLearned().size());

    }

}