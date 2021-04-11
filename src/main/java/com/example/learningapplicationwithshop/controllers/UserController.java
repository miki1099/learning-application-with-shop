package com.example.learningapplicationwithshop.controllers;

import com.example.learningapplicationwithshop.model.User;
import com.example.learningapplicationwithshop.model.dto.UserDto;
import com.example.learningapplicationwithshop.model.dto.UserLoginDto;
import com.example.learningapplicationwithshop.model.dto.UserSaveDto;
import com.example.learningapplicationwithshop.security.JwtTokenUtil;
import com.example.learningapplicationwithshop.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final ModelMapper modelMapper;

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

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public UserDto create(@Valid @RequestBody UserSaveDto user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/user/me", method = RequestMethod.GET)
    public UserDto getUserById(Principal principal) {
        return userService.findByLogin(principal.getName());
    }

    @RequestMapping(value = "/user/me", method = RequestMethod.PUT)
    public UserDto update(@Valid @RequestBody UserSaveDto user, Principal principal) {
        return userService.updateUser(getPrincipalUserId(principal), user);
    }

    @RequestMapping(value = "/user/updateQuestionsLearned/me", method = RequestMethod.PUT)
    public UserDto updateQuestionsLearned(Principal principal,
                                          @Valid @RequestBody List<Integer> questionsIndexes) {
        return userService.updateQuestionsLearned(getPrincipalUserId(principal), questionsIndexes);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserDto> login(@RequestBody UserLoginDto user) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getLogin(), user.getPassword()
                    )
            );
            User userAuth = (User) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtTokenUtil.generateAccessToken(userAuth)
                    )
                    .body(modelMapper.map(userAuth, UserDto.class));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private int getPrincipalUserId(Principal principal) {
        return userService.findByLogin(principal.getName()).getId();
    }

}
