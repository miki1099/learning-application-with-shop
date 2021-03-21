package com.example.learningapplicationwithshop.services;


import com.example.learningapplicationwithshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Page<User> getAllUsers(int page, int size);

    void deleteUser(User user);

    Optional<User> getUserByLastName(String lastname);

    User createUser(User user);

    User updateUser(User user);

    Optional<User> findByLogin(String login);

    Boolean isExistsByLogin(String login);

    Boolean isExistsByEmail(String email);

}
