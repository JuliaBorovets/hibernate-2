package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.RoleRepository;
import com.softserve.itacademy.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleServiceImpl service;

    Role role;

    Role expectedRole;

    @BeforeEach
    void createUser() {
        role = new Role();
        role.setId(1L);

        expectedRole = new Role();
        expectedRole.setName("Role Name");
        expectedRole.setId(7L);
    }

    @Test
    public void shouldCreateRoleAndReturn() {

        when(roleRepository.save(any(Role.class))).thenReturn(role);

        Role actualRole = service.create(role);

        verify(roleRepository).save(any(Role.class));
        Assertions.assertEquals(role.getId(), actualRole.getId());
    }

    @Test
    public void shouldReturnRoleById() {

        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(expectedRole));

        Role actual = service.readById(1L);

        assertEquals(expectedRole.getName(), actual.getName());
        assertEquals(expectedRole.getId(), actual.getId());
        verify(roleRepository).findById(anyLong());
    }

    @Test
    public void shouldThrowExceptionIfRoleIsNotFound() {

        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.readById(1L);
        });

        verify(roleRepository).findById(anyLong());
    }

    @Test
    public void shouldReturnUpdatedRole() {

        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        given(roleRepository.save(any(Role.class))).willReturn(expectedRole);

        Role actual = service.update(role);
        assertNotNull(actual);
    }

    @Test
    public void shouldDeleteRoleById() {

        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(expectedRole));

        service.delete(expectedRole.getId());

        verify(roleRepository).delete(any(Role.class));
        verify(roleRepository).findById(anyLong());
    }

    @Test
    public void shouldThrowExceptionCanNorDeleteRoleById() {

        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(expectedRole));

        expectedRole.getUsers().add(new User());

        assertThrows(RuntimeException.class, () -> {
            service.delete(expectedRole.getId());
        });

        verify(roleRepository, times(0)).delete(any(Role.class));
        verify(roleRepository).findById(anyLong());
    }

    @Test
    public void shouldReturnAllRoles() {
        List<Role> expected = Arrays.asList(new Role(), new Role());

        when(roleRepository.findAll()).thenReturn(expected);

        List<Role> actual = service.getAll();

        assertEquals(expected, actual);
        verify(roleRepository).findAll();
    }
}
