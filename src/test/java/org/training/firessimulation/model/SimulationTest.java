package org.training.firessimulation.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void testSimulationConstructorAndGetters() {
        Cell[][] grid = new Cell[2][2];
        grid[0][0] = Cell.TREE;
        grid[0][1] = Cell.BURNING;
        grid[1][0] = Cell.ASH;
        grid[1][1] = Cell.TREE;

        Simulation simulation = new Simulation();
        simulation.setGrid(grid);
        simulation.setStep(5);
        simulation.setActive(true);

        assertArrayEquals(grid, simulation.getGrid());
        assertEquals(5, simulation.getStep());
        assertTrue(simulation.isActive());
    }

    @Test
    void testEqualsAndHashCode() {
        Cell[][] grid1 = new Cell[2][2];
        grid1[0][0] = Cell.TREE;
        grid1[0][1] = Cell.BURNING;

        Cell[][] grid2 = new Cell[2][2];
        grid2[0][0] = Cell.TREE;
        grid2[0][1] = Cell.BURNING;

        Simulation sim1 = new Simulation();
        sim1.setGrid(grid1);
        sim1.setStep(1);
        sim1.setActive(true);

        Simulation sim2 = new Simulation();
        sim2.setGrid(grid2);
        sim2.setStep(1);
        sim2.setActive(true);

        Simulation sim3 = new Simulation();
        sim3.setGrid(grid1);
        sim3.setStep(2);
        sim3.setActive(false);

        assertEquals(sim1, sim2);
        assertNotEquals(sim1, sim3);
        assertEquals(sim1.hashCode(), sim2.hashCode());
        assertNotEquals(sim1.hashCode(), sim3.hashCode());
    }
}