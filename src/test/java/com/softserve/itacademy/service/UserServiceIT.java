package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class UserServiceIT {

    private UserService userService;

    private RoleRepository roleRepository;

    @Autowired
    public UserServiceIT(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Test
    @Transactional
    void shouldCreateUser(){
        User user = new User();
        user.setId(22L);
        user.setEmail("mike@mail.com");
        user.setFirstName("Mike");
        user.setLastName("Brown");
        user.setPassword("$2a$10$CdEJ2PKXgUCIwU4pDQWICuiPjxb1lysoX7jrN.Y4MTMoY9pjfPALO");
        user.setRole(roleRepository.getOne(1L));

        final User user1 = userService.create(user);

        assertNotNull(user1);

    }

    @Test
    @Transactional
    void shouldUpdateUser(){

        User user = userService.readById(5L);

        String newName = "Updated";

        final User updatedUser = userService.readById(5L);
        updatedUser.setFirstName(newName);

        assertEquals(user.getFirstName(),updatedUser.getFirstName());


    }

    @Test
    @Transactional
    void shouldGetUserById(){
        final User user = userService.readById(5L);
        assertNotNull(user);
    }

    @Test
    @Transactional
    void shouldDeleteUser(){
        userService.delete(5L);
        assertThrows(RuntimeException.class, () -> userService.readById(5L));
    }

    @Test
    @Transactional
    void shouldReturnUserByEmail(){
        final User user = userService.readByEmail("nick@mail.com");
        assertNotNull(user);
    }

    @Test
    @Transactional
    void shouldReturnAllUsers(){

        final List<User> all = userService.getAll();
        assertNotNull(all);
    }






}
