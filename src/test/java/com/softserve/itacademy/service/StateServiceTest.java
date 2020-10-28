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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StateServiceTest {

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

    @Test
    void shouldReturnStateById(){
        State state = new State();
        state.setId(1L);
        state.setName("State");
        given(stateRepository.findById(anyLong())).willReturn(Optional.of(state));

        final State actual = stateService.readById(1L);

        assertSame(state,actual);

        verify(stateRepository).findById(anyLong());

    }

    @Test
    void shouldUpdateState(){
        State state1 = new State();
        state1.setName("Ccc");
        state1.setId(1L);

        given(stateRepository.save(state1)).willReturn(state1);
        given(stateRepository.findById(anyLong())).willReturn(Optional.of(state1));

        final State update = stateService.update(state1);

        assertNotNull(update);

        verify(stateRepository).save(any(State.class));

    }

    @Test
    void shouldReturnStateByName(){
        State state1 = new State();
        state1.setName("Ccc");

        given(stateRepository.findByName(state1.getName())).willReturn(state1);

        final State actual = stateService.getByName("Ccc");

        assertSame(state1,actual);

        verify(stateRepository).findByName(anyString());
    }

    @Test
    void shouldDeleteState(){
        stateService.delete(1L);
        stateService.delete(1L);

        verify(stateRepository, times(2)).deleteById(1L);



    }
}
