package project.resources.view;

import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;

public final class GameOverFrame {

    private static Optional<GameOverFrame> singletonInstance = Optional.empty();

    // design pattern singleton per non creare più MatrixPrinter
    public static final GameOverFrame getGameOverFrameInstance(String result) {
        if (GameOverFrame.singletonInstance.isEmpty()) {
            GameOverFrame.singletonInstance = Optional.of(new GameOverFrame(result));
        }
        return GameOverFrame.singletonInstance.get();
    }

    private GameOverFrame(String result) {

        // frame creato per stampare al giocatore il fatto che è morto
        final JFrame frame = new JFrame("Game Over");
        final ImageIcon topLeftIcon = new ImageIcon("Game/images/icon/lego.png");
        frame.setIconImage(topLeftIcon.getImage());
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // mette il frame al centro
        frame.setLocationRelativeTo(null);

        final JPanel panel = new JPanel(new BorderLayout());

        // prende un immagine e la inserisce nel pannello
        final ImageIcon icon = new ImageIcon("Game/images/game-over.gif");
        final JLabel label = new JLabel(icon);
        panel.add(label, BorderLayout.CENTER);

        final JLabel scoreLabel = new JLabel(result, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(scoreLabel, BorderLayout.SOUTH);

        frame.add(panel);

        // rende visibile il pannello
        frame.setVisible(true);
    }
}
