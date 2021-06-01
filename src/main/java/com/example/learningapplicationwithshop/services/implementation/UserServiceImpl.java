package com.example.learningapplicationwithshop.services.implementation;

import com.example.learningapplicationwithshop.exceptions.BadInputException;
import com.example.learningapplicationwithshop.exceptions.PasswordDoesNotMatchException;
import com.example.learningapplicationwithshop.exceptions.UserNotFoundException;
import com.example.learningapplicationwithshop.model.Address;
import com.example.learningapplicationwithshop.model.Question;
import com.example.learningapplicationwithshop.model.User;
import com.example.learningapplicationwithshop.model.dto.PasswordDto;
import com.example.learningapplicationwithshop.model.dto.UserDto;
import com.example.learningapplicationwithshop.model.dto.UserSaveDto;
import com.example.learningapplicationwithshop.repositories.AddressRepository;
import com.example.learningapplicationwithshop.repositories.QuestionRepository;
import com.example.learningapplicationwithshop.repositories.RoleRepository;
import com.example.learningapplicationwithshop.repositories.UserRepository;
import com.example.learningapplicationwithshop.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final QuestionRepository questionRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<UserDto> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).getContent().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        if(userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("id");
        }

    }

    @Override
    @Transactional
    public void resetPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("email"));

        String generatedPassword = RandomStringUtils.random(20, true, true);
        user.setPassword(passwordEncoder.encode(generatedPassword));

        userRepository.save(user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Password Reset!");
        mailMessage.setFrom("learning.app.botmail@gmail.com");
        mailMessage.setText("You made request to reset password.\nYour new password is:\n"
                + generatedPassword + "\nNow login with new one an change it in user settings!" );
        javaMailSender.send(mailMessage);
    }

    @Override
    public UserDto getUserByLastName(String lastname) {
        Optional<User> userFound = userRepository.findFirstByLastName(lastname);
        if(userFound.isPresent()) return modelMapper.map(userFound.get(), UserDto.class);
        else throw new UserNotFoundException("Last Name");
    }

    @Override
    @Transactional
    public UserDto createUser(UserSaveDto user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSaved = userRepository.save(modelMapper.map(user, User.class));
        userSaved.setEnabled(true);
        userSaved.setRoles(Set.of(roleRepository.getOne(1)));
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Welcome to our app!");
        mailMessage.setFrom("learning.app.botmail@gmail.com");
        mailMessage.setText("Hello!\nYou created account on our website.\nYour login is: "
                + userSaved.getLogin() + "\nPassword: " + password
                + "\nWe hope you will learn a lot with us! :)" );
        javaMailSender.send(mailMessage);
        return modelMapper.map(userSaved, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto updateUser(Integer id, UserSaveDto user) {
        User userFound = getOneSafe(id);
        userFound.setId(id);
        if(user.getAddress() != null) {
            Address userFoundAddress = userFound.getAddress();
            userFound.setAddress(modelMapper.map(user.getAddress(), Address.class));
            if(userFoundAddress != null) {
                addressRepository.deleteById(userFoundAddress.getId());
            }
        }
        if(user.getEmail() != null) userFound.setEmail(user.getEmail());
        if(user.getName() != null) userFound.setName(user.getName());
        if(user.getLastName() != null) userFound.setLastName(user.getLastName());
        if(user.getPhone() != null) userFound.setPhone(user.getPhone());
        return modelMapper.map(userFound, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto updateQuestionsLearned(Integer id, List<Integer> indexes) {

        if (indexes == null || indexes.isEmpty()) throw new BadInputException();
        User userFound = getOneSafe(id);
        userFound.setId(id);

        Set<Question> questionsLearned = userFound.getQuestionsLearned();
//        for (int questionIndex : indexes) {
//            Optional<Question> questionFound = questionRepository.findById(questionIndex);
//            questionFound.ifPresent(questionsLearned::add);
//        }
        questionsLearned.addAll(questionRepository.findAllById(indexes));
        userFound.setQuestionsLearned(questionsLearned);
        return modelMapper.map(userFound, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto resetQuestionLearned(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("User with login: " + login + " not found"));
        user.setQuestionsLearned(new HashSet<>());
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto findByLogin(String login) {
        Optional<User> userFound= userRepository.findByLogin(login);
        if(userFound.isPresent()) return userFound.map(user -> modelMapper.map(user, UserDto.class)).orElse(null);
        else throw new UserNotFoundException("login");
    }

    @Override
    public UserDto findById(int id) {
        return modelMapper.map(getOneSafe(id), UserDto.class);
    }

    @Override
    public UserDto findByEmail(String email) {
        Optional<User> userFound= userRepository.findByEmail(email);
        if(userFound.isPresent()) return modelMapper.map(userFound.get(), UserDto.class);
        else throw new UserNotFoundException("email");
    }

    @Override
    @Transactional
    public UserDto changeEnable(int id, boolean isEnable) {
        User user = getOneSafe(id);
        user.setEnabled(isEnable);
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto changePassword(int id, PasswordDto passwordDto) {
        User userFound = getOneSafe(id);
        userFound.setId(id);
        if(!passwordEncoder.matches(passwordDto.getOldPassword(), userFound.getPassword())) {
            throw new PasswordDoesNotMatchException();
        }
        userFound.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        return modelMapper.map(userFound, UserDto.class);
    }

    @Override
    public double questionLearnedPercentage(String userLogin, String category) {
        User user = userRepository.findByLogin(userLogin)
                .orElseThrow(() ->
                        new UserNotFoundException("User with login: " + userLogin + " does not exist in database!"));
        double specificQuestionLearned;
        long specificQuestionsInDb;
        if(category != null) {
            specificQuestionLearned = user.getQuestionsLearned()
                    .stream()
                    .filter(question -> question.getCategory().equals(category))
                    .count();
            specificQuestionsInDb = questionRepository.countByCategory(category);
        } else {
            specificQuestionLearned = user.getQuestionsLearned().size();
            specificQuestionsInDb = questionRepository.count();
        }
        if(specificQuestionsInDb == 0) return 0;
        return specificQuestionLearned/specificQuestionsInDb * 100;
    }

    @Override
    public long countAllUsers() {
        return userRepository.count();
    }

    @Override
    public Boolean isExistsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public Boolean isExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private User getOneSafe(Integer id) {
        if (userRepository.existsById(id)) {
            return userRepository.getOne(id);
        } else {
            throw new UserNotFoundException("id");
        }
    }

}
