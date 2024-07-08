package project.resources.model.elements.obstacles;

import project.resources.model.characters.Player;
import project.resources.model.elements.Element;
import project.resources.model.sounds.ISound;
import project.resources.model.sounds.Sound;

// classe Banana: fa indietreggiare il personaggio di max 5 posizioni

public final class Banana implements Element {

	private static final String NAME = "Banana";

	private static final ISound sound = new Sound();

	private static final int NMOVEMENT_BASE_LIMIT = 0;

	// se il personaggio ha occupato più di 5 posizioni torna indietro di 5
	// se ne ha occupate di meno torna alla posizione iniziale
	@Override
	public final void effect(final Player player) {
		if (player.getMovement().getNMovement() < Banana.NMOVEMENT_BASE_LIMIT) {
			player.getMovement().setPos(this, player.getList().peek().x(), player.getList().peek().y());
			player.resetPositions();
			this.sound();
		} 
	}

	@Override
	public final String getName() {
		return Banana.NAME;
	}

	@Override
	public final void sound() {
		Banana.sound.playSound("Game/sounds_library/banana.wav", false);
	}
}