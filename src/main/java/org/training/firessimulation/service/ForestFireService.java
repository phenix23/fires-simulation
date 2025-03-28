package org.training.firessimulation.service;

import org.springframework.stereotype.Service;
import org.training.firessimulation.config.ForestFireConfig;
import org.training.firessimulation.model.Cell;
import org.training.firessimulation.model.Position;
import org.training.firessimulation.model.Simulation;

import java.util.Arrays;
import java.util.Random;

/**
 * Calcule la prochaine étape de la simulation
 * - Parcourt toutes les cellules en feu
 * - Les transforme en cendres
 * - Propage le feu aux voisins selon la probabilité
 * @return Nouvel état de la grille
 */

@Service
public class ForestFireService {
    private final ForestFireConfig config;
    private Simulation simulation;

    public ForestFireService(ForestFireConfig config) {
        this.config = config;
        initializeSimulation();
    }

    public void reset() {
        initializeSimulation();
    }

    public Simulation getCurrentState() {
        return simulation;
    }

    public Simulation nextStep() {
        if (!simulation.isActive()) {
            return simulation;
        }

        Cell[][] newGrid = Arrays.stream(simulation.getGrid())
                .map(Cell[]::clone)
                .toArray(Cell[][]::new);

        Random random = new Random();

        for (int y = 0; y < config.getHeight(); y++) {
            for (int x = 0; x < config.getWidth(); x++) {
                if (simulation.getGrid()[y][x] == Cell.BURNING) {
                    // Éteindre le feu actuel
                    newGrid[y][x] = Cell.ASH;

                    // Propager le feu aux voisins
                    propagateFire(newGrid, x, y, random);
                }
            }
        }

        // Vérifier s'il reste des cases en feu
        simulation.setActive(Arrays.stream(newGrid)
                .flatMap(Arrays::stream)
                .anyMatch(cell -> cell == Cell.BURNING));

        simulation.setGrid(newGrid);
        simulation.setStep(simulation.getStep() + 1);

        return simulation;
    }

    private void initializeSimulation() {
        Cell[][] grid = new Cell[config.getHeight()][config.getWidth()];

        // Initialiser toutes les cases comme des arbres
        for (int y = 0; y < config.getHeight();y++) {
            Arrays.fill(grid[y], Cell.TREE);
        }

        // Mettre le feu aux cases initiales
        for (Position position : config.getInitialFires()) {
            grid[position.getX()][position.getY()] = Cell.BURNING;
        }

        this.simulation = new Simulation(grid, 0, true);
    }

    private void propagateFire(Cell[][] grid, int x, int y, Random random) {
        // Propagation potentielle aux cases adjacentes
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (isValidCell(newX, newY) && grid[newY][newX] == Cell.TREE) {
                if (random.nextDouble() < config.getPropagationProbability()) {
                    grid[newY][newX] = Cell.BURNING;
                }
            }
        }
    }

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < config.getWidth() && y >= 0 && y < config.getHeight();
    }
}
