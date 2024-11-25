/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.microcars;

/**
 *
 * <h2><strong>Questa é la classe principale, con il Gioco Completo, in versione Single Player</strong></h2>
 * 
 * Essa include il main() e lancia tutte le altre<br>
 * La comprensione del suo funzionamento richiede conoscenze di IV e V IT Articolazione Informatica, ed in particolare:<br>
 * <ol>
 * <li>Programmazione Java ad Oggetti<br>
 * <li>Interfacce Grafiche<br>
 * <li>Gestione Eventi.<br>
 * </ol>
 * <br>
 * L'unica eccezione e' la classe {@link com.mycompany.microcars.AlertMessage AlertMessage}, che implementa alcuni concetti di programmazione MultiThread.<br>
 * Tuttavia, anch'essi sono solitamente oggetto del corso di IV ma usualmente trattati a TPSI.<br>
 * <br>
 * Nella versione Single Player, l'obiettivo é quello di compiere quanti più giri possibili, prima che la macchina si distrugga.<br>
 * Essa infatti, con probabilità via via crescente, in funzione dei giri percorsi, si usura, lasciando delle macchie d'olio sulla pista.<br>
 * Ogni volta che questo accade essa perde un po' della sua energia.<br>
 * Parimenti, essa si usura (e rallenta) se esce fuori-pista<br>
 * Se, al giro successivo, la vettura transita su di una macchia d'olio precedentemente persa, sbanderà.<br>
 * Con cadenza casuale, le macchie d'olio possono prendere fuoco, diventando ulteriori ostacoli sul percorso<br>
 * Se la vettura transita su di una macchia incendiata, si usura<br>
 * Una vettura usurata diminuisce proporzionalemente la sua velocità massima.<br>
 * <br>
 * Il sistema proporrà all'utente una finestra per scegliere le opzioni iniziali e poi una per il gioco vero e proprio<br>
 * <img src="../../../resources/01 Main User Case Scenario.jpg" alt="Main User Case Scenario"><br>
 * <br>
 * Paiono necessarie le seguenti classi (Diagramma Semplificato)<br>
 * <img src="../../../resources/01 Class Diagram (simplified).jpg" alt="Diagramma delle classi (Sempificato)"><br>
 * <br>
 * 
 * (Si ellega anche il Diagramma delle classi completo)<br>
 * <img src="../../../resources/02 Class Diagram (complete).jpg" alt="Diagramma delle classi (Sempificato)"><br>
 * <br>
 * 
 * 
 * @author Giulio Frandi
 * @version 1.0<br>
 * 
 */
public class AMicroCarsGame {

    private static AlertMessage configurationChoices, onGameOverChoice;

    /**
     * Dummy Constructor solo per togliere un Javadoc Warning - In questa classe ciò che conta é il metodo main.
     */
    public AMicroCarsGame(){
    
    }
    
    /**
    * 
    * La main() istanzia e fa vivere i due Frame che, insieme, costituiscono il gioco.
    * <br><br>
    * Essi sono:<br>
    * <ol>
    * <li>{@link com.mycompany.microcars.BasicOptionsChoiceFrame BasicOptionsChoiceFrame} che permette al giocatore di effettuare le scelte di inizio partita (Colore Macchina e Tracciato di Gara)<br>
    * <li>{@link com.mycompany.microcars.MainGameFrame MainGameFrame} ove si visualizza il gioco vero e proprio<br>
    * </ol>
    * <br>
    * I due frame sono chiamati in mutua esclusione e comunicano fra loro, secondo il diagramma di stato qui riprodotto.<br>
    * <img src="../../../resources/Diagramma Stati Finestre.jpg" alt="Diagramma di Stato delle Finestre"><br>
    * <br>
    * Si riporta anche il diagramma di sequenza<br>
    * <img src="../../../resources/Diagramma Sequenza Main e Finestre.jpg" alt="Diagramma di Sequenza delle Finestre"><br>
    * <br>
    * La comunicazione e' realizzata attraverso scambio di messaggi (configurationChoices e onGameOverChoice).<br>
    * I messaggi sono implementati dalla classe {@link com.mycompany.microcars.AlertMessage AlertMessage}.<br>
    * <br>
    * <strong>NB</strong>: In {@link com.mycompany.microcars.BasicOptionsChoiceFrame BasicOptionsChoiceFrame}, dello spazio é volutamente lasciato per future nuove implementazioni.
    *
    * @param args N/A
    * @throws java.lang.InterruptedException N/A
    */     
    public static void main(String[] args) throws InterruptedException {
             
        configurationChoices = new AlertMessage();
        onGameOverChoice = new AlertMessage();
        onGameOverChoice.setMessage("playAgain");
        while(onGameOverChoice.getMessage().matches("playAgain")){
            BasicOptionsChoiceFrame introOptionsFrame = new BasicOptionsChoiceFrame(configurationChoices);
            MainGameFrame trackFrame = new MainGameFrame(configurationChoices.getMessage(), onGameOverChoice); 
        }

        System.out.println("Game Over");
        
    }
}
