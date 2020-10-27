package com.softserve.itacademy.service;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceMockitoTest {

    @Mock
    ToDoRepository toDoRepository;

    @InjectMocks
    ToDoServiceImpl service;

    @Test
    void createTest() {
        ToDo toDo = new ToDo();
        toDo.setId(1L);

        when(toDoRepository.save(any(ToDo.class))).thenReturn(toDo);

        ToDo actualToDo = service.create(toDo);

        verify(toDoRepository).save(any(ToDo.class));
        Assertions.assertEquals(toDo.getId(), actualToDo.getId());
    }

    @Test
    public void readById() {
        ToDo expected = new ToDo();
        expected.setTitle("Mike's To-Do #1");
        expected.setId(7L);

        when(toDoRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        ToDo actual = service.readById(7L);

        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getId(), actual.getId());
        verify(toDoRepository).findById(anyLong());
    }

    @Test
    public void readByIdException() {

        when(toDoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.readById(7L);
        });

        verify(toDoRepository).findById(anyLong());
    }

    @Test
    void deleteTest() {

        ToDo expected = new ToDo();
        expected.setTitle("Mike's To-Do #1");
        expected.setId(7L);

        when(toDoRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        service.delete(expected.getId());

        verify(toDoRepository).delete(any(ToDo.class));
        verify(toDoRepository).findById(anyLong());
    }

    @Test
    void getAllTest() {
        List<ToDo> expected = Arrays.asList(new ToDo(), new ToDo());

        when(toDoRepository.findAll()).thenReturn(expected);

        List<ToDo> actual = service.getAll();

        assertEquals(expected, actual);
        verify(toDoRepository).findAll();
    }

}
