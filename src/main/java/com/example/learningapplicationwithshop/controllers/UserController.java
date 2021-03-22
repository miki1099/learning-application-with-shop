package com.example.learningapplicationwithshop.controllers;

import com.example.learningapplicationwithshop.model.User;
import com.example.learningapplicationwithshop.model.dto.UserDto;
import com.example.learningapplicationwithshop.model.dto.UserSaveDto;
import com.example.learningapplicationwithshop.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @RequestMapping(value = "/admin/users", params = {"page", "size"}, method = RequestMethod.GET)
    public List<UserDto> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        List<UserDto> users = userService.getAllUsers(page, size);
        return users;
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

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public UserDto create(@Valid @RequestBody UserSaveDto user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public UserDto getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public UserDto update(@PathVariable Integer id, @Valid @RequestBody UserSaveDto user) {
        return userService.updateUser(id, user);
    }

}
