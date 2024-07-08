package Game.test.elements.obstacles;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import project.resources.model.characters.Linda;
import project.resources.model.characters.Player;
import project.resources.model.elements.Element;
import project.resources.model.elements.aids.ImmunitySpell;
import project.resources.model.elements.obstacles.Banana;
import project.resources.model.enums.MovementRules;
import project.resources.model.movement.Position;

public class TestBanana {

    private Player character; 
    private Element banana; 
    private Element imm; 

    @Before
    public void setUp() {
        character = Linda.getLindaInstance(new Position(1, 1));
        banana = new Banana();
        imm = new ImmunitySpell();
    }

    // test costruttore e effetto banana
    @Test
    public void testbananaeff() {
        character.getMovement().move(MovementRules.MOVING_LEFT, false);
        character.addCurrentPosition();
        character.getMovement().move(MovementRules.MOVING_LEFT, false); // si deve trovare in questa posizione
        character.addCurrentPosition();
        character.getMovement().move(MovementRules.MOVING_FORWARD, false);
        character.addCurrentPosition();
        character.getMovement().move(MovementRules.MOVING_RIGHT, false);
        character.addCurrentPosition();
        character.getMovement().move(MovementRules.MOVING_DOWN, false);
        character.addCurrentPosition();
        character.getMovement().move(MovementRules.MOVING_LEFT, false);
        character.addCurrentPosition();
        character.getMovement().move(MovementRules.MOVING_LEFT, false);
        character.addCurrentPosition();
        character.getMovement().resetNMovement(imm, -1);
        banana.effect(character);
        assertEquals(new Position(1, 0), character.getMovement().getPos());
        assertEquals(1, character.getList().size()); // +1 per il costruttore di character
    }
}