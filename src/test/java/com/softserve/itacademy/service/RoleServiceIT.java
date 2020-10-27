package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.UserRepository;
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
public class RoleServiceIT {

    RoleService roleService;

    UserRepository userRepository;

    @Autowired
    public RoleServiceIT(RoleService roleService, UserRepository userRepository) {
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @Transactional
    @Test
    public void getAllTest(){
        int expectedSize = 2;
        List<Role> roles = roleService.getAll();
        assertEquals(expectedSize, roles.size());
    }

    @Test
    @Transactional
    public void createRoleTest(){
        Role role = new Role();
        role.setName("Role3");
        role = roleService.create(role);
        assertNotEquals(0, role.getId());
    }

    @Transactional
    @Test
    public void readById(){
        Role expected = new Role();
        expected.setName("ADMIN");
        long expectedId = 1L;

        Role actual = roleService.readById(expectedId);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expectedId, actual.getId());
    }

    @Test
    @Transactional
    public void updateTest(){
        String newName = "ADMIN2";

        Role role = roleService.readById(1L);
        role.setName(newName);

        Role actual = roleService.update(role);

        Role expected = new Role();
        expected.setName(newName);
        long expectedId = 1L;

        assertEquals(expectedId, actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertNotNull(actual.getUsers());
    }

    @Test
    @Transactional
    public void deleteExceptionTest(){

        long roleId = 1L;

        assertThrows(RuntimeException.class, () -> {
            roleService.delete(roleId);
        });

        List<Role> roles = roleService.getAll();
        assertTrue(roles.contains(roleService.readById(roleId)));
    }


    @Test
    @Transactional
    public void deleteTest(){

        Role role = new Role();
        role.setName("Role3");
        role = roleService.create(role);

        roleService.delete(roleService.readById(role.getId()).getId());

        List<Role> roles = roleService.getAll();
        assertEquals(2, roles.size());
    }

}
