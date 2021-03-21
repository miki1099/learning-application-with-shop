package com.example.learningapplicationwithshop.services.implementation;

import com.example.learningapplicationwithshop.model.User;
import com.example.learningapplicationwithshop.repositories.UserRepository;
import com.example.learningapplicationwithshop.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;


    @Override
    public Page<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public Optional<User> getUserByLastName(String lastname) {
        return userRepository.findFirstByLastName(lastname);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Boolean isExistsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public Boolean isExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
