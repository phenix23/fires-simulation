package org.training.firessimulation.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Simulation {
    private Cell[][] grid;
    private int step;
    private boolean active;
}
