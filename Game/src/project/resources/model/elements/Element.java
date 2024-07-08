package project.resources.model.elements;

import project.resources.model.characters.Player;

// interfaccia per gli ostacoli
public interface Element {

	// effetto che deve avere ogni ostacolo
	void effect(final Player player);

	// Nome dell'effetto
	String getName();

	// implementare il suono
	void sound();
}
