package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task readById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with id: " + id + " doesn't exist"));
    }

    @Override
    public Task update(Task task) {
        final Task updatedTask = readById(task.getId());
        Optional.ofNullable(task.getName()).ifPresent(updatedTask::setName);
        Optional.ofNullable(task.getPriority()).ifPresent(updatedTask::setPriority);
        Optional.ofNullable(task.getState()).ifPresent(updatedTask::setState);
        return taskRepository.save(updatedTask);
    }

    @Override
    public void delete(long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getAll() {
        final List<Task> all = taskRepository.findAll();
        return all.isEmpty() ? Collections.emptyList() : all;
    }

    @Override
    public List<Task> getByTodoId(long todoId) {
        return taskRepository.findAllByTodoId(todoId);
    }
}
