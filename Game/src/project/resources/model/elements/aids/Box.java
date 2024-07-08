package project.resources.model.elements.aids;

import project.resources.model.characters.Player;
import project.resources.model.elements.Element;
import project.resources.model.sounds.ISound;
import project.resources.model.sounds.Sound;

// classe cassa per guadagnare monete

public final class Box implements Element {

	private static final String NAME = "Box";
	private static final ISound sound = new Sound();
	private final int coinsToAdd;

	// costruttore
	public Box(final int coinsToAdd) {
		this.coinsToAdd = coinsToAdd;
	}

	// Aggiunta delle monete al personaggio
	@Override
	public final void effect(final Player player) {
		player.setCoins(player.getCoins() + this.coinsToAdd);
		this.sound();
	}

	@Override
	public final String getName() {
		return Box.NAME;
	}

	@Override
	public final void sound() {
		Box.sound.playSound("Game/sounds_library/box.wav", false);
	}
}