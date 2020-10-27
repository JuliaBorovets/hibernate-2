package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User readById(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User with id " + id + " not found"));

    }

    @Override
    public User readByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User update(User user) {
        final User updateUser = readById(user.getId());

        Optional.ofNullable(user.getFirstName()).ifPresent(updateUser::setFirstName);
        Optional.ofNullable(user.getLastName()).ifPresent(updateUser::setLastName);
        Optional.ofNullable(user.getPassword()).ifPresent(updateUser::setPassword);
        Optional.ofNullable(user.getEmail()).ifPresent(updateUser::setEmail);

        return userRepository.save(updateUser);

    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        final List<User> all = userRepository.findAll();
        return all.isEmpty() ? Collections.emptyList() : all;
    }
}
