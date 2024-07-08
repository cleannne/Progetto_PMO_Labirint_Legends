package project.resources.model.movement;

import project.resources.model.elements.Element;
import project.resources.model.enums.MovementRules;

public interface IMovement {
    
    Position getPos();

    void setPos(final Element element, final int x, final int y);

    boolean move(final MovementRules direction, final boolean isWall);

    int getNMovement();

    void resetNMovement(final Element element, final int nMovement);
}
