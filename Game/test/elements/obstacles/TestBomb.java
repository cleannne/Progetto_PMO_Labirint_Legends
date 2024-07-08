package Game.test.elements.obstacles;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import project.resources.model.characters.Linda;
import project.resources.model.characters.Player;
import project.resources.model.elements.Element;
import project.resources.model.elements.aids.ImmunitySpell;
import project.resources.model.elements.obstacles.Bomb;
import project.resources.model.movement.Position;

public class TestBomb { // test per la classe Bomba

    private Player character; 
    private Element bomb;
    private Element imm;

    @Before
    public void setUp() {
        character = Linda.getLindaInstance(new Position(1, 1));
        bomb = new Bomb(4);
        imm = new ImmunitySpell();
    }

    @Test
    public void testbombeff() {
        character.setCoins(5);
        character.getMovement().resetNMovement(imm, -1);
        bomb.effect(character);
        assertEquals(1, character.getCoins());
    }
}
