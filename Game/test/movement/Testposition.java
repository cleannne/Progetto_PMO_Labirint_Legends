package Game.test.movement;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import project.resources.model.movement.Position;

public class Testposition {

    private Position pos;

    @Before
    public void setUp() {
        pos = new Position(5, 12);
    }

    @Test
    public void testposition() {
        assertEquals(5, pos.x());
        assertEquals(12, pos.y());
    }
}
