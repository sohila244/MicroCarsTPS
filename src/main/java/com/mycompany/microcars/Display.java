package com.mycompany.microcars;

import static java.awt.Color.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
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
 */
public final class Display extends Component {
    
    private final BufferedImage dImg;
    private int currentIntegrity;
    private int currentLap;

    /**
     * Il costruttore prepara l'immagine <strong>BufferedImage</strong> per l'area da disegnare e richiama la funzione di update della stessa.
     * 
     * @param integrity int compreso tra 0 e 100, rappresentante lo stato di salute della macchinina
     * @param lap int che conta i giri di circuito percorsi dalla macchinina fino all'istante corrente
     */
    public Display(int integrity, int lap) {
        dImg = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
        updateDisplay(integrity, lap);
    }

    /**
     * Metodo che aggiorna il display, disegnandovi un rettangolo nero e poi scrivendovi i dati di interesse in bianco.
     * 
     * @param integrity int compreso tra 0 e 100, rappresentante lo stato di salute della macchinina
     * @param lap int che conta i giri di circuito percorsi dalla macchinina fino all'istante corrente
     */
    public void updateDisplay(int integrity, int lap) {
        currentIntegrity = integrity;
        currentLap = lap;
        
        Graphics2D g = dImg.createGraphics();
        g.setColor(BLACK);
        g.fillRect(0, 0, dImg.getWidth(), dImg.getHeight());

        // Disegna le informazioni sul display
        g.setColor(WHITE);
        g.drawString("Integrity: " + currentIntegrity, 10, 20);
        g.drawString("Lap: " + currentLap, 10, 40);

        // Disegna la barra di integrità
        drawIntegrityBar(g);

        g.dispose();
    }

    /**
     * Metodo che disegna una barra verticale segmentata e colorata in base all'integrità
     */
    private void drawIntegrityBar(Graphics2D g) {
        int barWidth = 20;
        int barHeight = 100;
        int segments = 10;
        int segmentHeight = barHeight / segments;

        // Calcola il numero di segmenti da colorare
        int filledSegments = (currentIntegrity * segments) / 100;

        // Calcola le coordinate per centrare la barra
        int xCenter = (dImg.getWidth() - barWidth) / 2; // Centra la barra orizzontalmente
        int yStart = (dImg.getHeight() - barHeight) / 2; // Centra la barra verticalmente

        for (int i = 0; i < segments; i++) {
            if (i < filledSegments) {
                // Colora i segmenti in base al livello di integrità
                if (currentIntegrity > 70) {
                    g.setColor(GREEN);
                } else if (currentIntegrity > 30) {
                    g.setColor(YELLOW);
                } else {
                    g.setColor(RED);
                }
            } else {
                g.setColor(DARK_GRAY); // Segmenti vuoti
            }
            g.fillRect(xCenter, yStart + i * segmentHeight, barWidth, segmentHeight - 2);
        }

        // Disegna un bordo bianco attorno alla barra
        g.setColor(WHITE);
        g.drawRect(xCenter, yStart, barWidth, barHeight);
    }

    /**
     * Metodo getter per l'immagine del display
     * 
     * @return BufferedImage con l'ultima immagine del display aggiornata
     */
    public BufferedImage getImage() {
        return dImg;
    }

    /**
     * Override del metodo paint per disegnare l'immagine del display (<strong>this.dImg</strong>).
     * 
     * @param g Graphics proveniente dal contesto ove disegnare il display, renderizzato a partire da (<strong>this.dImg</strong>)
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(dImg, 0, 0, null);
    }
}
