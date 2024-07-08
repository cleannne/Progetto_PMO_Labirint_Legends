package project.resources.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Optional;
import java.util.EnumSet;

import project.resources.view.FileViewer;
import project.resources.view.GameOverFrame;
import project.resources.view.InputPlayer;
import project.resources.view.MatrixPrinter;
import project.resources.model.characters.Fonzie;
import project.resources.model.characters.Linda;
import project.resources.model.characters.Player;
import project.resources.model.enums.GameDifficulty;
import project.resources.model.enums.GameStatus;
import project.resources.model.enums.MovementRules;
import project.resources.model.gamemap.GameMap;
import project.resources.model.gamemap.IGameMap;
import project.resources.model.movement.Position;
import project.resources.model.ranking.IRanking;
import project.resources.model.ranking.IResult;
import project.resources.model.ranking.IUser;
import project.resources.model.ranking.Ranking;
import project.resources.model.ranking.Result;
import project.resources.model.ranking.User;
import project.resources.model.sounds.ISound;
import project.resources.model.sounds.Sound;

// implementazione classe controller
public final class Controller {

	// attributi classe controller
	private final ISound principalSound;
	private static Optional<Controller> singletonController;
	private final IUser user; 
	private final IResult result; 
	private final IRanking ranking;
	private final InputPlayer player;
	private final ISound ambientSound;
	private MatrixPrinter print;
	private IGameMap map;
	private Player character;
	private int rows;
	private int cols;
	private Optional<MovementRules> movementDir;

	private Controller() {
		this.movementDir = Optional.empty();
		this.user = User.getUserInstance();
		this.result = Result.getResultInstance();
		this.ranking = Ranking.getRankingInstance();
		this.player = InputPlayer.getInputPlayerinstance();
		this.ambientSound = new Sound();
		this.principalSound = new Sound();
	}

	// singleton
	public static Controller getControllerInstance() {
		if (Controller.singletonController == null) {
			Controller.singletonController = Optional.ofNullable(new Controller());
		}
		return Controller.singletonController.get();
	}

	public void startGame() {

		// Metodo usato per la raccolta delle informazioni iniziali del player giocante
		this.getData();

		// Stream che controlla tutte le difficoltà presenti nel gioco, e cerca quella
		// con il nome corretto e.g.: EASY, NORMAL, HARD ...
		final Optional<GameDifficulty> diffOpt = EnumSet.allOf(GameDifficulty.class)
				.stream()
				.filter(v -> v.getName() == player.getGameLevel())
				.reduce((a, b) -> a);

		// Se quella difficoltà non esiste, lancia una eccezione dato che non è possibile giocare
		if (diffOpt.isEmpty()) {
			throw new RuntimeException("User inserted a non valid difficulty option!");
		}

		// Ottieni la difficoltà e la posizione iniziale del character
		final GameDifficulty diff = diffOpt.get();

		// Posizione iniziale del personaggio in fondo a destra
		final Position startingPos = new Position(diff.getMapDimensions().x() - 2, diff.getMapDimensions().y() - 2);

		// Crea la mappa in base alla difficoltà scelta e al personaggio
		final IGameMap myMap = new GameMap(diff,
				diff.getMapDimensions(),
				player.getCharacter() == 1 ? Linda.getLindaInstance(startingPos)
						: Fonzie.getFonzieInstance(startingPos));

		// Inizializzazione comunicazione tra il modello e la view
		this.getGameMap(myMap,
				myMap.getCharacterFromMap(),
				diff.getMapDimensions().x(),
				diff.getMapDimensions().y());

		// Comunicazione tra modello e view per tutto il tempo di gioco
		this.runGame();
	}

	// metodo utilizzato per ottenere il modello della mappa per iniziare il gioco
	private void getGameMap(final IGameMap map, final Player character, final int rows, final int cols) {
		this.map = map;
		this.character = character;
		this.rows = rows;
		this.cols = cols;
		this.print = MatrixPrinter.getMatrixPrinterInstance(this.map.getMap(),
				this.character, this.rows, this.cols);
		this.print.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(final KeyEvent e) {
				// Inutilizzato
			}

			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyChar() == 'w') {
					movementDir = Optional.of(MovementRules.MOVING_FORWARD);
				} else if (e.getKeyChar() == 'a') {
					movementDir = Optional.of(MovementRules.MOVING_LEFT);
				} else if (e.getKeyChar() == 's') {
					movementDir = Optional.of(MovementRules.MOVING_DOWN);
				} else if (e.getKeyChar() == 'd') {
					movementDir = Optional.of(MovementRules.MOVING_RIGHT);
				}
			}

			@Override
			public void keyReleased(final KeyEvent e) {
				// Inutilizzato
			}
		});
	}

	// metodo principale del controller per la gestione della parte grafica e del modello
	private void runGame() {

		// inizio musica
		// inizializzo una variabile temporanea per consentire ascoltare il suono di sottofondo
		this.principalSound.playSound("Game/sounds_library/suono.wav", true);

		// inizio campionamento tempo
		final int startTime = (int) System.currentTimeMillis();

		while (this.map.getGameStatus() == GameStatus.GAME_IN_PROGRESS) {

			// aggiornamento grafica mappa
			this.printGame();

			this.movementDir = null;

			// acquisizione tasti WASD
			while (this.movementDir == null) {
				try {
					Thread.sleep(100);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.ambientSound.stopSound();
			this.ambientSound.playSound("Game/sounds_library/moviment.wav", false);
			// mi muovo in base al carattere premuto
			if (this.movementDir.isPresent()) {
				this.map.move(this.movementDir.get());
			}
		}

		// chiusura di tutte le finestre della grafica e audio
		this.print.exit();
		this.principalSound.stopSound();

		// settaggio di tutti i risultati nell apposita classe
		result.setTotalCoins(this.map.getCharacterFromMap().getCoins());
		result.setUser(user);
		final int endTime = (int) System.currentTimeMillis();
		result.setTotalMatchDuration(endTime - startTime);
		result.setCharacterChosen(this.map.getCharacterFromMap().getName());

		// se sono qua il gioco è terminato perchè sono riuscito ad arrivare all'uscita
		if (this.map.getGameStatus() == GameStatus.GAME_WON) {
			// aggiornamento e ordinamento nel file per la stampa della classifica
			ranking.addResult(result);
			ranking.sortFileRanking();
			// suono vittoria del personaggio
			this.principalSound.playSound("Game/sounds_library/victory.wav", false);
			// creo l'oggetto che si occupa di mostrare a schermo la classifica
			new FileViewer(result.getResult());
		}

		// se sono qua il gioco è terminato per uccisione di un NPC
		if (this.map.getGameStatus() == GameStatus.GAME_LOST) {
			// suono morte del personaggio
			this.principalSound.playSound("Game/sounds_library/game_over.wav", false);
			GameOverFrame.getGameOverFrameInstance(result.getResult());
		}
	}

	// metodo per ottenere i dati per il modello
	private void getData() {
		// finchè l'input (il nome) non è corretto non andare avanti
		while (player.getUserInput() == null || player.getUserInput().isEmpty()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// setto il nome dato che se è uscito dal while è corretto
		user.setUsername(player.getUserInput());
	}

	// metodo per aggiornare la mappa
	private void printGame() {
		this.print.updateMatrix(this.map.getMap());
	}

}

class ExecuteController {
	public static void main(String... args) {
		final Controller controller = Controller.getControllerInstance();
		controller.startGame();
	}
}
