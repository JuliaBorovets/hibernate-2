package com.softserve.itacademy.service;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
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
public class ToDoServiceTests {

    ToDoService toDoService;

    UserRepository userRepository;

    @Autowired
    public ToDoServiceTests(ToDoService toDoService, UserRepository userRepository) {
        this.toDoService = toDoService;
        this.userRepository = userRepository;
    }

    @Test
    public void getAllToDosByUserIdTest(){
        int expectedSize = 4;
        List<ToDo> toDos = toDoService.getByUserId(5L);
        assertEquals(expectedSize, toDos.size());
    }

    @Test
    @Transactional
    public void createToDoTest(){
        ToDo toDo = new ToDo();
        toDo.setTitle("Title-3");
        toDo = toDoService.create(toDo);
        assertNotEquals(0, toDo.getId());
    }

    @Test
    public void readById(){
        ToDo expected = new ToDo();
        expected.setTitle("Mike's To-Do #1");
        expected.setId(7L);

        ToDo actual = toDoService.readById(7L);

        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    @Transactional
    public void updateToDoTest(){
        String newTitle = "New 2";

        ToDo toDo = toDoService.readById(7L);
        toDo.setTitle(newTitle);

        ToDo actual = toDoService.update(toDo);

        ToDo expected = new ToDo();
        expected.setTitle(newTitle);
        expected.setId(7L);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertNotNull(actual.getOwner());
    }

    @Test
    @Transactional
    public void updateToDoTestOwner(){
        String newTitle = "New 2";
        User expectedOwner = userRepository.findById(5L).get();

        ToDo toDo = toDoService.readById(7L);
        toDo.setTitle(newTitle);
        toDo.setOwner(expectedOwner);

        ToDo actual = toDoService.update(toDo);

        ToDo expected = new ToDo();
        expected.setTitle(newTitle);
        expected.setId(7L);
        expected.setOwner(expectedOwner);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getOwner(), actual.getOwner());
    }

    @Test
    @Transactional
    public void getAllToDoTest(){

        toDoService.delete(7L);
        toDoService.delete(8L);
        toDoService.delete(9L);
        toDoService.delete(10L);
        toDoService.delete(11L);
        toDoService.delete(12L);
        toDoService.delete(13L);
        toDoService.delete(20L);
        toDoService.delete(22L);
        int expectedSize = 0;
        List<ToDo> toDoList = toDoService.getAll();
        assertEquals(expectedSize, toDoList.size());
    }


}
