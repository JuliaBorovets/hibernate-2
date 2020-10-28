package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.ToDoService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService {

    private ToDoRepository toDoRepository;

    public ToDoServiceImpl(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Override
    public ToDo create(ToDo todo) {
        return toDoRepository.save(todo);
    }

    @Override
    public ToDo readById(long id) {
        return toDoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No ToDo with id=" + id));
    }

    @Override
    public ToDo update(ToDo todo) {
        ToDo toDoUpdated = readById(todo.getId());

        Optional.ofNullable(todo.getTitle()).ifPresent(toDoUpdated::setTitle);
        Optional.ofNullable(todo.getOwner()).ifPresent(toDoUpdated::setOwner);
        Optional.ofNullable(todo.getTasks()).ifPresent(toDoUpdated::setTasks);
        Optional.ofNullable(todo.getCollaborators()).ifPresent(toDoUpdated::setCollaborators);
        todo.setCreatedAt(LocalDateTime.now());

        return toDoRepository.save(toDoUpdated);
    }

    @Override
    public void delete(long id) {
        toDoRepository.delete(readById(id));
    }

    @Override
    public List<ToDo> getAll() {
        return toDoRepository.findAll();
    }

    @Override
    public List<ToDo> getByUserId(long userId) {
        return toDoRepository.findTodoByUserId(userId);
    }
}
