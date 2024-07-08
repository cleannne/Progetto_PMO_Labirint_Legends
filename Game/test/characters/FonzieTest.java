package Game.test.characters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import project.resources.model.characters.Fonzie;
import project.resources.model.characters.Player;
import project.resources.model.enums.MovementRules;
import project.resources.model.movement.Position;

public class FonzieTest {

    private Player test;

    @Before
    public void setUp() {
        test = Fonzie.getFonzieInstance(new Position(0, 0));
    }
    
    @Test
    public void testCost(){

        Assert.assertEquals(0, test.getCoins());
        Assert.assertEquals(1, test.getSpeed());
        Assert.assertEquals("Fonzie", test.getName());
    }

    @Test
    public void testSetCoin(){

        test.setCoins(5);
        Assert.assertEquals(5, test.getCoins());

    }

    @Test
    public void testDeath(){

        test.takeDamage();                      // fonzie ha 2 vite
        Assert.assertFalse(test.isDead());
        test.takeDamage();
        Assert.assertTrue(test.isDead());

    }

    @Test
    public void testPositions(){
    
        test.getMovement().move(MovementRules.MOVING_FORWARD, false);
        test.addCurrentPosition();
        test.getMovement().move(MovementRules.MOVING_LEFT, false);
        test.addCurrentPosition();
        test.getMovement().move(MovementRules.MOVING_FORWARD, false);
        test.addCurrentPosition();
        test.getMovement().move(MovementRules.MOVING_LEFT, false);
        test.addCurrentPosition();
        Assert.assertEquals(5, test.getList().size());                  // test se cambia la size dell'array
        Assert.assertEquals(new Position(0, 0),test.getList().getFirst());   // test ultima posizione

        test.getMovement().move(MovementRules.MOVING_FORWARD, false);
        test.addCurrentPosition();
        Assert.assertEquals(5, test.getList().size());                   // test se cambia la size dell'array 
        Assert.assertEquals(new Position(-1, 0),test.getList().getFirst());     // test ultima posizione

        test.resetPositions();
        Assert.assertEquals(1, test.getList().size());                   // test se cambia la size dell'array
        Assert.assertEquals(new Position(-3, -2),test.getList().getFirst());      // test ultima posizione

    }
    
    @Test
    public void testSingleton(){
        final Player test2 = Fonzie.getFonzieInstance(new Position(0, 6));

        Assert.assertEquals(test2, test);
        Assert.assertEquals(new Position(-3, -2),test2.getList().getFirst());     // test ultima posizione essendo singleton 
                                                                                  // deve rimanere invariata da quello precendente
    }
}
