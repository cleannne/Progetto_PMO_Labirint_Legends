package project.resources.model.elements.obstacles;

import project.resources.model.characters.Player;
import project.resources.model.elements.Element;
import project.resources.model.sounds.ISound;
import project.resources.model.sounds.Sound;

// classe bomba usata per far perdere monete al personaggio

public final class Bomb implements Element {

	private static final String NAME = "Bomb";
	private static final ISound sound = new Sound();
	private static final int NMOVEMENT_BASE_LIMIT = 0;

	private final int coinsToRemove; // rimuove 1/4 delle monete totali

	// costruttore
	public Bomb(final int coinsToRemove) {
		this.coinsToRemove = coinsToRemove;
	}

	// effetto: toglie 1/4 delle monete
	@Override
	public final void effect(final Player player) {
		if (player.getMovement().getNMovement() < Bomb.NMOVEMENT_BASE_LIMIT) {
			player.setCoins(Math.max(0, player.getCoins() - coinsToRemove));
			this.sound();
		}
	}

	@Override
	public final String getName() {
		return Bomb.NAME;
	}

	@Override
	public final void sound() {
		Bomb.sound.playSound("Game/sounds_library/bomb.wav", false);
	}
}