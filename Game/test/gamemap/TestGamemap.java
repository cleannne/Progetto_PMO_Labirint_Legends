package Game.test.gamemap;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import project.resources.model.characters.Linda;
import project.resources.model.characters.Player;
import project.resources.model.enums.GameDifficulty;
import project.resources.model.enums.GameStatus;
import project.resources.model.gamemap.GameMap;
import project.resources.model.gamemap.IGameMap;
import project.resources.model.gamemap.MapCell;
import project.resources.model.movement.Position;

import java.util.Map;

public class TestGamemap {

    private Player character;
    private Position mapDimensions;
    private IGameMap gameMap;

    @Before
    public void setUp() {
        character = Linda.getLindaInstance(new Position(5, 5));
        mapDimensions = new Position(10, 10);
        gameMap = new GameMap(GameDifficulty.EASY, mapDimensions, character);
    }

    @Test
    public void testMapGeneration() {
        final Map<Position, MapCell> map = gameMap.getMap();
        assertNotNull(map);
        map.entrySet().forEach(e -> {
            final Position pos = e.getKey();
            assertNotNull(pos);
            assertNotNull(e.getValue());
            assertFalse(pos.x() < 0 && pos.y() < 0 && pos.x() >= 10 && pos.y() >= 10);
        });
        assertEquals(GameStatus.GAME_IN_PROGRESS, gameMap.getGameStatus());
    }

    @Test
    public void testRandomElementsGeneration() {
        final Map<Position, MapCell> map = gameMap.getMap();
        final long emptyCellsCount = map.values().stream().filter(cell -> cell == MapCell.EMPTY_CELL).count();
        final long totalCells = mapDimensions.x() * mapDimensions.y();
        assertTrue(emptyCellsCount > 0 && emptyCellsCount < totalCells);
    }

    @Test
    public void testMapValidity() {
        assertTrue(gameMap.isMapValid());
    }
}
