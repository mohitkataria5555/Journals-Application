package com.mk.journalApp.service;

import com.mk.journalApp.Repository.UserRepository;
import com.mk.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@Disabled
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user.setId(new ObjectId("507f1f77bcf86cd799439011"));
        user.setUsername("testUser");
        user.setPassword("password123");
    }

    @Test
    void testSaveUser() {
        userService.saveUser(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testSaveNewUser() {
        userService.saveNewUser(user);

        assertTrue(passwordEncoder.matches("password123", user.getPassword()));
        assertEquals(List.of("USER"), user.getRoles());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetAll() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getAll();
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user.getUsername(), users.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findById(user.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void testDelete() {
        userService.delete(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void testFindByUserName() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        User foundUser = userService.findByUserName(user.getUsername());
        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    void testSaveAdmin() {
        userService.saveAdmin(user);

        assertTrue(passwordEncoder.matches("password123", user.getPassword()));
        assertEquals(List.of("USER", "ADMIN"), user.getRoles());
        verify(userRepository, times(1)).save(user);
    }
}
