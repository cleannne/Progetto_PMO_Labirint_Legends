package Game.test.movement;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import project.resources.model.elements.Element;
import project.resources.model.elements.aids.ImmunitySpell;
import project.resources.model.enums.MovementRules;
import project.resources.model.movement.IMovement;
import project.resources.model.movement.Movement;
import project.resources.model.movement.Position;

public class TestMovement {

    private IMovement movement;

    @Before
    public void setUp() {
        movement = new Movement(new Position(4, 4));
    }

    @Test
    public void testmovementcost() { // test costruttore
        assertEquals(0, movement.getNMovement());
        assertEquals(4, movement.getPos().x());
        assertEquals(4, movement.getPos().y());
    }

    // il personaggio parte dal lato destro infariore alla mappa .... . .
    // ...0 0 1
    // ...0 P<-- personaggio
    // ...1 1 1

    @Test
    public void testmovementup() { // test movimento up e del metodo getNMovement()
        final boolean result = movement.move(MovementRules.MOVING_FORWARD, false);
        assertEquals(3, movement.getPos().x());
        assertEquals(-1, movement.getNMovement());
        assertEquals(result, true);
    }
    // il personaggio parte dal lato destro inferiore della mappa .... . .
    // ...0 P<-- personaggio
    // ...0 0 1
    // ...1 1 1

    @Test
    public void testmovementdown() { // test movimento down e del metodo getNMovement()
        final boolean result = movement.move(MovementRules.MOVING_DOWN, false);
        assertEquals(5, movement.getPos().x());
        assertEquals(-1, movement.getNMovement());
        assertEquals(result, true);
    }
    // il personaggio parte dal lato destro inferiore della mappa .... . .
    // ...0 0 1
    // ...0 P<-- personaggio
    // ...1 1 1

    @Test
    public void testmovementleft() { // test movimento left e del metodo getNMovement()
        final boolean result = movement.move(MovementRules.MOVING_LEFT, false);
        assertEquals(3, movement.getPos().y());
        assertEquals(-1, movement.getNMovement());
        assertEquals(result, true);
    }
    // il personaggio parte dal lato destro inferiore della mappa .... . .
    // ...0 0 1
    // ...P<-- personaggio
    // ...1 1 1

    @Test
    public void testmovementright() { // test movimento right e del metodo getNMovement()
        final boolean result = movement.move(MovementRules.MOVING_RIGHT, false);
        assertEquals(5, movement.getPos().y());
        assertEquals(-1, movement.getNMovement());
        assertEquals(result, true);
    }
    // il personaggio parte dal lato destro inferiore della mappa .... . .
    // ...0 0 1
    // ...0 P<-- personaggio
    // ...1 1 1

    // test per verificare se il personaggio va contro un muro restituisce l'errore
    @Test
    public void testmovementexcup() { // test se movimento up genera l'eccezione
        final boolean result = movement.move(MovementRules.MOVING_FORWARD, true);
        assertEquals(result, false);
    }

    @Test
    public void testmovementexcdown() { // test se movimento down genera l'eccezione
        final boolean result = movement.move(MovementRules.MOVING_DOWN, true);
        assertEquals(result, false);
    }

    @Test
    public void testmovementexcleft() { // test se movimento left genera l'eccezione
        final boolean result = movement.move(MovementRules.MOVING_LEFT, true);
        assertEquals(result, false);
    }

    @Test
    public void testmovementexcright() { // test se movimento right genera l'eccezione
        final boolean result = movement.move(MovementRules.MOVING_RIGHT, true);
        assertEquals(result, false);
    }

    @Test
    public void testmovementreset() { // test metodo reset invocato solo da immunityspell
        final Element imm = new ImmunitySpell();
        movement.resetNMovement(imm, 3);
        assertEquals(3, movement.getNMovement());
        movement.resetNMovement(null, 6);
        assertEquals(3, movement.getNMovement());
    }
}