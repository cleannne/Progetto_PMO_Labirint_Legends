package project.resources.model.sounds;

public interface ISound {
    
    // metodo per riprodurre audio in due modi: in loop o una sola volta
    public void playSound(final String soundFile, final boolean loop);

    // metodo per forzare la chiusura del suono nel loop
    public void stopSound();

    public boolean isRunning();
}
