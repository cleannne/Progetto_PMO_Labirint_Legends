package project.resources.model.enums;

import project.resources.model.elements.aids.Box;
import project.resources.model.elements.aids.ImmunitySpell;
import project.resources.model.elements.obstacles.Banana;
import project.resources.model.elements.obstacles.Bomb;
import project.resources.model.elements.obstacles.Npc;
import project.resources.model.gamemap.MapCell;
import project.resources.model.movement.Position;

import java.util.Map;
import java.util.Optional;

/**
 * Enum per configurare la difficoltà del gioco, rappresentata da una mappa
 * e dalla percentuale di copertura di un certo elemento sulla mappa,
 * i.e. "MapCell.WALL_CELL, 0.1f" significa che il 10% delle celle vuote
 * della mappa sarà popolato da mura.
 * 
 * La somma dei valori di una difficoltà non deve essere maggiore di 0.9
 */

public enum GameDifficulty {
    EASY("EASY", new Position(GameDifficulty.EASY_MAP_SIZE, GameDifficulty.EASY_MAP_SIZE), Map.of(
            MapCell.WALL_CELL, GameDifficulty.TENP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new Box(GameDifficulty.EASY_COINS_TO_ADD))), GameDifficulty.FIVETEENP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new Bomb(GameDifficulty.EASY_COINS_TO_REMOVE))), GameDifficulty.FIVEP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new Banana())), GameDifficulty.FIVEP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new ImmunitySpell())), GameDifficulty.EIGHTP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new Npc())), GameDifficulty.TWOP_EMPTY_SPOTS_TO_COVER)),

    NORMAL("NORMAL", new Position(GameDifficulty.NORMAL_MAP_SIZE, GameDifficulty.NORMAL_MAP_SIZE), Map.of(
            MapCell.WALL_CELL, GameDifficulty.FIVETEENP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new Box(GameDifficulty.NORMAL_COINS_TO_ADD))), GameDifficulty.TENP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new Bomb(GameDifficulty.NORMAL_COINS_TO_REMOVE))), GameDifficulty.TENP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new Banana())), GameDifficulty.TENP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new ImmunitySpell())), GameDifficulty.FIVEP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new Npc())), GameDifficulty.FIVEP_EMPTY_SPOTS_TO_COVER)),

    HARD("HARD", new Position(GameDifficulty.HARD_MAP_SIZE, GameDifficulty.HARD_MAP_SIZE), Map.of(
            MapCell.WALL_CELL, GameDifficulty.TENP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new Box(GameDifficulty.HARD_COINS_TO_ADD))), GameDifficulty.FIVEP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new Bomb(GameDifficulty.HARD_COINS_TO_REMOVE))), GameDifficulty.THIRTEENP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new Banana())), GameDifficulty.TENP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new ImmunitySpell())), GameDifficulty.FIVEP_EMPTY_SPOTS_TO_COVER,
            new MapCell(false, Optional.of(new Npc())), GameDifficulty.FIVETEENP_EMPTY_SPOTS_TO_COVER));

    private final String name;
    private final Position mapDimensions;
    private final Map<MapCell, Float> generationPercentages;

    private static final float TENP_EMPTY_SPOTS_TO_COVER = 0.1f;
    private static final float FIVEP_EMPTY_SPOTS_TO_COVER = 0.05f;
    private static final float FIVETEENP_EMPTY_SPOTS_TO_COVER = 0.15f;
    private static final float THIRTEENP_EMPTY_SPOTS_TO_COVER = 0.13f;
    private static final float TWOP_EMPTY_SPOTS_TO_COVER = 0.02f;
    private static final float EIGHTP_EMPTY_SPOTS_TO_COVER = 0.08f;

    private static final int EASY_MAP_SIZE = 18;
    private static final int NORMAL_MAP_SIZE = 22;
    private static final int HARD_MAP_SIZE = 24;

    private static final int EASY_COINS_TO_ADD = 5;
    private static final int EASY_COINS_TO_REMOVE = 3;
    private static final int NORMAL_COINS_TO_ADD = 20;
    private static final int NORMAL_COINS_TO_REMOVE = 10;
    private static final int HARD_COINS_TO_ADD = 30;
    private static final int HARD_COINS_TO_REMOVE = 20;

    private GameDifficulty(final String name, final Position mapDimensions,
            final Map<MapCell, Float> generationPercentages) {
        final float totalPercentage = generationPercentages.values()
                .stream()
                .reduce(0.f, (a, b) -> a + b);
        generationPercentages.values().forEach(v -> {
            if (v < 0) {
                throw new RuntimeException("Uno dei valori delle difficoltá é negativo!");
            }
        });
        if (totalPercentage < 0.0f || totalPercentage > 0.9f) {
            throw new RuntimeException("I valori delle difficoltá sono out of bounds (somma < di 0 o > 0.9)");
        }
        this.name = name;
        this.mapDimensions = mapDimensions;
        this.generationPercentages = generationPercentages;
    }

    public Map<MapCell, Float> getGenerationMap() {
        return this.generationPercentages;
    }

    public String getName() {
        return this.name;
    }

    public Position getMapDimensions() {
        return this.mapDimensions;
    }
}