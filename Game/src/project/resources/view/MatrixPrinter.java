package project.resources.view;

import project.resources.model.characters.Player;
import project.resources.model.gamemap.MapCell;
import project.resources.model.movement.Position;

import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;

public final class MatrixPrinter extends JFrame {

    private static Optional<MatrixPrinter> singletonInstance = Optional.empty();
    private final Player character;
    private final int rows;
    private final int cols;
    private Map<Position, MapCell> map;

    private MatrixPrinter(final Map<Position, MapCell> map, final Player character,
            final int rows, final int cols) {
        final ImageIcon topLeftIcon = new ImageIcon("Game/images/icon/lego.png");
        this.setIconImage(topLeftIcon.getImage());
        this.map = map;
        this.character = character;
        this.rows = rows;
        this.cols = cols;
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // design pattern singleton per non creare più MatrixPrinter
    public final static MatrixPrinter getMatrixPrinterInstance(final Map<Position, MapCell> map, final Player character,
            final int rows, final int cols) {
        if (MatrixPrinter.singletonInstance.isEmpty()) {
            MatrixPrinter.singletonInstance = Optional.of(new MatrixPrinter(map, character, rows, cols));
        }
        return MatrixPrinter.singletonInstance.get();
    }

    // metodo per la chiusura della finestra
    public final void exit() {
        dispose();
    }

    public final void updateMatrix(final Map<Position, MapCell> map) {
        this.map = map;
        this.repaint();
    }

    private final void drawCell(final Graphics g, final String path, final int xPos, final int yPos, final int width,
            final int height) {
        final ImageIcon icon = new ImageIcon(path);
        final Image image = icon.getImage();
        g.drawImage(image, xPos, yPos, width, height, this);
    }

    // metodo per disegnare in maniera dinamica tutte le immagini della matrice
    @Override
    public final void paint(final Graphics g) {
        // Aggiungi buffering doppio per rimuovere lo sfarfallio
        // Creiamo un'immagine e la disegniamo al posto del JFrame vero e proprio
        final Image framebuffer = createImage(this.getWidth(), this.getHeight());
        // Grafica di framebuffer
        final Graphics framebufferGraphics = framebuffer.getGraphics();
        // traslazione verso il basso di 30 px
        framebufferGraphics.translate(0, 30);
        // suddivisione in celle uguali in base alla lunghezza della finestra
        final int cellWidth = getWidth() / this.cols;
        final int cellHeight = (getHeight() - 30) / this.rows;
        // popolamento muri e oggetti, 
        // controllo per ogni oggetto all'interno della matrice
        IntStream.range(0, this.rows).forEach(i -> {
            IntStream.range(0, this.cols).forEach(j -> {
                final Position pos = new Position(i, j);
                final MapCell cell = map.get(pos);
                final int xPos = j * cellWidth;
                final int yPos = i * cellHeight;
                // Creazione background, altrimenti le immagini avrebbero un background bianco
                if (character.getMovement().getPos().equals(pos) ||
                        cell.element().isPresent() ||
                        !cell.isWall()) {
                    this.drawCell(framebufferGraphics, "Game/images/Empty.png", j * cellWidth, i * cellHeight,
                            cellWidth, cellHeight);
                }
                // Disegna gli elementi sulla mappa
                if (this.character.getMovement().getPos().equals(pos)) {
                    this.drawCell(framebufferGraphics, "Game/images/" + this.character.getName() + ".png", xPos, yPos,
                            cellWidth,
                            cellHeight);
                } else if (cell.isWall()) {
                    this.drawCell(framebufferGraphics, "Game/images/wall.png", xPos, yPos, cellWidth, cellHeight);
                } else if (cell.element().isPresent()) {
                    this.drawCell(framebufferGraphics, "Game/images/" + cell.element().get().getName() + ".png", xPos,
                            yPos, cellWidth,
                            cellHeight);
                }
            });
        });
        // Disegna il framebuffer sullo schermo
        g.drawImage(framebuffer, 0, 0, this);
    }
}
