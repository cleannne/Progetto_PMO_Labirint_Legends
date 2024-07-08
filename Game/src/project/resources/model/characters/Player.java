package project.resources.model.characters;

import java.util.Deque;

import project.resources.model.movement.IMovement;
import project.resources.model.movement.Movement;
import project.resources.model.movement.Position;

// Classe astratta per i personaggi
public abstract class Player {
	private int coins;
	private int life;
	private final IMovement movement;
	private final int speed;
	private final String name;

	private static final int LIFE_DEC = 1;
	private static final int DEAD = 0;

	public Player(final String name, final Position startingPosition, final int speed, final int life) {
		this.name = name;
		this.life = life;
		this.coins = 0;
		this.movement = new Movement(startingPosition);
		this.speed = speed;
	}

	// nome del personaggio
	public final String getName() {
		return this.name;
	}

	public final int getSpeed() {
		return this.speed;
	}

	public final int getCoins() {
		return this.coins;
	}

	public final void setCoins(final int coins) {
		this.coins = coins;
	}

	public final IMovement getMovement() {
		return this.movement;
	}

	public final boolean isDead() {
		return this.life <= DEAD;
	}

	public final void takeDamage() {
		this.life -= Player.LIFE_DEC;
	}

	public abstract void addCurrentPosition();
	public abstract void resetPositions();
	public abstract Deque<Position> getList();
}
