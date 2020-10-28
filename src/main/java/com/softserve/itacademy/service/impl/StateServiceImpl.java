package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.StateService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {

    private StateRepository stateRepository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public State create(State state) {
        return stateRepository.save(state);
    }

    @Override
    public State readById(long id) {
        return stateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("State with id " + id + " doesn't exist"));
    }

    @Override
    public State update(State state) {
        final State updatedState = readById(state.getId());
        Optional.ofNullable(state.getName()).ifPresent(updatedState::setName);
        Optional.ofNullable(state.getTasks()).ifPresent(updatedState::setTasks);
        return stateRepository.save(updatedState);
    }

    @Override
    public void delete(long id) {
        stateRepository.deleteById(id);
    }

    @Override
    public List<State> getAll() {
        return stateRepository.findAll();
    }

    @Override
    public State getByName(String name) {
        return stateRepository.findByName(name);
    }

    @Override
    public List<State> getSortAsc() {
        return stateRepository.findByOrderByNameAsc();
    }
}
