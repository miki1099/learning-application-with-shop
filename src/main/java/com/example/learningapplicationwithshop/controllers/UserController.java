package com.example.learningapplicationwithshop.controllers;

import com.example.learningapplicationwithshop.model.User;
import com.example.learningapplicationwithshop.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping(value = "/admin/users", params = {"page", "size"})
    public List<User> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<User> result = userService.getAllUsers(page, size);
        if (page > result.getTotalPages()) {
            throw new RuntimeException("Page out of index");
        }
        return result.getContent();
    }

}
