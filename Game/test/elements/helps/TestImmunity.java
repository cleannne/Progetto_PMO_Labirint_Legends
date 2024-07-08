package Game.test.elements.helps;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import project.resources.model.characters.Linda;
import project.resources.model.characters.Player;
import project.resources.model.elements.Element;
import project.resources.model.elements.aids.ImmunitySpell;
import project.resources.model.movement.Position;

public class TestImmunity {

    private Player character;  
    private Element imm; 

    @Before
    public void setUp() {
        character = Linda.getLindaInstance(new Position(1, 1));
        imm = new ImmunitySpell();
    }

    @Test
    public void testimmunityeffecteff() {
        imm.effect(character);
        assertEquals(3, character.getMovement().getNMovement());
    }
}
