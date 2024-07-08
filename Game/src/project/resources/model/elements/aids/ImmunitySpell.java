package project.resources.model.elements.aids;

import project.resources.model.characters.Player;
import project.resources.model.elements.Element;
import project.resources.model.sounds.ISound;
import project.resources.model.sounds.Sound;

public final class ImmunitySpell implements Element {

	private static final String NAME = "Immunity_Spell";
	private static final int STEPS = 3;

	private static final ISound sound = new Sound();

	// effetto usato per diventare immune agli NPC
	@Override
	public void effect(final Player player) {
		player.getMovement().resetNMovement(this, STEPS);
		this.sound();
	}

	@Override
	public final String getName() {
		return ImmunitySpell.NAME;
	}

	@Override
	public final void sound() {
		ImmunitySpell.sound.playSound("Game/sounds_library/spell.wav", false);
	}
}