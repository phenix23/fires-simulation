package org.training.firessimulation.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "forest.fire")
public class ForestFireConfig {
    private int height;
    private int width;
    private double propagationProbability;
    private List<int[]> initialFires;
}
