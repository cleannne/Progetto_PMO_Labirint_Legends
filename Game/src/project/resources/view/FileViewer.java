package project.resources.view;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public final class FileViewer extends JFrame {

    private JList<String> dataList;
    private JLabel scoreLabel;
    private static final int MAX_ITEM_RANKING = 10;
    private static final int LABEL_FONT_SIZE = 10;

    public FileViewer(String result) {
        this.scoreLabel = new JLabel(result, SwingConstants.CENTER);
        this.scoreLabel.setFont(new Font("Arial", Font.BOLD, FileViewer.LABEL_FONT_SIZE));

        // creazione della Finestra di stampa della classifica
        final ImageIcon topLeftIcon = new ImageIcon("Game/images/icon/lego.png");
        this.setIconImage(topLeftIcon.getImage());
        setTitle("TOP 10");
        setSize(600, 230);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final DefaultListModel<String> listModel = new DefaultListModel<>();
        // prende i primi 10 dati dal file ranking.txt
        try {
            final BufferedReader reader = new BufferedReader(new FileReader("Game/ranking/ranking.txt"));
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null && count != FileViewer.MAX_ITEM_RANKING) {
                listModel.addElement(line);
                ++count;
            }
            // chiude la chiamata al file
            reader.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        // inserisce nella lista i dati letti dal file
        dataList = new JList<>(listModel);
        dataList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataList.setLayoutOrientation(JList.VERTICAL);
        dataList.setVisibleRowCount(-1);

        // aggiunge un pannello scorrevole alla lista
        final JScrollPane scrollPane = new JScrollPane(dataList);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        add(scrollPane, BorderLayout.CENTER);

        // mette le celle al centro
        final DefaultListCellRenderer renderer = (DefaultListCellRenderer) dataList.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        add(this.scoreLabel, BorderLayout.SOUTH);

        // mette la finestra al centro della pagina e la rende visibile
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
