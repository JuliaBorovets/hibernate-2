package com.softserve.itacademy.service;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;
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
public class TaskServiceIT {

    private final TaskService taskService;

    @Autowired
    public TaskServiceIT(TaskService taskService) {
        this.taskService = taskService;
    }

    @Test
    @Transactional
    void shouldCreateTask(){
        Task task = new Task();
        task.setName("AAAA");

        final Task task1 = taskService.create(task);

        assertNotEquals(0,task1.getId());

    }

    @Test
    @Transactional
    void shouldUpdateTask(){
        String newName = "Updated";
        Task task = taskService.readById(5L);
        task.setName(newName);
        Task actual = taskService.update(task);
        Task expected = new Task();
        expected.setName(newName);
        expected.setId(5L);
        assertNotSame(expected, actual);
    }

    @Test
    @Transactional
    void shouldReturnTaskById(){
        final Task task = taskService.readById(5L);
        assertNotNull(task);
    }

    @Test
    @Transactional
    void shouldDeleteTask(){
        taskService.delete(5L);

        assertThrows(RuntimeException.class,() -> taskService.readById(5L));

    }

    @Test
    @Transactional
    void shouldReturnAllTask(){
        final List<Task> all = taskService.getAll();
        assertNotNull(all);
    }

    @Test
    @Transactional
    void shouldReturnTasksByToDoId(){
        final List<Task> byTodoId = taskService.getByTodoId(7L);
        assertNotNull(byTodoId);
    }
}
