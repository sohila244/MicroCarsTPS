package com.mycompany.microcars;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.logging.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.io.File;

/**
 * <h2>Questa classe fornisce la grafica e la logica esecutiva per la scelta delle opzioni introduttive di gioco - <u style="color:green;">Es. 1</u></h2>
 * 
 * Essa estende ed eredita da JPanel ed implementa ActionListener<br>
 * 
 * @author Giulio Frandi
 * @version 1.0<br>
 */
public class BasicOptionsChoicePanel extends JPanel implements ActionListener {
    
    private JFrame parentFrame;
    public List<String> maps = new ArrayList<>(); 
    private String choosenCar;
    private String choosenTrack;
    private AlertMessage m;
    private List<String> cars = new ArrayList<>();
    private int carIndex1 = 0, carIndex2 = 1;
    private int trackIndex = 0;
    private JButton carButton1, carButton2;
    private JButton trackButton;
    private JButton customTrackButton;
    private final Border blueBorder = new LineBorder(Color.BLUE, 2);
    private final Border defaultBorder = new JButton().getBorder(); 

    /**
     * Il costruttore crea la GUI ed attiva gli ascoltatori.
     */
    public BasicOptionsChoicePanel(AlertMessage m, JFrame parentFrame) {
        this.m = m;
        this.parentFrame = parentFrame;
        setLayout(new GridLayout(3, 4));

        loadCarsFromFolder("data/img/", cars);
        loadMapsFromFolder("data/tracks/");
        
        try {
            JLabel ip = createLabel("Ip number = " + InetAddress.getLocalHost().getHostAddress());
            add(ip);

            JLabel empty2 = createLabel("Client / Server (To Be Done)");
            add(empty2);

            JButton carc = createButton("CAR COLOR");
            carc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openColorChanger();
                }
            });
            add(carc);

            JButton bPlay = createButton("Play");
            add(bPlay);

            JButton leftArrowCar = createButton("<");
            leftArrowCar.setActionCommand("< Car");
            add(leftArrowCar);  

            carButton1 = new JButton("Cyan Car", new MicroCarSprite(cars.get(carIndex1)).convertToIcon());
            carButton1.setVerticalTextPosition(AbstractButton.BOTTOM);
            carButton1.setHorizontalTextPosition(AbstractButton.CENTER);
            carButton1.addActionListener(this);
            add(carButton1);

            carButton2 = new JButton("Red Car", new MicroCarSprite(cars.get(carIndex2)).convertToIcon());
            carButton2.setVerticalTextPosition(AbstractButton.BOTTOM);
            carButton2.setHorizontalTextPosition(AbstractButton.CENTER);
            carButton2.addActionListener(this);
            add(carButton2);

            JButton rightArrowCar = createButton(">");
            rightArrowCar.setActionCommand("> Car");
            add(rightArrowCar);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            
            JButton leftArrowTrack = createButton("<");
            leftArrowTrack.setActionCommand("< Track");
            add(leftArrowTrack);

            trackButton = new JButton(capitalize(maps.get(trackIndex)) + " Track", new CircuitImage(maps.get(trackIndex), (int) (0.8 * screenSize.getWidth()), (int) (0.8 * screenSize.getHeight())).convertToIcon());
            trackButton.setVerticalTextPosition(AbstractButton.BOTTOM);
            trackButton.setHorizontalTextPosition(AbstractButton.CENTER);
            trackButton.addActionListener(this);
            add(trackButton);

            JButton rightArrowTrack = createButton(">");
            rightArrowTrack.setActionCommand("> Track");
            add(rightArrowTrack);

            customTrackButton = createButton("Crea la tua mappa");     
            add(customTrackButton);

            choosenCar = cars.get(0);               
            choosenTrack = "basic";             
            updateBorders();  
            updateCarButtons();

        } catch (UnknownHostException ex) {
            Logger.getLogger(BasicOptionsChoiceFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        return button;
    }

    public static void loadCarsFromFolder(String folderPath, List<String> availableCars) {
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isFile() && file.getName().endsWith("Car.png")) {
                    String carName = file.getName().replace("Car.png", "");
                    if (!availableCars.contains(carName)) {
                        availableCars.add(carName);
                    }
                }
            }
        }
    }
    
    private void loadMapsFromFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isFile() && file.getName().endsWith("Track.csv")) {
                    String mapName = file.getName().replace("Track.csv", "");
                    if (!maps.contains(mapName)) {
                        maps.add(mapName);
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();

        switch (buttonPressed) {
            case "< Car" -> {
                carIndex1 = (carIndex1 - 1 + cars.size()) % cars.size();
                carIndex2 = (carIndex2 - 1 + cars.size()) % cars.size();
                updateCarButtons();
            }
            case "> Car" -> {
                carIndex1 = (carIndex1 + 1) % cars.size();
                carIndex2 = (carIndex2 + 1) % cars.size();
                updateCarButtons();
            }
            case "< Track" -> {
                trackIndex = (trackIndex - 1 + maps.size()) % maps.size();
                updateMapButton();
            }
            case "> Track" -> {
                trackIndex = (trackIndex + 1) % maps.size();
                updateMapButton();
            }
            case "Play" -> {
                choosenTrack = maps.get(trackIndex);
                m.setMessage(choosenTrack + ";" + choosenCar);
                parentFrame.dispose();
            }
            case "Crea la tua mappa" -> openMapCreator();
            default -> {
                for (String car : cars) {
                    if (buttonPressed.equalsIgnoreCase(car + " Car")) {
                        choosenCar = car;
                        break;
                    }
                }
            }
        }
        updateBorders();
    }

    private void openMapCreator() {
        MapBuilder trackBuilder= new MapBuilder();
        trackBuilder.setVisible(true);
        trackBuilder.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loadMapsFromFolder("data/tracks/");
                updateMapButton();
            }
        });
    }

    private void openColorChanger() {
        ColorChanger colorChanger = new ColorChanger();
        colorChanger.setVisible(true);

        colorChanger.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                
                loadCarsFromFolder("data/img/",cars);
                // Handle actions when ColorChanger is closed
                // Example: Update the car color in the panel, if needed
            }
        });
    }

    private void updateCarButtons() {
        carButton1.setText(capitalize(cars.get(carIndex1)) + " Car");
        carButton1.setIcon(new MicroCarSprite(cars.get(carIndex1)).convertToIcon());
        carButton2.setText(capitalize(cars.get(carIndex2)) + " Car");
        carButton2.setIcon(new MicroCarSprite(cars.get(carIndex2)).convertToIcon());
        updateBorders();
    }

    private void updateMapButton() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        trackButton.setText(capitalize(maps.get(trackIndex)) + " Track");
        trackButton.setIcon(new CircuitImage(maps.get(trackIndex), (int) (0.8 * screenSize.getWidth()), (int) (0.8 * screenSize.getHeight())).convertToIcon());
        updateBorders();
    }

    private void updateBorders() {
        carButton1.setBorder(choosenCar.equals(cars.get(carIndex1)) ? blueBorder : defaultBorder);
        carButton2.setBorder(choosenCar.equals(cars.get(carIndex2)) ? blueBorder : defaultBorder);
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
