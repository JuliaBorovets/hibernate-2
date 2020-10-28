package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {

    @Mock
    ToDoRepository toDoRepository;

    @InjectMocks
    ToDoServiceImpl service;

    ToDo expectedToDo;

    @BeforeEach
    void setUp() {
        expectedToDo = new ToDo();
        expectedToDo.setTitle("Mike's To-Do #1");
        expectedToDo.setId(7L);
    }

    @Test
    void shouldCreateToDoAndReturn() {
        ToDo toDo = new ToDo();
        toDo.setId(1L);

        when(toDoRepository.save(any(ToDo.class))).thenReturn(toDo);

        ToDo actualToDo = service.create(toDo);

        verify(toDoRepository).save(any(ToDo.class));
        Assertions.assertEquals(toDo.getId(), actualToDo.getId());
    }

    @Test
    public void shouldReturnToDoById() {
        when(toDoRepository.findById(anyLong())).thenReturn(Optional.of(expectedToDo));

        ToDo actual = service.readById(7L);

        assertEquals(expectedToDo.getTitle(), actual.getTitle());
        assertEquals(expectedToDo.getId(), actual.getId());
        verify(toDoRepository).findById(anyLong());
    }

    @Test
    public void shouldThrowExceptionCanNotFind() {

        when(toDoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.readById(7L);
        });

        verify(toDoRepository).findById(anyLong());
    }

    @Test
    void shouldDeleteToDoById() {

        when(toDoRepository.findById(anyLong())).thenReturn(Optional.of(expectedToDo));

        service.delete(expectedToDo.getId());

        verify(toDoRepository).delete(any(ToDo.class));
        verify(toDoRepository).findById(anyLong());
    }

    @Test
    void shouldReturnAllToDo() {
        List<ToDo> expected = Arrays.asList(new ToDo(), new ToDo());

        when(toDoRepository.findAll()).thenReturn(expected);

        List<ToDo> actual = service.getAll();

        assertEquals(expected, actual);
        verify(toDoRepository).findAll();
    }

    @Test
    void shouldUpdateToDo() {

        ToDo toDo = new ToDo();
        toDo.setId(1L);

        when(toDoRepository.findById(anyLong())).thenReturn(Optional.of(toDo));
        given(toDoRepository.save(any(ToDo.class))).willReturn(expectedToDo);

        ToDo actual = service.update(toDo);

        assertNotNull(actual);
        verify(toDoRepository).findById(anyLong());
        verify(toDoRepository).save(any(ToDo.class));
    }

    @Test
    void shouldReturnToDoByOwnerIdOrCollaboratorId() {

        List<ToDo> expected = Arrays.asList(new ToDo(), new ToDo());

        when(toDoRepository.findTodoByUserId(anyLong())).thenReturn(expected);

        List<ToDo> actual = service.getByUserId(1L);

        verify(toDoRepository).findTodoByUserId(anyLong());
        assertEquals(expected, actual);
    }
}
