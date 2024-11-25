/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microcars;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.logging.*;
import javax.swing.*;

/**
 * <h2>Questa classe fornisce la grafica e la logica esecutiva per la scelta delle opzioni introduttive di gioco - <u style="color:green;">Es. 1</u></h2>
 * 
 * Essa estende ed eredita da JPanel ed implementa ActionListener<br>
 * <br>
 * Vi sono utilizzati, anche a titolo di esempio dei JLabel e dei JButton.<br>
 * <br>
 * Per una trattazione sistematica del loro utilizzo, vedasi, oltre alla documentazione java fruibile online, ad esempio anche:<br>
 * <Strong>Syntax. Informatica per gli Istituti Tecnici Tecnologi - Tomo B - Edizioni Atlas</strong><br>
 * La ristampa numero 10 del Gennaio 2023 descrive tali elementi al <Strong>Capitolo 4 - Interfacce Grafiche</Strong><br>
 * I JButton fanno, naturalmente, leva sugli elementi base della Programmazione per Eventi (ActionListener, actionPerformed ed ActionEvent)<br>
 * Lo stesso testo di cui sopre ne offre una esauriente trattazione introduttiva al <Strong>Capitolo 5 - Programmazione Guidata Dagli Eventi</Strong><br>
 * 
 * @author Giulio Frandi
 * @version 1.0<br>
 */
public class BasicOptionsChoicePanel extends JPanel implements ActionListener{
    
    /** Copia locale del Frame in cui é aperto questo JPanel */
    private JFrame parentFrame;
    /** Immagini dei circuiti*/
    private CircuitImage basicTrackImage, eightTrackImage ,triangleTrackImage; 
    /** Macchinina scelta dall'utente */
    private String choosenCar;
    /** Percorso scelto dall'utente */
    private String choosenTrack;
    /** messaggio che trasferisce il Set-Up ritenuto dal giocatore a <a href="basicoptionschoiceframe.html">BasicOptionsChoiceFrame</a>, JFrame (thread) di gioco<br>
     *  Formato: <i>choosenTrack</i>+";"+<i>choosenCar</i><br>
     */
    private AlertMessage m; 
    
    /**
     * 
     * Il costruttore crea una griglia di 3 righe x 4 colonne, vi disegna la GUI in modalita Single Player ed attiva gli ascoltatori. 
     * <br><br>
     * Nella prima riga trovano posto:<br>
     * <ol>
     * <li>un JLabel che informa l'utente sul numero IP della propria macchina<br>
     * <li>un JLabel a marcaposto della funzione di selezione ruolo client/server ancora da sviluppare (finalizzata al multiplayer)<br>
     * <li>un JLabel a marcaposto della funzione di connessione client/server ancora da sviluppare (finalizzata al multiplayer)<br>
     * <li>un JButton finalizzato ad iniziare il gioco.<br>
     * </ol>
     * <br>
     * Nella seconda riga trovano posto:<br>
     * <ol>
     * <li>un JButton per selezionare la macchina "cyan", comunque proposta di default;<br>
     * <li>un JButton per selezionare la macchina "red";<br>
     * <li>un JButton per selezionare la macchina "green";<br>
     * <li>un JLabel a marcaposto di uno spazio vuoto per permettere all'utente di inserire una nuova automobilina.<br>
     * </ol>
     * <br>
     * Nella terza riga trovano posto:<br>
     * <ol>
     * <li>un JButton per selezionare il tracciato "basic", comunque proposto di default;<br>
     * <li>un JButton per selezionare il tracciato "eight",<br>
     * <li>due JLabel a marcaposto di altrettanti spazi vuoti per permettere all'utente di inserire due nuovi percorsi.<br>
     * </ol>
     * <br>
     * Ad ogni elemento attivo, é associato un ActionListener, che intercetta le azioni utente e le passa all'Ascoltatore (vedi sotto)<br>
     * <br>
     * <img src="../../../resources/BasicOptionChoicePanelScreenshot.png" alt="Screenshot del Pannello di Selezione Opzioni di Gioco"><br>
     * <br> 
     * <Strong style="color:green;">Esercizio 1:</strong> Aggiungere una nuova automobilina, di un colore a scelta<br>
     * <strong>NB</strong>: per evitare che il nuovo elemento sia intercettato dal rilevatore di contatto, che studieremo in seguito,<br>
     * é possibile partire da una copia dell'immagine <strong>"data\img\cyanCar.png"</strong>, modificandone il colore prestando attenzione  a sceglierne uno mai usato nel gioco<br>
     * Ricordarsi di generare anche il png della macchinina in fiamme, partendo da una copia di <strong>"data\img\cyanFire.png"</strong> e ricorolandola opportunamente<br>
     * In realtà, prestando attenzione alla scelta dei colori, nulla vieta di cambiare completamente la forma dell'immagine della macchina<br>
     * <br>
     * @param m messaggio che trasferisce il Set-Up ritenuto dal giocatore a {@link com.mycompany.microcars.MainGameFrame MainGameFrame}, JFrame (thread) di gioco<br>
     * <br>
     * Oltre ai meccanismi di controllo per gestire la mutua esclusione nella comunicazione tra processi, esso porta una stringa con <strong>sintassi</strong>: <i>choosenTrack</i>+";"+<i>choosenCar</i><br>
     * <ol>
     * <li><em>choosenTrack</em> viene dall'omonima variabile, aggiornata dal gestore degli eventi e contiene l'identificativo del percorso (es. basic, eight, etc.)<br>
     * <li><em>choosenCar</em> viene dall'omonima variabile, aggiornata dal gestore degli eventi e contiene l'identificativo del colore auto (es. cyan, red, green, etc.)<br>
     * </ol>
     * <br>
     * @param parentFrame il passaggio del JFrame di contesto al JPanel si rivela necessario per poterlo chiudere<br>
     * <strong>Nota</strong>: il passaggio del contesto ad una sua parte non é buona prassi di programmazione. Capire se si può migliorare.
     *
     */
    public BasicOptionsChoicePanel(AlertMessage m, JFrame parentFrame){
        this.m = m;
        this.parentFrame = parentFrame;

        // Crea una griglia di 3 righe per quattro colonne, che si riempirà ad ogni elemento grafico successivamente istanziato
        setLayout(new GridLayout(3,4));
        try {
            // Questa sezione crea una scritta (JLabel), concatenando una stringa costante ed una generata a partire dal numero IP
            // Posiziona tale scritta al centro dello spazio di riferimento
            // Quando tutto é pronto, essa viene poi aggiunta al JPanel
            JLabel ip = new JLabel("Ip number = "+InetAddress.getLocalHost().getHostAddress());
            ip.setHorizontalAlignment(SwingConstants.CENTER);
            add(ip);
            
            // Questa sezione crea una scritta (JLabel) marcaposto per funzioni future e la aggiunge al JPanel
            JLabel empty2 = new JLabel("Client / Server (To Be Done)");
            empty2.setHorizontalAlignment(SwingConstants.CENTER);
            add(empty2);
            
            // Questa sezione crea una scritta (JLabel) marcaposto per funzioni future e la aggiunge al JPanel
            JLabel empty3 = new JLabel("Connect / Open Connection (To Be Done)");
            empty3.setHorizontalAlignment(SwingConstants.CENTER);
            add(empty3);
            
            // Questa sezione crea il JButton per iniziare il gioco
            // Esso include solo una semplice scritta
            // Al JButton é associato un ActionListener (vedi sotto) per produrne il comportamento desiderato
            // Quando tutto é pronto, il JButton viene aggiunto al JPanel
            JButton bPlay = new JButton("Play");
            bPlay.addActionListener(this);
            add(bPlay);
            
            // Questa sezione crea il JButton per selezionare l'automobilina "cyan"
            // Esso include sia una scritta che un'icona, ricavata dalla classe <font color="red">MicroCar</font>
            // Il testo é posizionato sotto l'immagine e centrato
            // Al JButton é associato un ActionListener (vedi sotto) per produrne il comportamento desiderato
            // Quando tutto é pronto, il JButton viene aggiunto al JPanel                        
            JButton bCar1 = new JButton("Cyan Car", new MicroCarSprite("cyan").convertToIcon());
            bCar1.setVerticalTextPosition(AbstractButton.BOTTOM);
            bCar1.setHorizontalTextPosition(AbstractButton.CENTER);
            bCar1.addActionListener(this);            
            add(bCar1);
            
            // Questa sezione crea il JButton per selezionare l'automobilina "red"      
            JButton bCar2 = new JButton("Red Car", new MicroCarSprite("red").convertToIcon());
            bCar2.setVerticalTextPosition(AbstractButton.BOTTOM);
            bCar2.setHorizontalTextPosition(AbstractButton.CENTER);
            bCar2.addActionListener(this);
            add(bCar2);            
            
            // Questa sezione crea il JButton per selezionare l'automobilina "green"
            JButton bCar3 = new JButton("Green Car", new MicroCarSprite("green").convertToIcon());
            bCar3.setVerticalTextPosition(AbstractButton.BOTTOM);
            bCar3.setHorizontalTextPosition(AbstractButton.CENTER);
            bCar3.addActionListener(this);
            add(bCar3);
            
            
            
            
            // Questa sezione é lasciata libera per permettere di aggiungere una nuova automobilina
            JLabel empty8 = new JLabel("Put here another car");
            empty8.setHorizontalAlignment(SwingConstants.CENTER);
            add(empty8);
            
            // Questa sezione crea il JButton per selezionare il percorso "basic"
            // Dapprima viene letta la dimensione dello schermo
            // Poi viene istanziata <strong>basic</stong>TrackImage, di tipo <fonr color="red">TrackImage</font> e grande 80% di esso
            // Questa viene convertita ad Icona ed impiegata per generare un JButton, utile per selezionare il percorso "basic"
            // Ad esso viene aggiunto il testo "Basic Track", sotto l'icona e centrato
            // Al JButton é associato un ActionListener (vedi sotto) per produrne il comportamento desiderato
            // Quando tutto é pronto, il JButton viene aggiunto al JPanel            
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            basicTrackImage = new CircuitImage("basic", (int) (0.8 * screenSize.getWidth()), (int) (0.8 * screenSize.getHeight()));
            JButton bTrack1 = new JButton("Basic Track", basicTrackImage.convertToIcon());
            bTrack1.setVerticalTextPosition(AbstractButton.BOTTOM);
            bTrack1.setHorizontalTextPosition(AbstractButton.CENTER);
            bTrack1.addActionListener(this);
            add(bTrack1);
           
            // Questa sezione crea il JButton per selezionare il percorso "eight"
            eightTrackImage = new CircuitImage("eight", (int) (0.8 * screenSize.getWidth()), (int) (0.8 * screenSize.getHeight()));
            JButton bTrack2 = new JButton("Eight Track", eightTrackImage.convertToIcon());
            bTrack2.setVerticalTextPosition(AbstractButton.BOTTOM);
            bTrack2.setHorizontalTextPosition(AbstractButton.CENTER);
            bTrack2.addActionListener(this);
            add(bTrack2);
            
             triangleTrackImage= new CircuitImage("triangle", (int) (0.8 * screenSize.getWidth()), (int) (0.8 * screenSize.getHeight()));
            JButton bTrack3 = new JButton("Triangle Track", triangleTrackImage.convertToIcon());
            bTrack3.setVerticalTextPosition(AbstractButton.BOTTOM);
            bTrack3.setHorizontalTextPosition(AbstractButton.CENTER);
            bTrack3.addActionListener(this);
            add(bTrack3);
            
            // Questa sezione é lasciata libera per permettere di aggiungere un nuovo percorso
            JLabel empty11 = new JLabel("Custom Track (To Be Done)");
            empty11.setHorizontalAlignment(SwingConstants.CENTER);
            add(empty11);
            
            // Questa sezione é lasciata libera per permettere di aggiungere un nuovo percorso
            JLabel empty12 = new JLabel("Custom Track (To Be Done)");
            empty12.setHorizontalAlignment(SwingConstants.CENTER);
            add(empty12);
            
            // Settaggio parametri di gioco di default, se l'utente non ne seleziona di diversi prima di cominciare a giocare
            choosenCar = "cyan";                //macchina "cyan"
            choosenTrack = "basic";             //track "basic"
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(BasicOptionsChoiceFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Override del metodo standard actionPerformed di Actionlistener per realizzare
     * il gestore degli eventi associati ai pulsanti creati in questo JPanel.
     * <br><br>
     * In caso sia stata selezionata una macchinina, aggiorna opportunamente la variabile choosenCar<br>
     * In caso sia stato selezionato un percorso, aggiorna opportunamente sia la variabile choosenTrack che il suo setUp<br>
     * <br>
     * La selezione del pulsante "play" ingenera:<br>
     * <ol>
     * <li>la sparizione del JFrame in cui questo JPanel é contenuto setVisible(false)<br>
     * <li>la costruzione del messaggio m e la sua resa disponibile al Frame di gioco<br>
     * </ol>
     * <br>
     * L'impiego della classe {@link com.mycompany.microcars.AlertMessage AlertMessage}, mettendo un messaggio a disposizione:<br>
     * <ol>
     * <li>risveglia {@link com.mycompany.microcars.MainGameFrame MainGameFrame} JFrame di gioco vero e proprio<br>
     * <li>gli trasferisce le informazioni di set-up<br>
     * <li>dispone del JFrame (thread) corrente<br> 
     * </ol>
     * <br>
     * @param e ActionEvent che porta il pulsante premuto.<br> 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();
        switch (buttonPressed) {
            case "Play" -> {
                m.setMessage(choosenTrack+";"+choosenCar);
                parentFrame.dispose();
            }
            case "Cyan Car" -> choosenCar = "cyan";
            case "Red Car" -> choosenCar = "red";
            case "Green Car" -> choosenCar = "green";
            case "Basic Track" -> choosenTrack = "basic";
            case "Eight Track" -> choosenTrack = "eight";
            default -> {
            }
        }
        
    }

}
