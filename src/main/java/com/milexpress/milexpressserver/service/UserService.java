package com.milexpress.milexpressserver.service;

import com.milexpress.milexpressserver.config.TokenService;
import com.milexpress.milexpressserver.model.request.UpdateUserRequest;
import com.milexpress.milexpressserver.model.request.UserRequest;
import com.milexpress.milexpressserver.model.response.UserResponse;
import com.milexpress.milexpressserver.model.db.User;
import com.milexpress.milexpressserver.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(UserRequest userRegRequest) {
        User user = convertToUser(userRegRequest);

        if (this.userRepository.findById(userRegRequest.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario ja registrado");
        } else {
            return this.userRepository.save(user);
        }
    }


    private User convertToUser(UserRequest userRegRequest) {
        User user = new User();
        user.setName(userRegRequest.name());
        user.setEmail(userRegRequest.email());
        user.setCpf(userRegRequest.cpf());
        user.setPassword(new BCryptPasswordEncoder().encode(userRegRequest.password()));
        user.setRole(userRegRequest.role());

        return user;
    }

    public UserResponse getUser(String userEmail) {
        User user = userRepository.findById(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        System.out.println("Meu usuario lindo: " + user);
        return userToUserResponse(user);
    }

    private UserResponse userToUserResponse(User user) {
        return new UserResponse(
                user.getName(),
                user.getEmail()
        );
    }

    public User updateUserData(UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(updateUserRequest.email())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setName(updateUserRequest.name());

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
