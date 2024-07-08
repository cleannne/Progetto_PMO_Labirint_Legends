package project.resources.model.sounds;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public final class Sound implements ISound {

    private Clip clip;
    private File file;

    // metodo per riprodurre audio in due modi: in loop o una sola volta
    @Override
    public final void playSound(final String soundFile, final boolean loop) {
        try {
            this.file = new File(soundFile);
            this.clip = AudioSystem.getClip();
            this.clip.open(AudioSystem.getAudioInputStream(file));
            if (loop) {
                // Riprodotto continuamente
                this.clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                // Riprodotto un'unica volta
                this.clip.start();
            }
        } catch (final IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // metodo per forzare la chiusura del suono nel loop
    @Override
    public final void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    @Override
    public final boolean isRunning() {
        return this.clip.isRunning();
    }
}
