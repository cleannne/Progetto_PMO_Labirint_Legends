package project.resources.model.ranking;

import java.util.Optional;

// classe Ranking usata per la gestione del ranking

public final class Ranking implements IRanking {

	private static Optional<IRanking> singletonInstance = Optional.empty();
	private final IFileMask database;

	private Ranking() {
		this.database = FileMask.getFileInstance();
	}

	// design pattern singleton per non creare altri ranking
	public static final IRanking getRankingInstance() {
		if (Ranking.singletonInstance.isEmpty()) {
			Ranking.singletonInstance = Optional.of(new Ranking());
		}
		return Ranking.singletonInstance.get();
	}

	// metodo per salvare i dati nel database
	@Override
	public final void addResult(final IResult result) {
		this.database.writeInFile(result.getResult());
	}

	// metodo pubblico per fare il sort del file
	@Override
	public final void sortFileRanking() {
		this.database.sortFile();
	}
}
