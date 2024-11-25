/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microcars;

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * <h2>Questa classe compone il display di reporting sui dati di gioco - <u style="color:green;">Es. 3</u></h2><br>
 * Esso é poi reso disponibile alla classe che la invoca ({@link com.mycompany.microcars.MainGamePanel MainGamePanel})<br> 
 * <br>
 * Per ora, sono implementati solo Stato di Salute (<strong>integrity</strong>) della macchina e giri da essa percorsi (<strong>lap</strong>).<br>
 * L'interfaccia é rudimentale. Meriterebbe d'essere migliorata<br>
 * <br>
 * <Strong style="color:green;">Esercizio 3</strong>: Migliorare la resa grafica dell'interfaccia, introducendo una barra verticale animata, segmentata e colorata per rappresentare l'integrità<br> 
 * 
 * @author Giulio Frandi
 * @version 1.0
 * 
 */
public final class Display extends Component{
    
    /** Immagine del display con i dati di gioco da mostrare al giocatore */
    private final BufferedImage dImg;
    /**
     * Il costruttore prepara l'immagine <strong>BufferedImage</strong> per l'area da disegnare e richiama la funzione di update della stessa<br>
     * 
     * @param integrity int compreso tra 0 e 100, rappresentante lo stato di salute della macchinina
     * @param lap int che cnta i giri di circuito percorsi dalla macchinina fino all'istante corrente
     * 
     */
    public Display(int integrity, int lap){
        
        dImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        updateDisplay(integrity, lap);
    
    }
    /**
     * Metodo che aggiorna il display, disegnandovi un rettagolo nero e poi scrivendovi i dati di interesse in bianco<br>
     * 
     * @param integrity int compreso tra 0 e 100, rappresentante lo stato di salute della macchinina
     * @param lap int che cnta i giri di circuito percorsi dalla macchinina fino all'istante corrente
     * 
     */
    public void updateDisplay(int integrity, int lap){
        
        Graphics2D g = dImg.createGraphics();
        g.setColor(BLACK);
        g.fillRect(0, 0, 100, 100);
        g.setColor(WHITE);
        g.drawString("Integrity: "+integrity, 10, 20);
        if(lap >= 0)
            g.drawString("Lap: "+lap, 10, 35);
    
    }
    /**
     * Metodo getter per l'immagine del display
     * 
     * @return BufferedImage con l'ultima immagine del display aggiornata
     * 
     */
    public BufferedImage getImage(){
        
        return dImg;
    
    }
     /**
     * Override del metodo paint per disegnare l'immagine del display (<strong>this.dImg</strong>)<br>
     * 
     * @param g Graphics proveniente dal contesto ove disegnare il display, renderizzato a partire da (<strong>this.dImg</strong>)
     * 
     */
    @Override
    public void paint(Graphics g) {
    
        g.drawImage(dImg, 0, 0, null);
    
    }
    
}
