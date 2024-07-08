package project.resources.model.gamemap;

import project.resources.model.characters.Player;
import project.resources.model.elements.obstacles.Npc;
import project.resources.model.enums.GameDifficulty;
import project.resources.model.enums.GameStatus;
import project.resources.model.enums.MovementRules;
import project.resources.model.movement.Position;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.EnumSet;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;

// classe che implementa la mappa del gioco
public final class GameMap implements IGameMap {
	private static final Position FINAL_POSITION = new Position(1, 0); // Posizione di vittoria del character
	private static final Random RAND = new Random(); // Variabile random statica per evitare re-inizializzazioni
	private static final int MAX_MAP_GENERATION_ATTEMPS = 30_000;

	private final Map<Position, MapCell> map;
	private final int rows;
	private final int columns;
	private final Map<MapCell, Integer> generationValues;
	private final Player character;
	private GameStatus gameStatus;

	// numero di righe
	// numero di colonne
	// nome del personaggio che si intende creare
	public GameMap(final GameDifficulty difficulty, final Position mapDimensions, final Player character) {
		this.rows = mapDimensions.x();
		this.columns = mapDimensions.y();
		this.character = character;
		this.generationValues = new HashMap<>();
		this.createsGenerationValues(difficulty);
		this.map = new HashMap<>();
		this.generateMap();
		this.gameStatus = GameStatus.GAME_IN_PROGRESS;
	}

	private final void createsGenerationValues(final GameDifficulty difficulty) {
		// Si rimuovono 2 colonne e 2 righe per evitare possibili muri esterni.
		// Inoltre si rimuovono un'altra riga e un'altra colonna 
		// per assicurare almeno una via percorribile
		final int totalSpots = (this.rows - 3) * (this.columns - 3);
		for (final Map.Entry<MapCell, Float> pair : difficulty.getGenerationMap().entrySet()) {
			this.generationValues.put(pair.getKey(),
					(int) ((float) totalSpots * pair.getValue()
					// La quantità di muri viene dimezzata poiché vengono generati a coppie
							* (pair.getKey().equals(MapCell.WALL_CELL) ? 0.5f : 1.0f)));
		}
	}

	// Metodo per ottenere le dimensioni della mappa x: righe, y: colonne
	@Override
	public final Position getMapDimensions() {
		return new Position(this.rows, this.columns);
	}

	// metodo per lo status della mappa
	@Override
	public final GameStatus getGameStatus() {
		return this.gameStatus;
	}

	@Override
	public final Map<Position, MapCell> getMap() {
		return Map.copyOf(this.map);
	}

	// metodo per generare la mappa in modo dinamico
	private final void generateMap() {
		int attempts = 1;
		boolean mapReady = false;
		while (!mapReady) {
			System.out.println("Tentativo di generazione mappa numero: " + attempts);
			this.map.clear();
			// Generazione della mappa con muri
			IntStream.range(0, rows).forEach(i -> {
				IntStream.range(0, columns).forEach(j -> {
					if (i == 0 || j == 0 || i == rows - 1 || j == columns - 1) {
						this.map.put(new Position(i, j), MapCell.WALL_CELL);
					} else {
						this.map.put(new Position(i, j), MapCell.EMPTY_CELL);
					}
				});
			});
			// Lista con tutte le posizioni vuote sulla mappa
			final List<Position> emptyPositions = this.map.keySet()
					.stream()
					.filter(p -> this.map.get(p) == MapCell.EMPTY_CELL)
					.filter(p -> !p.equals(this.character.getMovement().getPos()))
					.collect(Collectors.toList());
			// Generazione degli elementi e dei muri sulla mappa
			this.generationValues
					// Per ogni elemento, produrne un numero x e metterli in posizioni random
					.forEach((type, genAmount) -> {
						IntStream.range(0, genAmount).forEach(i -> {
							// Prendere una posizione random e collocarci l'elemento
							final Position pos = emptyPositions.remove(RAND.nextInt(emptyPositions.size()));
							this.map.put(pos, type);
							// Se è un muro, assicurarsi che vengano generati in coppie
							if (type.equals(MapCell.WALL_CELL)) {
								final Position directionOffset = MovementRules.getRandomDirection().getPositionOffset();
								final Position newPosition = new Position(pos.x() + directionOffset.x(),
										pos.y() + directionOffset.y());
								this.map.put(newPosition, type);
								emptyPositions.remove(newPosition);
							}
						});
					});
			// Aggiungere via d'uscita
			map.put(FINAL_POSITION, MapCell.EMPTY_CELL);
			// Controlla se la mappa funziona
			mapReady = this.isMapValid();
			attempts += 1;
			// Se la mappa è stata generata più di 30000 volte, lancia un'eccezione
			if (attempts > GameMap.MAX_MAP_GENERATION_ATTEMPS) {
				throw new RuntimeException("La mappa non è stata generata correttamente.");
			}
		}
	}

	// metodo per validare la mappa
	@Override
	public final boolean isMapValid() {
		// Insieme per memorizzare le posizioni visitate
		final Set<Position> visitedPositions = new HashSet<>();
		// Coda che riordina le posizioni da visitare in base alla più vicina all'uscita
		final Queue<Position> positionsToVisit = new PriorityQueue<>(Comparator.comparingInt(p -> {
			final int dx = p.x() - FINAL_POSITION.x();
			final int dy = p.y() - FINAL_POSITION.y();
			return dx * dx + dy * dy;
		}));
		positionsToVisit.add(this.character.getMovement().getPos());
		// Controlla tutte le posizioni da visitare
		while (!positionsToVisit.isEmpty()) {
			// Ottieni la posizione più vicina all'uscita
			final Position currentPos = positionsToVisit.poll();
			// Aggiungerla all'insieme di quelle già visitate
			visitedPositions.add(currentPos);
			// Se è la posizione finale per uscire, allora la mappa è valida
			if (currentPos.equals(FINAL_POSITION)) {
				return true;
			}
			// Insieme che contiene tutte le posizioni vicine a quella corrente 
			// eccetto quelle già visitate o già nell'insieme positionsToVisit
			final Set<Position> reachablePositions = this
					.calculateValidPositionsAround(currentPos, this.character.getSpeed())
					.stream()
					.filter(p -> !visitedPositions.contains(p) && !positionsToVisit.contains(p))
					.collect(Collectors.toSet());
			// Aggiungerle all'insieme delle posizioni da visitare
			for (final Position p : reachablePositions) {
				positionsToVisit.add(p);
			}
		}
		// Non valid map
		return false;
	}

	// metodo per ritornare le posizioni
	private final Set<Position> calculateValidPositionsAround(final Position pos, final int speed) {
		final Set<Position> positions = new HashSet<>();
		// Ottieni tutte le direzioni possibili
		EnumSet.allOf(MovementRules.class)
				.stream()
				.map(r -> r.getPositionOffset())
				.forEach(e -> {
					// Controlla la posizione in base alla velocità del personaggio
					Optional<Position> finalPos = Optional.empty();
					for (int i = 0; i < speed; ++i) {
						final Position newPos = new Position(pos.x() + e.x() * (i + 1), pos.y() + e.y() * (i + 1));
						// Se il personaggio ha colpito un muro o un NPC esci dal ciclo
						if (!checkIfIsInBounds(newPos) || checkIfIsPositionDeadly(newPos)) {
							break;
						}
						// Aggiorna la posizione corrente del personaggio
						finalPos = Optional.of(newPos);
					}
					// Se il personaggio si è mosso, aggiungere la nuova posizione a quelle possibili
					if (finalPos.isPresent()) {
						positions.add(finalPos.get());
					}
				});
		return positions;
	}

	// Controlla che una posizione sia nei limiti della mappa
	private final boolean checkIfIsInBounds(final Position p) {
		return p.x() >= 0 && p.y() >= 0 && p.x() <= rows - 1 && p.y() <= columns - 1;
	}

	// Controlla che la posizione non sia "raggiungibile" e.g.: muri e npc
	private final boolean checkIfIsPositionDeadly(final Position p) {
		return this.map.get(p).equals(MapCell.WALL_CELL)
				|| (this.map.get(p).element().isPresent() && this.map.get(p).element().get() instanceof Npc);
	}

	// metodo per muovere il personaggio nella mappa
	@Override
	public final void move(final MovementRules key) {
		// prendi la direzione del personaggio
		final Position positionOffset = key.getPositionOffset();
		// prendi la sua velocità
		final int characterSpeed = character.getSpeed();

		// se la cella non esiste e nella cella in mezzo non c'è un muro mi fermo prima
		IntStream.range(0, characterSpeed).forEach(i -> {
			// Ottieni la posizione e cella corrente
			final Position currentPos = this.character.getMovement().getPos();
			final MapCell currentCell = this.map.get(new Position(
					currentPos.x() + positionOffset.x(),
					currentPos.y() + positionOffset.y()));
			// Muovi il personaggio verso la direzione desiderata
			this.character.getMovement().move(key, currentCell.isWall());
			// Aggiorna la coda di posizioni del personaggio (PER LA BANANA)
			this.character.addCurrentPosition();
			// Svuota la posizione nuova del personaggio
			this.map.put(this.character.getMovement().getPos(), MapCell.EMPTY_CELL);
			// Se c'è un elemento attiva il suo effetto
			if (currentCell.element().isPresent()) {
				currentCell.element().get().effect(this.character);
			}
			// Se il personaggio è morto, siamo in game over
			if (this.character.isDead()) {
				this.gameStatus = GameStatus.GAME_LOST;
			}
			// Condizione di vittoria
			if (this.checkIfIsTimeToEndTheMatch(this.character.getMovement().getPos())) {
				this.gameStatus = GameStatus.GAME_WON;
				return;
			}
		});
		System.out.println("COINS " + this.character.getCoins()); 
	}

	// metodo getter per ottenere il personaggio dalla mappa
	@Override
	public final Player getCharacterFromMap() {
		return this.character;
	}

	// Controlla se la partita è finita guardando se il personaggio è all'uscita del labirinto
	private final boolean checkIfIsTimeToEndTheMatch(final Position pos) {
		return pos.equals(FINAL_POSITION);
	}
}
