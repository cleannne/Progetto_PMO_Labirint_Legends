package project.resources.model.gamemap;

import java.util.Map;

import project.resources.model.characters.Player;
import project.resources.model.enums.GameStatus;
import project.resources.model.enums.MovementRules;
import project.resources.model.movement.Position;

public interface IGameMap {
    
    Position getMapDimensions();

    GameStatus getGameStatus();

    Map<Position, MapCell> getMap();

    boolean isMapValid();

    void move(final MovementRules key);

    Player getCharacterFromMap();
}
