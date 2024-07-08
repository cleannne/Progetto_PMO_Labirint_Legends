package project.resources.model.ranking;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.List;

// classe per la gestione del file con la classifica

public final class FileMask implements IFileMask {
	private static final Pattern NUMERICAL_PATTERN = Pattern.compile("\\d+");
	private static final int MONEY_MEMORY_INDEX = 4;
	private static final int TIME_MEMORY_INDEX = 3;

	private static Optional<IFileMask> singletonInstance = Optional.empty();
	private final File file;

	// costruttore
	private FileMask(final String fileName) {
		this.file = new File(fileName);
		try {
			// Controlla se il file non esiste
			if (!this.file.exists()) {
				// Crea il file
				if (!this.file.createNewFile()) {
					throw new RuntimeException("Errore nella creazione del file di ranking.");
				}
			}
		} catch (final IOException e) {
			throw new RuntimeException("Errore nel file di ranking: " + e.getMessage());
		}
	}

	// design pattern singleton per non creare più file
	public final static IFileMask getFileInstance() {
		if (FileMask.singletonInstance.isEmpty()) {
			FileMask.singletonInstance = Optional.of(new FileMask("Game/ranking/ranking.txt"));
		}
		return FileMask.singletonInstance.get();
	}

	// metodo getter per avere il file (per il testing)
	public final File getFile(){
		return this.file;
	}

	// scrive nel file i valori ottenuti dal giocatore
	@Override
	public final void writeInFile(final String result) {
		try (final BufferedWriter bw = new BufferedWriter(new FileWriter(this.file, true))) {
			bw.write(result + '\n');
		} catch (final IOException e) {
			System.out.println("Errore nella scrittura del file: " + e.getMessage());
		}
	}

	// ritorna una parte numerica della stringa
	private final String getNumericalPartsFromString(final String str) {
		final Matcher matcher = NUMERICAL_PATTERN.matcher(str);
		final StringBuilder result = new StringBuilder();
		while (matcher.find()) {
			result.append(matcher.group());
		}
		return result.toString();
	}

	// riordina il file in base al tempo più basso
	@Override
	public final void sortFile() {
		// lista di appoggio per il sorting
		final List<String> lines = new ArrayList<>();

		// legge i dati dal file riga per riga e li inserisce nella lista
		try (final BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (final IOException e) {
			System.out.println("Errore nella lettura del file: " + e.getMessage());
		}

		// Ordina la lista in base al punteggio più basso monete-tempo
		Collections.sort(lines, Comparator.comparingInt(s -> {
			final String[] parts = s.split(": "); // spezza le righe secondo il separatore ": "
			// Ordina in base a monete - tempo (le monete contano nella classifica)
			return Integer.parseInt(getNumericalPartsFromString(parts[FileMask.MONEY_MEMORY_INDEX]))
					- Integer.parseInt(getNumericalPartsFromString(parts[FileMask.TIME_MEMORY_INDEX]));
		}));

		// Sovrascrive il vecchio file con il nuovo elenco ordinato
		try (final BufferedWriter writer = new BufferedWriter(new FileWriter(this.file))) {
			for (final String line : lines) {
				writer.write(line);
				writer.newLine();
			}
		} catch (final IOException e) {
			System.out.println("Errore nella scrittura del file: " + e.getMessage());
		}
	}
}
