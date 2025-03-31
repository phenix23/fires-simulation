package org.training.firessimulation.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.training.firessimulation.model.Position;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ForestFireConfigTest {

    @Test
    void testConfigProperties() {
        ForestFireConfig config = new ForestFireConfig();
        config.setHeight(10);
        config.setWidth(20);
        config.setPropagationProbability(0.3);
        config.setInitialFires(List.of(new Position(1,1), new Position(2,2)));

        assertEquals(10, config.getHeight());
        assertEquals(20, config.getWidth());
        assertEquals(0.3, config.getPropagationProbability());
        assertEquals(2, config.getInitialFires().size());
        assertEquals(new Position(1,1), config.getInitialFires().get(0));
        assertEquals(new Position(2,2), config.getInitialFires().get(1));
    }
}