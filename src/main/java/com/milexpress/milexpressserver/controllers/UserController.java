package com.milexpress.milexpressserver.controllers;

import com.milexpress.milexpressserver.config.TokenService;
import com.milexpress.milexpressserver.model.db.User;
import com.milexpress.milexpressserver.model.request.AuthRequest;
import com.milexpress.milexpressserver.model.request.UpdateUserRequest;
import com.milexpress.milexpressserver.model.request.UserRequest;
import com.milexpress.milexpressserver.model.response.AuthResponse;
import com.milexpress.milexpressserver.model.response.UserResponse;
import com.milexpress.milexpressserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    @PostMapping("/authenticate")
    public AuthResponse authenticateUser(@RequestBody AuthRequest authRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }

    @PostMapping("/update")
    public User updateUserData(@RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUserData(updateUserRequest);
    }

    @GetMapping("/getUser")
    public UserResponse getUser(@RequestBody String userEmail) {
        System.out.println("user email: " + userEmail);
        return userService.getUser(userEmail);
    }

    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}