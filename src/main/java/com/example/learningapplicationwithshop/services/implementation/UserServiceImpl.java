package com.example.learningapplicationwithshop.services.implementation;

import com.example.learningapplicationwithshop.model.User;
import com.example.learningapplicationwithshop.repositories.UserRepository;
import com.example.learningapplicationwithshop.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
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
}
