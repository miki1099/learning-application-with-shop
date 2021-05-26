package com.example.learningapplicationwithshop.controllers;

import com.example.learningapplicationwithshop.model.User;
import com.example.learningapplicationwithshop.model.dto.UserLoginDto;
import com.example.learningapplicationwithshop.model.dto.UserLoginReturnDto;
import com.example.learningapplicationwithshop.security.JwtTokenUtil;
import com.example.learningapplicationwithshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserLoginReturnDto> login(@RequestBody UserLoginDto user) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getLogin(), user.getPassword()
                    )
            );
            User userAuth = (User) authenticate.getPrincipal();
            UserLoginReturnDto userReturn = new UserLoginReturnDto();
            userReturn.setLogin(userAuth.getLogin());
            userReturn.setToken(jwtTokenUtil.generateAccessToken(userAuth));

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtTokenUtil.generateAccessToken(userAuth)
                    )
                    .body(userReturn);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/refreshToken")
    public ResponseEntity<UserLoginReturnDto> refreshToken(Principal principal) {
        User userAuth = modelMapper.map(userService.findByLogin(principal.getName()), User.class);
        UserLoginReturnDto userReturn = new UserLoginReturnDto();
        userReturn.setLogin(userAuth.getLogin());
        userReturn.setToken(jwtTokenUtil.generateAccessToken(userAuth));

        return ResponseEntity.ok()
                .body(userReturn);
    }
}
