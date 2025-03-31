package org.training.firessimulation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.training.firessimulation.model.Position;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "forest.fire")
public class ForestFireConfig {
    private int height;
    private int width;
    private double propagationProbability;
    private List<Position> initialFires;
}
