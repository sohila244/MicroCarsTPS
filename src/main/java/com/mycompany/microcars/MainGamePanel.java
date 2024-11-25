package com.mycompany.microcars;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 *
 * <h2>Questa classe gestisce sia la grafica che la logica di gioco vero e proprio</h2>
 * 
 * @author Giulio Frandi
 * @version 1.0
 * 
 */
public class MainGamePanel extends JPanel{
    
    /** Dimensioni Orizzontale e Verticale del Frame di Gioco */
    private int fWidth, fHeight;
    /** Immagine del Percorso, con la sua logica */
    private CircuitImage trackImage;
    /** Immagine della macchinina, con la sua logica */
    private MicroCarSprite myCar;
    /** Immagine del Cruscotto, con la sua logica */
    private Display myDisplay;
    //private AlertMessage onGameOverChoice;
    /**
     * Il costruttore richiede in input i dati di set-up e la dimensione del frame in cui disegnare il gioco <br>
     * Successivamente, istanzia:<br>
     * <strong>-</strong><a href="CircuitImage.html">percorso</a>, <a href="MicroCarSprite.html">macchinina</a> e <a href="Display.html">cruscotto</a>.<br>
     * <strong>-</strong>Listener ed Handler per i tasti premuti dall'utente<br>
     * <strong>NB</strong>: la struttura dati per i <a href="dangers.html">pericoli</a> é qui generata a cascata dal costruttore del <a href="CircuitImage.html">percorso</a>.<br>
     * <br>
     * <img src="../../../resources/01 Class Diagram (simplified).jpg" alt="Diagramma delle classi (Sempificato)"><br>
     * <br>
     * Il risultato, compone la seguente finestra di gioco:<br>
     * <img src="../../../resources/MainGamePanelScreenshot.png" alt="Screenshot dello Schermo di Gioco"><br>
     * <br>
     * 
     * @param setUpMessage String con i parametri di set-up. Vedi sintassi in <a href="BasicOptionsChoicePanel.html">BasicOptionsChoicesPanel</a>
     * @param fWidth int con Dimensione Orizzontale Frame
     * @param fHeight int con Dimensione Verticale Frame
     * 
     */
    public MainGamePanel(String setUpMessage, int fWidth, int fHeight){
        
        this.fWidth = fWidth;
        this.fHeight = fHeight;
        trackImage = new CircuitImage(setUpMessage.split(";", 2)[0], fWidth, fHeight);
        myCar = new MicroCarSprite(setUpMessage.split(";", 2)[1], trackImage.getStartPositionX(), trackImage.getStartPositionY(), fWidth, fHeight);
        myDisplay = new Display(myCar.getIntegrity(), myCar.getLap());
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if(e.getID() == KeyEvent.KEY_PRESSED){
                    handleKeyPress(e.getKeyCode(), myCar);
                    repaint();
                }
                return false;
            }
        });     
        
    }
    /**
     * Questo metodo contiene la logica di gioco<br>
     * <br>
     * Fintantoché la macchinina non é distrutta:<br>
     * -la muove,<br>
     * -verifica se é fuori pista e nel caso la ammacca o la distrugge proprio se si schianta contro i bordi,<br>
     * -verifica se sta tocando un ostacolo e nel caso la ammacca,<br>
     * -verifica se ha compiuto un giro completo (e poi se é passata vicino al checkpoint),<br>
     * -aggiorna la situazione degli ostacoli sul percorso e lo ridisegna di conseguenza,<br>
     * -a fine ciclo é poi inserita una funzione di attesa perché il tutto non avvenga troppo velocemente.<br>
     * <br>
     * <img src="../../../resources/Diagramma Attività CircuitImage.playGame.jpg" alt="Diagramma delle attività del Ciclo di Gioco"><br>
     * <br>
     * Quando la macchina é distrutta, viene proposto al giocatore la scelta di ricominciare il gioco o di abbandonarlo.<br>
     * Nel caso egli voglia ricominciare:<br>
     * -viene settato opportunamente <strong>onGameOverChoice</strong>, anche riattivando <a href="AMicroCarsGame.html">AMicroCarsGame</a> che lo stava aspettando<br>
     * -viene disposta la chiusura del JFrame padre <a href="MainGameFrame.html">MainGameFrame</a><br>
     * 
     * @param parentFrame il passaggio del JFrame di contesto al JPanel si rivela necessario per poterlo chiudere<br>
     * <strong>Nota</strong>: il passaggio del contesto ad una sua parte non é buona prassi di programmazione. Capire se si può migliorare.
     * @param onGameOverChoice messaggio che trasferisce la scelta di giocare ancora o meno a <a href="AMicroCarsGame.html">AMicroCarsGame</a><br>
     * <strong> Sintassi</strong>:Il metodo trasferisce "playAgain" per indicare che il giocatore desidera provare un'algtra partita<br>
     * @throws InterruptedException N/A
     */
    public void playGame(JFrame parentFrame, AlertMessage onGameOverChoice) throws InterruptedException{

        while(myCar.isOperational()){
            myCar.move();
            if(myCar.isOutOfTracks(trackImage.getImage())){
                myCar.hurt();
                if(myCar.isTouchingBorders(trackImage.getImage()))
                    myCar.destroy();
            }
            if(myCar.isTouchingHazard(trackImage.getImage()))
                myCar.hurt();
            myCar.checkForCompletedLap(trackImage.getImage());
            myCar.checkAgainstShortLap(trackImage.getCheckPointX(), trackImage.getCheckPointY());
            trackImage.updateDangersSituation(myCar);
            trackImage.rebuildImageFromTiles();
            repaint();
            Thread.sleep(75);
        }
        
        int checkIfPlayAgain = JOptionPane.showConfirmDialog(null, "Vuoi giocare ancora?", null, JOptionPane.YES_NO_OPTION);
        if(checkIfPlayAgain == JOptionPane.YES_OPTION){
            onGameOverChoice.setMessage("playAgain");
            parentFrame.dispose();
        }
        else
            System.exit(0);
        
    } 
    /**
     * 
     * Override del metodo di paint che assembla:<br>
     * <strong>-</strong>il <a href="CircuitImage.html">percorso</a>, inclusi i <a href="dangers.html">pericoli</a><br>
     * <strong>-</strong>la <a href="MicroCarSprite.html">macchinina</a><br>
     * <strong>-</strong>il <a href="Display.html">cruscotto</a> dopo averne ricalcolato i parametri<br>
     * 
     * @param g <strong>Graphics</strong> nel quale disegnare gli elementi di gioco<br>
     * 
     */    
    @Override
    public void paint (Graphics g){
        
        g.drawImage(trackImage.getImage(), 0, 0, this);
        g.drawImage(myCar.getImage(), myCar.getPosX(), myCar.getPosY(), this);
        myDisplay.updateDisplay(myCar.getIntegrity(), myCar.getLap());
        g.drawImage(myDisplay.getImage(), (int)(this.fWidth * 0.85-10), (int)(this.fHeight * 0.1-20), this);

    }
    
    private static void handleKeyPress(int key, MicroCarSprite car){
        
        if(key == KeyEvent.VK_UP){
            car.accelerate();
        }
        if(key == KeyEvent.VK_RIGHT){
            car.rotateClockWise();
        }
        if(key == KeyEvent.VK_LEFT){
            car.rotateCounterClockWise();
        }
        if(key == KeyEvent.VK_DOWN){
            car.decelerate();
        }
        
    }

}
 