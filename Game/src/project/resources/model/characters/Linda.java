package project.resources.model.characters;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

import project.resources.model.movement.Position;

// Implementazione del personaggio 'Linda'
public final class Linda extends Player {

	private static Optional<Player> singletonInstance = Optional.empty();
	private static final int L_SPEED = 2;
	private static final int L_LIFE = 1;
	private static final int MAX_BANANA_STEPS = 7;

	private final Deque<Position> positions;

	// Costruttore
	private Linda(final Position startingPosition) {
		super("Linda", startingPosition, Linda.L_SPEED, Linda.L_LIFE);
		this.positions = new ArrayDeque<>();
		this.addCurrentPosition();
	}

	// Metodo per l'istanza singleton
	public static final Player getLindaInstance(final Position startingPosition) {
		if (Linda.singletonInstance.isEmpty()) {
			Linda.singletonInstance = Optional.of(new Linda(startingPosition));
		}
		return Linda.singletonInstance.get();
	}

	@Override
	public void addCurrentPosition() {
		this.positions.add(new Position(super.getMovement().getPos().x(), super.getMovement().getPos().y()));
		// Rimuovi l'ultima posizione se ci sono più di MAX_BANANA_STEPS valori
		if (this.positions.size() > Linda.MAX_BANANA_STEPS) {
			this.positions.remove();
		}
	}

	@Override
	public void resetPositions() {
		this.positions.clear();
		this.addCurrentPosition();
	}

	@Override
	public Deque<Position> getList() {
		return new ArrayDeque<>(this.positions);
	}
}
