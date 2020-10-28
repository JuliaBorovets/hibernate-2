package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.RoleRepository;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    private static final User user = new User();

    @BeforeEach
    void createUser() {
        Role testRole = new Role();
        testRole.setName("ADMIN");
        given(roleRepository.getOne(1L)).willReturn(testRole);

        user.setId(1L);
        user.setEmail("mike@mail.com");
        user.setFirstName("Mike");
        user.setLastName("Brown");
        user.setPassword("$2a$10$CdEJ2PKXgUCIwU4pDQWICuiPjxb1lysoX7jrN.Y4MTMoY9pjfPALO");
        user.setRole(roleRepository.getOne(1L));
    }


    @Test
    void shouldCreateUserSuccessfully() {
        given(userRepository.save(user)).willAnswer(invocation -> invocation.getArgument(0));
        final User savedUser = userService.create(user);
        assertNotNull(savedUser);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldReturnUserById() {
        long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        final User userById = userService.readById(userId);
        assertSame(user, userById);
        verify(userRepository).findById(any(Long.class));

    }

    @Test
    void shouldThrowExceptionIfUserNotFound(){
        when(userRepository.findById(anyLong())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () ->  userService.readById(1L));

    }

    @Test
    void shouldReturnUserByEmail() {
        given(userRepository.findByEmail("mike@mail.com")).willReturn(user);
        final User userByEmail = userService.readByEmail("mike@mail.com");
        assertSame(user, userByEmail);
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    void shouldReturnUpdatedUser() {
        given(userRepository.save(user)).willReturn(user);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        final User update = userService.update(user);
        assertNotNull(update);
    }

    @Test
    void shouldReturnAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user);
        users.add(user);
        given(userRepository.findAll()).willReturn(users);
        final List<User> actualUsers = userService.getAll();
        assertSame(users, actualUsers);
    }

    @Test
    void shouldDeleteUserById() {
        long userId = 1L;
        userService.delete(userId);
        userService.delete(userId);

        verify(userRepository, times(2)).deleteById(userId);
    }

}