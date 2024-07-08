package Game.test.sound;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import project.resources.model.sounds.ISound;
import project.resources.model.sounds.Sound;

public class SoundTest {

    @Test
    public void testSoundBox() {
        final ISound sound = new Sound();
        sound.playSound("Game/sounds_library/box.wav", false);
        // Aspetta fino alla fine del suono
        while (!sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
        while (sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
    }

    @Test
    public void testBananaSound() {
        final ISound sound = new Sound();
        sound.playSound("Game/sounds_library/banana.wav", false);
        // Aspetta fino alla fine del suono
        while (!sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
        while (sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
    }

    @Test
    public void testBombSound() {
        final ISound sound = new Sound();
        sound.playSound("Game/sounds_library/bomb.wav", false);
        // Aspetta fino alla fine del suono
        while (!sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
        while (sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
    }

    @Test
    public void testGameOverSound() {
        final ISound sound = new Sound();
        sound.playSound("Game/sounds_library/game_over.wav", false);
        // Aspetta fino alla fine del suono
        while (!sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
        while (sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
    }

    @Test
    public void testMovementSound() {
        final ISound sound = new Sound();
        sound.playSound("Game/sounds_library/moviment.wav", false);
        // Aspetta fino alla fine del suono
        while (!sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
        while (sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
    }

    @Test
    public void testNpcSound() {
        final ISound sound = new Sound();
        sound.playSound("Game/sounds_library/npc.wav", false);
        // Aspetta fino alla fine del suono
        while (!sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
        while (sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
    }

    @Test
    public void testSpellSound() {
        final ISound sound = new Sound();
        sound.playSound("Game/sounds_library/spell.wav", false);
        // Aspetta fino alla fine del suono
        while (!sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
        while (sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
    }

    @Test
    public void testSound() { // test backgroundSound e se si stoppa la musica
        final ISound sound = new Sound();
        sound.playSound("Game/sounds_library/suono.wav", true);
        try {
            Thread.sleep(2000);
        } catch (final InterruptedException e) {
        }
        sound.stopSound();
        assertEquals(false, sound.isRunning());
    }

    @Test
    public void testVictorySound() {
        final ISound sound = new Sound();
        sound.playSound("Game/sounds_library/victory.wav", false);
        // Aspetta fino alla fine del suono
        while (!sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
        while (sound.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
    }
}
