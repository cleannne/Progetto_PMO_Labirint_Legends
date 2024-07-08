package project.resources.model.elements.obstacles;

import project.resources.model.characters.Player;
import project.resources.model.elements.Element;
import project.resources.model.sounds.ISound;
import project.resources.model.sounds.Sound;

// classe Npc: nemico del personaggio, unico che lo può uccidere

public final class Npc implements Element {

	private static final String NAME = "NPC";
	private static final ISound sound = new Sound();
	private static final int NMOVEMENT_BASE_LIMIT = 0;

	// effetto per uccidere il personaggio
	@Override
	public final void effect(final Player player) {
		if (player.getMovement().getNMovement() <= Npc.NMOVEMENT_BASE_LIMIT) {
			player.takeDamage();
			this.sound();
		}
	}

	@Override
	public final String getName() {
		return Npc.NAME;
	}

	@Override
	public final void sound() {
		Npc.sound.playSound("Game/sounds_library/npc.wav", false);
	}
}
