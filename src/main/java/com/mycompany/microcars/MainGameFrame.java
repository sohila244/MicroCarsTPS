/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microcars;

/**
 *
 * @author giuli
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

/**
 *
 * <h2>Questa classe apre e gestisce la finestra di gioco vero e proprio</h2>
 * 
 * Essa estende ed eredita da JFrame.<br>
 * 
 * @author Giulio Frandi
 * @version 1.0<br>
 */
public class MainGameFrame extends JFrame{  
     /**
     * Il costruttore propone le classiche istruzioni per:<br>
     * <strong>-</strong>Istanziare un Frame<br>
     * <strong>-</strong>Leggere la dimensione dello schermo corrente<br>
     * <strong>-</strong>Dimensionare il frame a 80% dello schermo corrente<br>
     * <strong>-</strong>Gestire, tramite evento, il gadget standard di chiusura schermo, interpretandolo anche come chiusura appicativo<br>
     * <strong>-</strong>Istanziare ed aggiungere <a href="MainGamePanel.html">MainGamePanel</a>, un JPanel che visualizza il gioco vero e proprio e gestisce l'interazione con l'utente<br>
     * <br>
     * Successivamente richiama il metodo <strong>playGame()</strong> di <a href="MainGamePanel.html">MainGamePanel</a>, che gestisce il gioco vero e proprio.<br>
     * <br>
     * L'unica novità "non standard" é rappresentata dai parametri: <strong>setUpMessage</strong> ed <strong>onGameOverChoice</strong> (vedi sotto)<br>
     * <br>
     * @param setUpMessage String con i parmetri di SetUp. Interamente trasferito a <a href="MainGamePanel.html">MainGamePanel</a> (vedi sintassi in <a href="BasicOptionsChoicePanel.html">BasicOptionsChoicePanel</a>)<br>
     * @param onGameOverChoice AlertMessage produttore/consumatore che sincronizza il JFrame corrente con il ciclo principale in <a href="AMicroCarsGame.html">AMicroCarsGame</a>,<br>
     * trasferendogli anche la decisione di giocare di nuovo o meno (vedi sintassi in <a href="MainGamePanel.html">MainGamePanel</a>)
     * @throws InterruptedException N/A
     */
    public MainGameFrame(String setUpMessage, AlertMessage onGameOverChoice) throws InterruptedException{
        
        JFrame fMG = new JFrame("MicroCars - Game");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int fWidth = (int) (0.8 * screenSize.getWidth());
        int fHeight = (int) (0.8 * screenSize.getHeight());
        fMG.setSize(fWidth, fHeight);
        fMG.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        }); 
        
        MainGamePanel pMainGame = new MainGamePanel(setUpMessage, fWidth, fHeight);
        fMG.add(pMainGame);
        fMG.setVisible(true);
        pMainGame.playGame(fMG, onGameOverChoice);
        
    }

}
