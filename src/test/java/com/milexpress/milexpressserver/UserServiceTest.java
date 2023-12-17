package com.milexpress.milexpressserver;

import com.milexpress.milexpressserver.model.db.User;
import com.milexpress.milexpressserver.model.db.UserRole;
import com.milexpress.milexpressserver.model.request.UserRequest;
import com.milexpress.milexpressserver.model.response.UserResponse;
import com.milexpress.milexpressserver.repository.UserRepository;
import com.milexpress.milexpressserver.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserRequest userRequest;
    private User user;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest("marcelo@eler.com", "123", "Marcelo Eler", "123456789", UserRole.USER);
        user = new User("marcelo@eler.com", "123", "Marcelo Eler", "123456789", UserRole.USER, null);
    }

    @Test
    void registerUser_whenUserNotExists_shouldRegisterUser() {
        when(userRepository.findById(userRequest.email())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser(userRequest);

        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_whenUserExists_shouldThrowException() {
        when(userRepository.findById(userRequest.email())).thenReturn(Optional.of(user));

        assertThrows(ResponseStatusException.class, () -> userService.registerUser(userRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUser_whenUserExists_shouldReturnUser() {
        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));

        UserResponse result = userService.getUser(user.getEmail());

        assertEquals(user.getEmail(), result.email());
        verify(userRepository, times(1)).findById(user.getEmail());
    }

    @Test
    void getUser_whenUserNotExists_shouldThrowEntityNotFoundException() {
        when(userRepository.findById("naoexiste@exemplo.com")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUser("naoexiste@exemplo.com"));
    }
}
