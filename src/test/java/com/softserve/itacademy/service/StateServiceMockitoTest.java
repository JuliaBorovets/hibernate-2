package com.softserve.itacademy.service;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.impl.StateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StateServiceMockitoTest {

    @Mock
    StateRepository stateRepository;

    @InjectMocks
    StateServiceImpl stateService;

    State state;

    @BeforeEach
    void setUp() {
        state = new State();
        state.setId(1L);
        state.setName("State");
        state.setTasks(Arrays.asList(new Task(), new Task()));
    }

    @Test
    void shouldCreateState() {
        when(stateRepository.save(any(State.class))).thenReturn(state);

        State actualState = stateService.create(state);

        verify(stateRepository).save(any(State.class));

        Assertions.assertEquals(state, actualState);
    }

    @Test
    public void shouldReturnAllStates() {
        List<State> expected = Arrays.asList(new State(), new State());

        when(stateRepository.findAll()).thenReturn(expected);

        List<State> actual = stateService.getAll();

        assertEquals(expected, actual);
        verify(stateRepository).findAll();
    }

    @Test
    void shouldSortAllStatesByName() {

        State state1 = new State();
        state1.setName("Ccc");

        State state2 = new State();
        state2.setName("Aaa");

        State state3 = new State();
        state3.setName("Bbb");

        List<State> expected = Arrays.asList(state2, state3, state1);

        given(stateRepository.findByOrderByNameAsc()).willReturn(expected);

        List<State> actual = stateService.getSortAsc();

        verify(stateRepository).findByOrderByNameAsc();
        assertEquals(expected, actual);

    }
}
