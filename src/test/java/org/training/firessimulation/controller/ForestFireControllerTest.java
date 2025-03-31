package org.training.firessimulation.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.training.firessimulation.model.Simulation;
import org.training.firessimulation.service.ForestFireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ForestFireControllerTest {

    @Mock
    private ForestFireService service;

    @InjectMocks
    private ForestFireController controller;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testGetCurrentState() {
        Simulation expectedSimulation = new Simulation();
        when(service.getCurrentState()).thenReturn(expectedSimulation);

        Simulation result = controller.getCurrentState();

        assertSame(expectedSimulation, result);
        verify(service, times(1)).getCurrentState();
    }

    @Test
    void testNextStep() {
        Simulation expectedSimulation = new Simulation();
        when(service.nextStep()).thenReturn(expectedSimulation);

        Simulation result = controller.nextStep();

        assertSame(expectedSimulation, result);
        verify(service, times(1)).nextStep();
    }

    @Test
    void testReset() {
        Simulation expectedSimulation = new Simulation();
        when(service.getCurrentState()).thenReturn(expectedSimulation);

        Simulation result = controller.reset();

        assertSame(expectedSimulation, result);
        verify(service, times(1)).reset();
        verify(service, times(1)).getCurrentState();
    }
}