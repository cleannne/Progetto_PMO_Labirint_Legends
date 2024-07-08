package project.resources.model.ranking;

import java.util.Optional;

// classe per la gestione del risultato se l'utente vince la partita

public final class Result implements IResult {
	private static Optional<IResult> singletonInstance = Optional.empty();
	private static final int TO_SECONDS = 1000;
	private IUser user;
	private String characterChosen;
	private int coins;
	private int duration;

	// costruttore
	private Result() {
		this.coins = 0;
		this.duration = 0;
	}

	// design pattern singleton per non creare altri result
	public final static IResult getResultInstance() {
		if (Result.singletonInstance.isEmpty()) {
			Result.singletonInstance = Optional.of(new Result());
		}
		return Result.singletonInstance.get();
	}

	@Override
	public final void setUser(final IUser user) {
		this.user = user;
	}

	@Override
	public final void setCharacterChosen(final String characterChosen) {
		this.characterChosen = characterChosen;
	}

	@Override
	public final void setTotalCoins(final int coins) {
		this.coins = coins;
	}

	@Override
	public final void setTotalMatchDuration(final int time) {
		this.duration = time;
	}

	@Override
	public final String getResult() {
		return "[USER]: " + this.user.getUsername() +
				" [CHARACTER]: " + this.characterChosen +
				" [COINS]: " + this.coins +
				" [DURATION]: " + this.duration / Result.TO_SECONDS;
	}
}
