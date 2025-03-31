package org.training.firessimulation.service;

import org.training.firessimulation.config.ForestFireConfig;
import org.training.firessimulation.model.Position;
import org.training.firessimulation.model.Simulation;
import org.training.firessimulation.model.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ForestFireServiceTest {

    private ForestFireService service;
    private ForestFireConfig config;

    @BeforeEach
    void setUp() {
        config = Mockito.mock(ForestFireConfig.class);
        when(config.getHeight()).thenReturn(5);
        when(config.getWidth()).thenReturn(5);
        when(config.getPropagationProbability()).thenReturn(0.5);
        when(config.getInitialFires()).thenReturn(List.of(new Position(2,2)));

        service = new ForestFireService(config);
    }

    @Test
    void testInitialization() {
        Simulation simulation = service.getCurrentState();
        assertNotNull(simulation);
        assertEquals(0, simulation.getStep());
        assertTrue(simulation.isActive());

        Cell[][] grid = simulation.getGrid();
        assertEquals(5, grid.length);
        assertEquals(5, grid[0].length);

        // Vérifier que seule la case initiale est en feu
        assertEquals(Cell.BURNING, grid[2][2]);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i != 2 || j != 2) {
                    assertEquals(Cell.TREE, grid[i][j]);
                }
            }
        }
    }

    @Test
    void testNextStepWithNoPropagation() {
        // Forcer la probabilité à 0 pour éviter toute propagation
        when(config.getPropagationProbability()).thenReturn(0.0);
        service = new ForestFireService(config);

        Simulation simulation = service.nextStep();

        assertEquals(1, simulation.getStep());
        assertFalse(simulation.isActive()); // Le feu devrait s'éteindre

        Cell[][] grid = simulation.getGrid();
        assertEquals(Cell.ASH, grid[2][2]);

        // Vérifier qu'aucun voisin n'a pris feu
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                if (i == 2 && j == 2) continue;
                assertEquals(Cell.TREE, grid[i][j]);
            }
        }
    }

    @Test
    void testNextStepWithPropagation() {
        // Forcer la probabilité à 1 pour garantir la propagation
        when(config.getPropagationProbability()).thenReturn(1.0);
        service = new ForestFireService(config);

        Simulation simulation = service.nextStep();

        assertEquals(1, simulation.getStep());
        assertTrue(simulation.isActive()); // Le feu devrait se propager

        Cell[][] grid = simulation.getGrid();
        assertEquals(Cell.ASH, grid[2][2]);

        // Vérifier que les 4 voisins ont pris feu
        assertEquals(Cell.BURNING, grid[1][2]);
        assertEquals(Cell.BURNING, grid[3][2]);
        assertEquals(Cell.BURNING, grid[2][1]);
        assertEquals(Cell.BURNING, grid[2][3]);
    }

    @Test
    void testSimulationEndsWhenNoMoreFire() {
        // Forcer la probabilité à 0 pour éviter toute propagation
        when(config.getPropagationProbability()).thenReturn(0.0);
        service = new ForestFireService(config);

        Simulation simulation = service.nextStep();
        assertFalse(simulation.isActive());

        // Essayer une autre étape
        simulation = service.nextStep();
        assertFalse(simulation.isActive());
        assertEquals(1, simulation.getStep());
    }

    @Test
    void testReset() {
        service.nextStep();
        service.nextStep();

        service.reset();
        Simulation simulation = service.getCurrentState();

        assertEquals(0, simulation.getStep());
        assertTrue(simulation.isActive());
        assertEquals(Cell.BURNING, simulation.getGrid()[2][2]);
    }
}