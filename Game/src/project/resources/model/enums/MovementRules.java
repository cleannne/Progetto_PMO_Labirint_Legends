package project.resources.model.enums;

import java.util.EnumSet;
import java.util.Random;

import project.resources.model.movement.Position;

import java.util.Optional;

// Oggetti dell'enum da usare per identificare ciò che
// l'utente digita da tastiera per muovere il personaggio.

public enum MovementRules {
	MOVING_FORWARD('w'), // movimento sopra
	MOVING_DOWN('s'), // movimento sotto
	MOVING_LEFT('a'), // movimento sinistra
	MOVING_RIGHT('d'); // movimento destra

	static final Random RAND = new Random();
	private char keyboardValue;

	// acquisizione del valore del tasto premuto per muoversi
	private MovementRules(final char value) {
		this.keyboardValue = value;
	}

	// return del valore del tasto premuto per muoversi
	public char getKeyboardValue() {
		return this.keyboardValue;
	}

	// metodo per determinare dove spostare il personaggio
	public Position getPositionOffset() { // x per le colonne y per le righe
		switch (this) {
			case MOVING_FORWARD:
				return new Position(-1, 0);
			case MOVING_DOWN:
				return new Position(1, 0);
			case MOVING_LEFT:
				return new Position(0, -1);
			case MOVING_RIGHT:
				return new Position(0, 1);
			default:
				throw new RuntimeException("Valore di movement non valido");
		}
	}

	public static MovementRules getRandomDirection() {
		final Optional<MovementRules> randomDir = EnumSet.allOf(MovementRules.class)
				.stream()
				.reduce((a, b) -> {
					return RAND.nextInt(2) == 0 ? a : b;
				});
		if (randomDir.isPresent()) {
			return randomDir.get();
		}
		throw new RuntimeException("Could not generate a random direction.");
	}
}
