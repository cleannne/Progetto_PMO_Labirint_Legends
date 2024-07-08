package Game.test.elements.obstacles;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import project.resources.model.characters.Linda;
import project.resources.model.characters.Player;
import project.resources.model.elements.Element;
import project.resources.model.elements.aids.ImmunitySpell;
import project.resources.model.elements.obstacles.Npc;
import project.resources.model.movement.Position;

public class TestNpc { // test per la classe Npc

    private Player character; 
    private Element npc;
    private Element imm;

    @Before
    public void setUp() {
        character = Linda.getLindaInstance(new Position(0, 0));
        npc = new Npc();
        imm = new ImmunitySpell();
    }

    @Test
    public void testnpceff() {    
        character.getMovement().resetNMovement(imm, -1);
        npc.effect(character);
        assertTrue(character.isDead());
    }
}
