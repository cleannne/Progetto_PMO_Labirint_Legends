package Game.test.elements.helps;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import project.resources.model.characters.Linda;
import project.resources.model.characters.Player;
import project.resources.model.elements.Element;
import project.resources.model.elements.aids.Box;
import project.resources.model.movement.Position;

public class TestBox {

    private Player character;  
    private Element box; 

    @Before
    public void setUp() {
        character = Linda.getLindaInstance(new Position(1, 1)); 
        box = new Box(5);
    }

    @Test
    public void testboxeff() {
        character.setCoins(5);
        box.effect(character);
        assertEquals(10, character.getCoins());
    }
}
