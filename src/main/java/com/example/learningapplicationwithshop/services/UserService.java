package com.example.learningapplicationwithshop.services;


import com.example.learningapplicationwithshop.model.User;
import com.example.learningapplicationwithshop.model.dto.UserDto;
import com.example.learningapplicationwithshop.model.dto.UserSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getAllUsers(int page, int size);

    void deleteUserById(int id);

    UserDto getUserByLastName(String lastname);

    UserDto createUser(UserSaveDto user);

    UserDto updateUser(Integer id, UserSaveDto user);

    UserDto updateQuestionsLearned(Integer id, List<Integer> indexes);

    UserDto findByLogin(String login);

    UserDto findById(int id);

    UserDto findByEmail(String email);

    UserDto changeEnable(int id, boolean isEnable);

    Boolean isExistsByLogin(String login);

    Boolean isExistsByEmail(String email);

}
