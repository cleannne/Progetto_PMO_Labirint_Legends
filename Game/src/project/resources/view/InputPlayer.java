package project.resources.view;

import javax.swing.*;

import project.resources.model.enums.GameDifficulty;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumSet;
import java.util.Optional;

public final class InputPlayer extends JFrame {

    private static Optional<InputPlayer> singletonInstance = Optional.empty();
    private static final int LINDA_ID = 1;
    private static final int FONZIE_ID = 2;

    private JPanel panel;
    private JTextField textField;
    private JButton button;
    private JComboBox<String> comboBox;
    private JComboBox<String> levelBox;
    private String userInput;
    private String selectedImage;
    private String selectedLevel;

    // design pattern singleton per non creare più MatrixPrinter
    public static InputPlayer getInputPlayerinstance() {
        if (InputPlayer.singletonInstance.isEmpty()) {
            InputPlayer.singletonInstance = Optional.of(new InputPlayer());
        }
        return InputPlayer.singletonInstance.get();
    }

    private InputPlayer() {
        final ImageIcon topLeftIcon = new ImageIcon("Game/images/icon/lego.png");
        this.setIconImage(topLeftIcon.getImage());

        setTitle("Player Data");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        final JLabel label = new JLabel("Insert your Name:");
        textField = new JTextField(20);

        panel.add(label);
        panel.add(textField);

        comboBox = new JComboBox<String>();
        comboBox.addItem("Fonzie");
        comboBox.addItem("Linda");
        panel.add(comboBox);

        levelBox = new JComboBox<String>();
        EnumSet.allOf(GameDifficulty.class).forEach(d -> {
            levelBox.addItem(d.getName());
        });
        panel.add(levelBox);

        button = new JButton("PLAY");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInput = textField.getText();
                selectedImage = (String) comboBox.getSelectedItem();
                selectedLevel = (String) levelBox.getSelectedItem();
                if (userInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please, insert your name");
                } else {
                    dispose();
                }
            }
        });

        panel.add(button);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public final String getUserInput() {
        return userInput;
    }

    public final String getGameLevel() {
        return selectedLevel;
    }

    public final int getCharacter() {
        return ((selectedImage == "Linda") ? InputPlayer.LINDA_ID : InputPlayer.FONZIE_ID);
    }
}
