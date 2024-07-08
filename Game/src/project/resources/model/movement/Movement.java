package project.resources.model.movement;

import project.resources.model.elements.Element;
import project.resources.model.elements.aids.ImmunitySpell;
import project.resources.model.elements.obstacles.Banana;
import project.resources.model.enums.MovementRules;

//	Questa classe implementa la gestione del movimento
//	da parte dell'utente. Mette a disposizione della classe Map
//	i metodi per muovere il personaggio. Inoltre chiama delle 
//	eccezioni nel momento in cui il movimento non fosse possibile

public final class Movement implements IMovement {

	private static final int NMOVEMENT_BASE_LIMITER = 0;
	private static final int NMOVEMENT_DEC = 1;

	private Position pos;
	private int nMovement;

	public Movement(final Position startingPosition) {
		this.pos = startingPosition;
		this.nMovement = 0;
	}

	@Override
	public final Position getPos() {
		return this.pos;
	}

	@Override
	public final void setPos(final Element element, final int x, final int y) {
		if (element instanceof Banana) {
			this.pos = new Position(x, y);
		}
	}

	// metodo per muovere il personaggio nella mappa con relativa eccezione
	@Override
	public final boolean move(final MovementRules direction, final boolean isWall) {
		if (!isWall) {
			// Altrimenti bug di overflow (se nMovement = 0 scende nei negativi, a -4.3B
			// poi diventa positivo, rendendo l'invulnerabilitá infinita)
			if (this.nMovement >= Movement.NMOVEMENT_BASE_LIMITER) {
				this.nMovement -= Movement.NMOVEMENT_DEC;
			}
			final Position movementOffset = direction.getPositionOffset();
			this.pos = new Position(this.pos.x() + movementOffset.x(), this.pos.y() + movementOffset.y());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public final int getNMovement() {
		return this.nMovement;
	}

	@Override
	public final void resetNMovement(final Element element, final int nMovement) {
		if (element instanceof ImmunitySpell) {
			this.nMovement = nMovement;
		}
	}
}