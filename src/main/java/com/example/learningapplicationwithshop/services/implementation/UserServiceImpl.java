package com.example.learningapplicationwithshop.services.implementation;

import com.example.learningapplicationwithshop.exceptions.UserNotFoundException;
import com.example.learningapplicationwithshop.model.Address;
import com.example.learningapplicationwithshop.model.User;
import com.example.learningapplicationwithshop.model.dto.UserDto;
import com.example.learningapplicationwithshop.model.dto.UserSaveDto;
import com.example.learningapplicationwithshop.repositories.RoleRepository;
import com.example.learningapplicationwithshop.repositories.UserRepository;
import com.example.learningapplicationwithshop.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<UserDto> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).getContent().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUser(UserDto user) {
        userRepository.delete(modelMapper.map(user, User.class));
    }

    @Override
    public UserDto getUserByLastName(String lastname) {
        Optional<User> userFound = userRepository.findFirstByLastName(lastname);
        return userFound.map(user -> modelMapper.map(user, UserDto.class)).orElse(null);
    }

    @Override
    @Transactional
    public UserDto createUser(UserSaveDto user) {
        User userSaved = userRepository.save(modelMapper.map(user, User.class));
        userSaved.setRoles(Set.of(roleRepository.getOne(1)));
        return modelMapper.map(userSaved, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto updateUser(Integer id, UserSaveDto user) {
        User userFound = getOneSafe(id);
        userFound.setId(id);
        if(user.getAddress() != null) userFound.setAddress(modelMapper.map(user.getAddress(), Address.class));
        if(user.getEmail() != null) userFound.setEmail(user.getEmail());
        if(user.getName() != null) userFound.setName(user.getName());
        if(user.getLastName() != null) userFound.setLastName(user.getLastName());
        if(user.getPhone() != null) userFound.setPhone(user.getPhone());
        return modelMapper.map(userFound, UserDto.class);
    }

    @Override
    public UserDto findByLogin(String login) {
        Optional<User> userFound= userRepository.findByLogin(login);
        return userFound.map(user -> modelMapper.map(user, UserDto.class)).orElse(null);
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
            throw new UserNotFoundException();
        }
    }

}
