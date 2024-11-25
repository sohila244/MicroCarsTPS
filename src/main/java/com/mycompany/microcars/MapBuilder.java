package com.mycompany.microcars;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;

public class MapBuilder extends JFrame {
    private static final int ROWS = 10;
    private static final int COLS = 18;
    private JButton[][] gridButtons = new JButton[ROWS][COLS];
    private ImageIcon[] tilesImage = new ImageIcon[16];
    private int selectedTileIndex = 0; // Index of the selected tile (default: Field)

    public MapBuilder() {
        setTitle("Map Builder");
        setLayout(new BorderLayout());

        // Load tile images and initialize the grid with "Field" tiles
        loadTiles();
        JPanel gridPanel = new JPanel(new GridLayout(ROWS, COLS));
        initializeGrid(gridPanel);

        // Tile selection panel
        JPanel tilePanel = new JPanel(new FlowLayout());
        initializeTileButtons(tilePanel);

        // Save button
        JButton saveButton = new JButton("Save Map");
        saveButton.addActionListener(e -> saveMap());

        // Add components to frame
        add(gridPanel, BorderLayout.CENTER);
        add(tilePanel, BorderLayout.SOUTH);
        add(saveButton, BorderLayout.NORTH);

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initializeGrid(JPanel gridPanel) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                JButton button = new JButton(tilesImage[0]); // Initially set to "Field"
                button.putClientProperty("tileIndex", 0); // Set initial tileIndex to 0
                button.addActionListener(new GridButtonListener(row, col));
                gridButtons[row][col] = button;
                gridPanel.add(button);
            }
        }
    }

    private void initializeTileButtons(JPanel tilePanel) {
        for (int i = 0; i < tilesImage.length; i++) {
            JButton tileButton = new JButton(tilesImage[i]);
            int tileIndex = i;
            tileButton.addActionListener(e -> selectedTileIndex = tileIndex);
            tilePanel.add(tileButton);
        }
    }

    private void loadTiles() {
        try {
            tilesImage[0] = new ImageIcon(ImageIO.read(new File("data/img/tiles/Field.png")));
            tilesImage[1] = new ImageIcon(ImageIO.read(new File("data/img/tiles/Track.png")));
            tilesImage[2] = new ImageIcon(ImageIO.read(new File("data/img/tiles/TrackStart.png")));
            tilesImage[4] = new ImageIcon(ImageIO.read(new File("data/img/tiles/Corner2-4.png")));
            tilesImage[5] = new ImageIcon(ImageIO.read(new File("data/img/tiles/Corner1-3.png")));
            tilesImage[6] = new ImageIcon(ImageIO.read(new File("data/img/tiles/Corner4-2.png")));
            tilesImage[7] = new ImageIcon(ImageIO.read(new File("data/img/tiles/Corner3-1.png")));
            tilesImage[8] = new ImageIcon(ImageIO.read(new File("data/img/tiles/BorderUp.png")));
            tilesImage[9] = new ImageIcon(ImageIO.read(new File("data/img/tiles/BorderDown.png")));
            tilesImage[10] = new ImageIcon(ImageIO.read(new File("data/img/tiles/BorderLeft.png")));
            tilesImage[11] = new ImageIcon(ImageIO.read(new File("data/img/tiles/BorderRight.png")));
            tilesImage[12] = new ImageIcon(ImageIO.read(new File("data/img/tiles/CornerBorder2.png")));
            tilesImage[13] = new ImageIcon(ImageIO.read(new File("data/img/tiles/CornerBorder1.png")));
            tilesImage[14] = new ImageIcon(ImageIO.read(new File("data/img/tiles/CornerBorder3.png")));
            tilesImage[15] = new ImageIcon(ImageIO.read(new File("data/img/tiles/CornerBorder4.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   private void saveMap() {
    String[][] tilesDescription = new String[ROWS][COLS];

    // Recupera il simbolo del tile da ciascun bottone e mappa al simbolo
    for (int row = 0; row < ROWS; row++) {
        for (int col = 0; col < COLS; col++) {
            Integer tileIndex = (Integer) gridButtons[row][col].getClientProperty("tileIndex");
            if (tileIndex == null) {
                tileIndex = 0; // Default a "Field" se tileIndex è null
            }
            tilesDescription[row][col] = tileCodeToSymbol(tileIndex);
        }
    }

    // Salva nel file CSV con il formato specificato
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/tracks/costTrack.csv", false))) {
        // Riga di intestazione con valori specificati nelle prime celle
        writer.write(";"); 
        writer.write("PosX;600;PosY;100;CheckX;179;CheckY;86;");
        writer.newLine();

        // Riga con numeri delle colonne
        writer.write(";");
        for (int j = 1; j <= COLS; j++) {
            writer.write(j + ";");
        }
        writer.newLine();

        // Dati della griglia con numeri a sinistra e destra
        for (int row = 0; row < ROWS; row++) {
            writer.write((row + 1) + ";"); // Numero a sinistra della riga
            for (int col = 0; col < COLS; col++) {
                writer.write(tilesDescription[row][col] + ";");
            }
            writer.write((row + 1) + ";"); // Numero a destra della riga
            writer.newLine();
        }

        // Riga di chiusura con numeri delle colonne
        writer.write(";");
        for (int j = 1; j <= COLS; j++) {
            writer.write(j + ";");
        }
        writer.newLine();

        JOptionPane.showMessageDialog(this, "Map saved successfully.");
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Errore nel salvataggio della mappa.", "Errore", JOptionPane.ERROR_MESSAGE);
    }
}
    private String tileCodeToSymbol(int tileCode) {
        switch (tileCode) {
            case 0: return "F";
            case 1: return "T";
            case 2: return "S";
            case 4: return "F\\T";
            case 5: return "F/T";
            case 6: return "T\\F";
            case 7: return "T/F";
            case 8: return "B-F";
            case 9: return "F-B";
            case 10: return "B|F";
            case 11: return "F|B";
            case 12: return "B/F";
            case 13: return "F\\B";
            case 14: return "B\\F";
            case 15: return "F/B";
            default: return "F"; // Default to "Field"
        }
    }

    private class GridButtonListener implements ActionListener {
        private final int row;
        private final int col;

        public GridButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = gridButtons[row][col];
            button.setIcon(tilesImage[selectedTileIndex]);
            button.putClientProperty("tileIndex", selectedTileIndex); // Store the selected tile index
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MapBuilder::new);
    }
}
