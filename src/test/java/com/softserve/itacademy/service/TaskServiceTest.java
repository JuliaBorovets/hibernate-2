package com.softserve.itacademy.service;

import com.softserve.itacademy.model.*;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeAll;
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
public class TaskServiceTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    private static final Task task = new Task();

    @BeforeEach
    void createTestTask(){
        task.setId(1L);
        task.setName("Do staff");
        task.setPriority(Priority.LOW);
        task.setState(new State());
        task.setTodo(new ToDo());
    }


    @Test
    void createTaskTest(){
        given(taskRepository.save(task)).willReturn(task);

        final Task actualTask = taskService.create(task);

        assertSame(task, actualTask);

        verify(taskRepository).save(any(Task.class));

    }

    @Test
    void shouldReturnTaskById(){
        given(taskRepository.findById(1L)).willReturn(Optional.of(task));

        final Task actualTask = taskService.readById(1L);

        assertSame(task,actualTask);

        verify(taskRepository).findById(anyLong());
    }

    @Test
    void shouldReturnUpdatedTask(){
        given(taskRepository.save(task)).willReturn(task);
        given(taskRepository.findById(anyLong())).willReturn(Optional.of(task));
        final Task update = taskService.update(task);
        assertNotNull(update);

        verify(taskRepository).findById(anyLong());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void shouldDeleteTask(){

        taskService.delete(1L);
        taskService.delete(1L);

        verify(taskRepository, times(2)).deleteById(1L);
    }

    @Test
    void shouldReturnAllTasks(){
        List<Task> allTasks = new ArrayList<>();
        allTasks.add(task);
        allTasks.add(task);
        allTasks.add(task);

        given(taskRepository.findAll()).willReturn(allTasks);

        final List<Task> all = taskService.getAll();
        assertSame(all,allTasks);

    }

    @Test
    void shouldReturnTasksByToDoId(){
        List<Task> allTasks = new ArrayList<>();
        allTasks.add(task);
        allTasks.add(task);
        allTasks.add(task);

        given(taskRepository.findAllByTodoId(1L)).willReturn(allTasks);

        final List<Task> byTodoId = taskService.getByTodoId(1L);

        assertNotNull(byTodoId);



    }

}
