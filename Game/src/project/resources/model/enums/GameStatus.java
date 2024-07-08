package project.resources.model.enums;

public enum GameStatus {
    GAME_IN_PROGRESS,
    GAME_LOST,
    GAME_WON;

    public String getMessage() {
        switch (this) {
            case GAME_LOST:
                return "lost";                                                                      // messaggio mandato quando un Npc ti tocca 
            case GAME_WON:
                return "win";                                                                       // messaggio mandato quanto la partita finisce
            case GAME_IN_PROGRESS:
                return "";                                                                          // messaggio mandato quando è in corso il gioco
            default:
                throw new IllegalStateException("Il valore di status della partita non é valido!"); // messaggio di errore
        }
    }
}