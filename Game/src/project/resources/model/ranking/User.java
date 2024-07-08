package project.resources.model.ranking;

import java.util.Optional;

// classe per la gestione della sessione del giocatore

public final class User implements IUser {

	private static Optional<IUser> singletonInstance = Optional.empty();
	private String username;

	// design pattern singleton per non creare altri User durante la partita singolo giocatore
	public static IUser getUserInstance() {
		if (singletonInstance.isEmpty()) {
			User.singletonInstance = Optional.of(new User());
		}
		return User.singletonInstance.get();
	}

	// setto username del giocatore
	@Override
	public final void setUsername(final String username) {
		this.username = username;
	}

	// return del nome del giocatore
	@Override
	public final String getUsername() {
		return this.username;
	}
}
