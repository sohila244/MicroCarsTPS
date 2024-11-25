/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microcars;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * <h2>Questa classe apre e gestisce la finestra di dialogo per la scelta delle opzioni introduttive di gioco.</h2>
 * 
 * Essa estende ed eredita da JFrame, 
 * 
 * @author Giulio Frandi
 * @version 1.0<br>
 */
public class BasicOptionsChoiceFrame extends JFrame{
    /**
     * 
     * Il costruttore propone le classiche istruzioni per Istanziare un Frame.
     * <br><br>
     * <ol>
     * <li>Leggere la dimensione dello schermo corrente<br>
     * <li>Dimensionare il frame a 80% dello schermo corrente<br>
     * <li>Gestire, tramite evento, il gadget standard di chiusura schermo, interpretandolo anche come chiusura appicativo<br>
     * <li>Istanziare ed aggiungere {@link com.mycompany.microcars.BasicOptionsChoicePanel BasicOptionsChoicePanel}, un JPanel che visualizza le opzioni dipsonibili all'utente e ne gestisce le scelte<br>
     * </ol>
     * <br>
     * L'unica novità "non standard" é rappresentata dal parametro {@link com.mycompany.microcars.AlertMessage AlertMessage} <Strong>m</strong> (vedi sotto).
     * 
     * @param m Messaggio che porta con se le scelte di configurazione, trasmesso dal chiamante {@link com.mycompany.microcars.AMicroCarsGame AMicroCarsGame} 
     * e passato a {@link com.mycompany.microcars.BasicOptionsChoicePanel BasicOptionsChoicePanel}<br>
     * Per una sua descrizione più precisa, vedasi sintasi proprio in {@link com.mycompany.microcars.BasicOptionsChoicePanel BasicOptionsChoicePanel}<br>
     */
    public BasicOptionsChoiceFrame(AlertMessage m){
        
        JFrame f = new JFrame("MicroCars - SetUp");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize((int) (0.8 * screenSize.getWidth()), (int) (0.8 * screenSize.getHeight()));
        //Gestione del gadget di chiusura finestra. Override del metodo standard che con funzione aggiuntiva di fine applicazione
        f.addWindowListener(new WindowAdapter(){
            @Override 
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        //Nota: passare un oggetto chiamante ad un oggetto chiamato é un pessimo stile di programmazione
        //Per future versioni: si può fare in modo più elegante?
        JPanel p = new BasicOptionsChoicePanel(m, f);  
        f.add(p);
        f.setVisible(true);
    }
}
