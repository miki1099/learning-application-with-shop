package com.example.learningapplicationwithshop.controllers;

import com.example.learningapplicationwithshop.model.dto.PasswordDto;
import com.example.learningapplicationwithshop.model.dto.UserDto;
import com.example.learningapplicationwithshop.model.dto.UserSaveDto;
import com.example.learningapplicationwithshop.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {


    private final UserService userService;

    @RequestMapping(value = "/admin/users", params = {"page", "size"}, method = RequestMethod.GET)
    public List<UserDto> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return userService.getAllUsers(page, size);
    }

    @RequestMapping(value = "/admin/user/findByLastName/{lastName}", method = RequestMethod.GET)
    public UserDto getUserByLastName(@PathVariable String lastName) {
        return userService.getUserByLastName(lastName);
    }

    @RequestMapping(value = "/admin/user/findByEmail/{email}", method = RequestMethod.GET)
    public UserDto getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @RequestMapping(value = "/admin/user/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UserDto> deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/admin/user/changeEnable/{userId}", method = RequestMethod.PUT)
    public UserDto changeUserEnable(@PathVariable int userId, boolean isEnable) {
        return userService.changeEnable(userId, isEnable);
    }

    @GetMapping(value = "admin/countAllUsers")
    public long countAllUsers() {
        return userService.countAllUsers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public UserDto create(@Valid @RequestBody UserSaveDto user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/user/me", method = RequestMethod.GET)
    public UserDto getUserById(Principal principal) {
        return userService.findByLogin(principal.getName());
    }

    @PutMapping(value = "/user/resetQuestions")
    public UserDto resetUserQuestionsLearned (Principal principal) {
        return userService.resetQuestionLearned(principal.getName());
    }


    @RequestMapping(value = "/user/me", method = RequestMethod.PUT)
    public UserDto update(@Valid @RequestBody UserSaveDto user, Principal principal) {
        return userService.updateUser(getPrincipalUserId(principal), user);
    }

    @PutMapping(value = "/user/me/changePassword")
    public UserDto changePassword(@RequestBody PasswordDto passwordDto, Principal principal) {
        return userService.changePassword(getPrincipalUserId(principal), passwordDto);
    }

    @RequestMapping(value = "/user/updateQuestionsLearned/me", method = RequestMethod.PUT)
    public UserDto updateQuestionsLearned(Principal principal,
                                          @Valid @RequestBody List<Integer> questionsIndexes) {
        return userService.updateQuestionsLearned(getPrincipalUserId(principal), questionsIndexes);
    }

    @GetMapping(value = "user/questionPercentageLearned")
    public double questionPercentageLearned(@Param("category") String category, Principal principal) {
        return userService.questionLearnedPercentage(principal.getName(), category);
    }

    private int getPrincipalUserId(Principal principal) {
        return userService.findByLogin(principal.getName()).getId();
    }

}
