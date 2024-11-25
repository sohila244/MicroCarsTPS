package com.mycompany.microcars;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ColorChanger extends JFrame {
    private JComboBox<String> carSelector;
    private JComboBox<String> colorSelector;
    private JTextField nameField;
    private JButton saveButton;
    private JLabel previewLabel;
    private List<String> availableCars = new ArrayList<>();
    private BufferedImage selectedCarImage;
    private String folderpath = "data/img/";

    private Runnable onCloseCallback;

    public ColorChanger() {
        this.onCloseCallback = onCloseCallback;

        setLayout(new BorderLayout());
        setTitle("Color Changer Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (onCloseCallback != null) {
                    onCloseCallback.run();
                }
            }
        });

        // Carica l'elenco delle auto disponibili
        BasicOptionsChoicePanel.loadCarsFromFolder(folderpath, availableCars); // Modifica qui!

        // Pannello superiore con controlli
        JPanel topPanel = new JPanel(new FlowLayout());
        carSelector = new JComboBox<>(availableCars.toArray(new String[0]));
        colorSelector = new JComboBox<>(new String[]{"Red", "Green", "Blue", "Yellow", "Orange", "Purple", "Pink", "Cyan", "Magenta", "Gray", "Black", "White"});
        nameField = new JTextField(10);
        saveButton = new JButton("Salva");

        topPanel.add(new JLabel("Scegli auto:"));
        topPanel.add(carSelector);
        topPanel.add(new JLabel("Scegli colore:"));
        topPanel.add(colorSelector);
        topPanel.add(new JLabel("Nome auto:"));
        topPanel.add(nameField);
        topPanel.add(saveButton);

        add(topPanel, BorderLayout.NORTH);

        previewLabel = new JLabel();
        add(previewLabel, BorderLayout.CENTER);

        saveButton.addActionListener(new SaveButtonListener());

        updatePreview();
        carSelector.addActionListener(e -> updatePreview());
        colorSelector.addActionListener(e -> updatePreview());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updatePreview() {
        String selectedCar = (String) carSelector.getSelectedItem();
        String selectedColor = (String) colorSelector.getSelectedItem();

        if (selectedCar != null) {
            try {
                selectedCarImage = ImageIO.read(new File(folderpath + selectedCar + "Car.png"));
                BufferedImage previewImage = changeColor(selectedCarImage, selectedColor);
                previewLabel.setIcon(new ImageIcon(previewImage));
            } catch (IOException e) {
                Logger.getLogger(ColorChanger.class.getName()).log(Level.SEVERE, "Immagine non trovata.", e);
            }
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String color = (String) colorSelector.getSelectedItem();
            String baseCarName = (String) carSelector.getSelectedItem();
            String customName = nameField.getText().trim();

            if (customName.isEmpty()) {
                JOptionPane.showMessageDialog(ColorChanger.this, "Inserisci un nome per la nuova auto.");
                return;
            }

            try {
                BufferedImage newCarImage = changeColor(selectedCarImage, color);
                File carFile = new File(folderpath + customName + "Car.png");
                ImageIO.write(newCarImage, "png", carFile);

                BufferedImage fireImage = ImageIO.read(new File(folderpath + baseCarName + "Fire.png"));
                BufferedImage newFireImage = changeColor(fireImage, color);
                File fireFile = new File(folderpath + customName + "Fire.png");
                ImageIO.write(newFireImage, "png", fireFile);

                JOptionPane.showMessageDialog(ColorChanger.this, "Auto salvata con successo come " + customName + "Car.png e " + customName + "Fire.png");
            } catch (IOException ex) {
                Logger.getLogger(ColorChanger.class.getName()).log(Level.SEVERE, "Errore nel salvataggio dell'auto.", ex);
                JOptionPane.showMessageDialog(ColorChanger.this, "Errore nel salvataggio dell'auto.");
            }
        }
    }

    private BufferedImage changeColor(BufferedImage original, String color) {
        BufferedImage coloredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = coloredImage.createGraphics();
        g2d.drawImage(original, 0, 0, null);

        Color newColor;
        float alpha = 0.5f;
        switch (color.toLowerCase()) {
            case "red": newColor = new Color(1f, 0f, 0f, alpha); break;
            case "green": newColor = new Color(0f, 1f, 0f, alpha); break;
            case "blue": newColor = new Color(0f, 0f, 1f, alpha); break;
            case "yellow": newColor = new Color(1f, 1f, 0f, alpha); break;
            case "orange": newColor = new Color(1f, 0.5f, 0f, alpha); break;
            case "purple": newColor = new Color(0.5f, 0f, 0.5f, alpha); break;
            case "pink": newColor = new Color(1f, 0.75f, 0.8f, alpha); break;
            case "cyan": newColor = new Color(0f, 1f, 1f, alpha); break;
            case "magenta": newColor = new Color(1f, 0f, 1f, alpha); break;
            case "gray": newColor = new Color(0.5f, 0.5f, 0.5f, alpha); break;
            case "black": newColor = new Color(0f, 0f, 0f, alpha); break;
            case "white": newColor = new Color(1f, 1f, 1f, alpha); break;
            default: newColor = new Color(1f, 1f, 1f, alpha);
        }

        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.setColor(newColor);
        g2d.fillRect(0, 0, original.getWidth(), original.getHeight());
        g2d.dispose();
        
        return coloredImage;
    }
}
