package project.resources.model.characters;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

import project.resources.model.movement.Position;

public final class Fonzie extends Player {

	private static Optional<Player> singletonInstance = Optional.empty();
	private static final int F_SPEED = 1;
	private static final int F_LIFE = 2;
	private static final int MAX_BANANA_STEPS = 5;

	private final Deque<Position> positions;

	private Fonzie(final Position startingPosition) {
		super("Fonzie", startingPosition, Fonzie.F_SPEED, Fonzie.F_LIFE);
		this.positions = new ArrayDeque<>();
		this.addCurrentPosition();
	}

	// Metodo per l'istanza singleton
	public static final Player getFonzieInstance(final Position startingPosition) {
		if (Fonzie.singletonInstance.isEmpty()) {
			Fonzie.singletonInstance = Optional.of(new Fonzie(startingPosition));
		}
		return Fonzie.singletonInstance.get();
	}

	@Override
	public void addCurrentPosition() {
		this.positions.add(new Position(super.getMovement().getPos().x(), super.getMovement().getPos().y()));
		// Rimuovi l'ultima posizione se ci sono più di MAX_BANANA_STEPS valori
		if (this.positions.size() > Fonzie.MAX_BANANA_STEPS) {
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
