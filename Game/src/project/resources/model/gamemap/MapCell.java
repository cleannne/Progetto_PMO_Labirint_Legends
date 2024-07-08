package project.resources.model.gamemap;

import java.util.Optional;

import project.resources.model.elements.Element;

public final record MapCell(boolean isWall, Optional<Element> element) {

    public static final MapCell EMPTY_CELL = new MapCell(false, Optional.empty());

    public static final MapCell WALL_CELL = new MapCell(true, Optional.empty());

    public boolean isEmpty() {
        return !this.isWall && this.element.isEmpty();
    }
}
