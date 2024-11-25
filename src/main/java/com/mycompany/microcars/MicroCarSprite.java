 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microcars;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * <h2>Questa classe gestisce lo sprite della macchinina - <u style="color:green;">Es. 4, 5, 6 e 7</u></h2>
 * 
 * @author Giulio Frandi
 * @version 1.0
 */
public class MicroCarSprite extends Component{
    
    /** Immagini della macchina. Rispettivamente: Base, Ruotata ad angolo attuale ed Infuocata */
    private BufferedImage dImg, rImg, fImg;
    /** Per ora non usata. Riservata per usi futuri (multiplayer) */
    private String carColor;
    /** Variabili di stato della macchinina: Coordinate X ed Y, angolo di rotazione, velocita attuale e massima */
    private int posX, posY, angle, speed, maxSpeed;
    /** Variabili di stato della macchinina: percentuale d'integrità e numero di giri percorsi */
    private int integrity, lap;
    /** flag di stato della macchinina per evitare che il giocatore "tagli" il percorso */
    private boolean shortLap;
    /**
     * 
     * Questa versione del costruttore si limita a prendere in conto il colore richiesto dall'utente (nella stringa <strong>carColor</strong>)<br>
     * ed a caricare in memoria l'immagine della macchinina integra dal file corrispondente: <strong>"data/img/"+carColor+"Car.png"</strong><br>
     * 
     * NB: Questa versione semplificata é utile, ad esempio, per generare l'icona della macchinina stessa.<br> 
     * 
     * @param carColor stringa con il colore della macchinina
     * 
     */
    public MicroCarSprite(String carColor){
        
        try {
            this.carColor = carColor;
            dImg = ImageIO.read(new File("data/img/"+carColor+"Car.png"));
            dImg = MicroCarSprite.resizeImage(dImg, (int) (0.3 * dImg.getWidth()), (int) (0.3 * dImg.getHeight()));
        } catch (IOException ex) {
            Logger.getLogger(MicroCarSprite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /**
     * 
     * Questa versione del costruttore prende in conto il colore richiesto dall'utente (nella stringa <strong>carColor</strong>)<br>
     * e carica in memoria a partire dal file corrispondente:<br>
     * <strong>-</strong>l'immagine della macchinina integra (<strong>"data/img/"+carColor+"Car.png"</strong>)<br>
     * <strong>-</strong>l'immagine della macchinina distrutta ed in fiamme (<strong>"data/img/"+carColor+"Fire.png"</strong>)<br>
     * Entrambe sono subito ridimensionate in funzione della grandezza del frame di gioco.<br>
     * <br>
     * Esso inoltre inizializza i principali attributi della macchinina secondo la logica di gioco:<br>
     * <strong>-</strong>La posizione secondo quanto richiesto dal contesto chiamante,<br>
     * <strong>-</strong>Velocità ed Angolo Iniziali pari a zero (peraltro ruotando la macchinina di conseguenza),<br>
     * <strong>-</strong>Integrità Strutturale al 100% e Velocità Massima ad un quinto di tale parametro<br>
     * <strong>-</strong>Giri compiuti a -1, di maniera da portarli a 0 al primo passaggio dal via (sul traguardo)<br>
     * <strong>-</strong>Parametro di controllo sul percorso non completato a false<br>
     * (Maggiori spiegazioni nel dettaglio dei metodi <strong>checkForCompletedLap()</strong> e <strong>checkAgainstShortLap()</strong>.<br>
     * <br>
     * <Strong style="color:green;">Esercizio 4:</strong> Sviluppare quanto serve per usare come parametro un vero e proprio <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html">Color</a><br>
     * invece che una <strong>stringa</strong>, permettendo all'utente di scegliere, ad esmepio, la propria macchina fra 16 colori diversi.<br>
     * Una volta sviluppati i metodi necessari in questa classe, bisognerebbe poi richiamarli opportunamente da <a href="BasicOptonPanel.html">BasicOptionPanel</a><br> 
     * <br>
     * 
     * @param carColor stringa con il colore della macchinina<br>
     * @param startPosX intero con la posizione X di partenza della macchinina nel frame di gioco<br>
     * @param startPosY intero con la posizione X di partenza della macchinina nel frame di gioco<br>
     * @param fWidth intero con la dimensione orizzontale del Frame di Gioco<br>
     * @param fHeight intero con la dimensione orizzontale del Frame di Gioco<br>
     */
    public MicroCarSprite(String carColor, int startPosX, int startPosY, int fWidth, int fHeight) {
       try {
            this.carColor = carColor;
            dImg = ImageIO.read(new File("data/img/"+carColor+"Car.png"));
            fImg = ImageIO.read(new File("data/img/"+carColor+"Fire.png"));
            dImg = MicroCarSprite.resizeImage(dImg, (int) (0.1 * fHeight), (int) (0.1 * fHeight));
            fImg = MicroCarSprite.resizeImage(fImg, (int) (0.1 * fHeight), (int) (0.1 * fHeight));
            this.posX = startPosX;
            this.posY = startPosY;
            speed = 0;
            angle = 0;
            rImg = rotate(dImg, angle);
            integrity = 100;
            maxSpeed = integrity / 5;
            lap = -1;
            shortLap = false;
       } catch (IOException e) {
       }
       
    }
    
    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
    
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
        
    }
    
    private static BufferedImage rotate(BufferedImage img, int angle){
        
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        Graphics2D g2 = newImage.createGraphics();
        g2.rotate(Math.toRadians(angle), width / 2, height / 2);
        g2.drawImage(img, null, 0, 0);
        return newImage;
        
    }
    /**
     * 
     * Questo metodo converte ad icona l'immagine della macchinina.<br>
     * 
     * @return ImageIcon contenente l'immagine della macchinina <br>
     */
    public ImageIcon convertToIcon(){
        
        return new ImageIcon(dImg);
        
    }
    /**
     * 
     * Questo metodo ritorna l'informazione sullo stato di operatività della macchinina.<br>
     * 
     * @return boolean con true se la macchinina é ancora operativa, altrimenti false<br>
     */
    public boolean isOperational(){
        
        return this.integrity > 0;
    
    }
    /**
     * 
     * Questo metodo percorre tutti i pixel della macchinina e nonppena uno di quelli non trasperenti sta toccando<br>
     * un'area verde chiaro (0xFFB5E61D) dell'immagine relativa al tracciato considera che essa sia furoi pista<br>
     * 
     * @param currentTrackImage BufferedImage del tracciato su cui la macchinina gareggia<br>
     * @return boolean con true nonappena uno dei pixel non trasparenti della macchinina é fuori dal tracciato, altrimenti false<br>
     * 
     */
    public boolean isOutOfTracks(BufferedImage currentTrackImage){
        
        for (int y = 1; y < rImg.getWidth(); y++)
            for (int x = 1; x < rImg.getHeight(); x++)
                if(((rImg.getRGB(x, y) & 0xFF000000) != 0x00) && (currentTrackImage.getRGB(posX + x, posY + y) == 0xFFB5E61D)){
                    slowDown();
                    return true;
                }                    
        return false;
    
    }
    /**
     * 
     * Questo metodo percorre tutti i pixel della macchinina e nonppena uno di quelli non trasperenti sta toccando<br>
     * un'area verde scuro (0xFF22B14C) dell'immagine relativa al tracciato considera che essa abbia impattato il bordo<br>
     * 
     * @param currentTrackImage BufferedImage del tracciato su cui la macchinina gareggia<br>
     * @return boolean con true nonappena uno dei pixel non trasparenti della macchinina ha impattatoil bordo, altrimenti false<br>
     * 
     */
    public boolean isTouchingBorders(BufferedImage currentTrackImage){
        
        for (int y = 1; y < rImg.getWidth(); y++)
            for (int x = 1; x < rImg.getHeight(); x++)
                if(((rImg.getRGB(x, y) & 0xFF000000) != 0x00) && (currentTrackImage.getRGB(posX + x, posY + y) == 0xFF22B14C)) 
                    return true;
        return false;
    
    }
    /**
     * 
     * Questo metodo verifica se la macchinina sta toccando un ostacolo<br>
     * <br>
     * Esso richiede in input l'immagine del percorso, a questo stadio arrichita con quelle degli ostacoli<br>
     * Successivamente, percorre tutti i pixel dello sprite e, per quelli non trasparenti, verifica il colore<br>
     * di quelli dell'immagine del percorso con cui sono a contatto:<br>
     * <br>
     * <strong>-</strong>0xFFED1C24 (Rosso Fuoco) é interpretato come fiamma<br>
     * <strong>-</strong>0xFFFF7F27 (Arancione) é interpretato come fiamma<br>
     * <strong>-</strong>0xFFC0C0C0 (Grigio Chiaro) é interpretato come detrito metallico<br>
     * <strong>-</strong>0xFFB0B0B0 (Grigio Medio) é interpretato come detrito metallico<br>
     * Tutti questi colori ritornano true, ad indicazione del contatto con un ostacolo "distruttivo"<br>
     * <br>
     * <strong>-</strong>0xFF000000 (Nero) é interpretato come macchia d'olio<br>
     * Il contatto con questo colore, ritorna false (non dannegia immediatamente la macchinina)<br>
     * Esso, tuttavia, richiama il metodo privato <strong>slip()</strong> provocando uno suo sbandamento<br>
     * <br>
     * <Strong style="color:green;">Esercizio 6:</strong> Aggiungere una nuova tile (bosco, fango, laghetto, etc.) alla mappa, programmando:<br>
     * <strong>-</strong>Qui la verifica che la macchinina vi stia passando sopra<br>
     * <strong>-</strong>Qui la reazione della macchinina a tale effetto, se diverso da quanto già proposto<br>
     * <strong>-</strong>In <a href="MainGamePanel.html">MainGamePanel.playGame()</a> la logica di gioco, sfruttando i due metodi di cui sopra<br> 
     * <strong>-</strong>In <a href="CircuitImage.html">CircuitImage</a> quanto necessario per mostrarla a schermo<br>
     * <br>
     * Naturalmente, é anche necessario approntare:<br>
     * <strong>-</strong>la grafica vera e propria della tessera relativa in un file <strong>"data\img\tiles\"+xxx+".png"</strong><br>
     * <strong>-</strong>la descrizione simbolica del percorso inun file <strong>"data\tracks\"+xxx+"Track.csv"</strong><br>
     * <strong>-</strong>un pulsante selettore di tracciato in <a href="BasicOptionsChoicePanel.html">BasicOptionsChoicePanel</a><br>
     * <br>
     * <Strong style="color:green;">Esercizio 7:</strong> Aggiungere un nuovo ostacolo, questa volta benevolo alla mappa<br>
     * La logica interna deve far si che, ogni volta che la macchinina completa un giro, vi sia il 20% di chance che,<br>
     * in un punto scelto a caso del circuito, compaia una piccola chiave inglese dorata (0xFFFFD700)<br>
     * Quando la macchinina la raccoglie (i.e. vi passa sopra), la chiave deve sparire e l'integrità deve aumentare di 10 punti<br>
     * 
     * @param currentTrackImage BuffereImage contenente l'immagine del tracciato, arrichita degli ostacoli
     * @return boolean con true se la macchinina sta toccando un ostacolo distruttivo, false altrimenti
     */
    public boolean isTouchingHazard(BufferedImage currentTrackImage){
        
        for (int y = 1; y < rImg.getWidth(); y++)
            for (int x = 1; x < rImg.getHeight(); x++)
                if((rImg.getRGB(x, y) & 0xFF000000) != 0x00)
                    if(
                        currentTrackImage.getRGB(posX + x, posY + y) == 0xFFED1C24 ||
                        currentTrackImage.getRGB(posX + x, posY + y) == 0xFFFF7F27 ||
                        currentTrackImage.getRGB(posX + x, posY + y) == 0xFFC0C0C0 ||
                        currentTrackImage.getRGB(posX + x, posY + y) == 0xFFB0B0B0
                    ) return true;
                    else if (currentTrackImage.getRGB(posX + x, posY + y) == 0xFF000000){
                        slip();
                    }
        return false;
    
    }
    /**
     * 
     * Questo metodo verifica l'attirbuto privato <strong>lap</strong> e ritorna true solo se esso risulta non-negativo<br>
     * In altre parole, questo metodo ci permette di sapere se, dopo aver iniziato il gioco, l'utente ha ingaggiato<br>
     * il percorso, superando la line adi partenza almeo una volta.<br>
     * 
     * @return boolean con true se l'utente ha ingaggiato il percorso, superando la linea di partenza almeno una volta, altrimenti false<br>
     *
     */
    public boolean hasStarted(){
        return lap > -1;
    }
    /**
     * 
     * Questo metodo verifica che l'attributo privato <strong>shortLap</strong> sia stato resettato a false<br>
     * La logica di gioco in <a href="maingamepanel.html">MainGamePanel</a> ha effettuato tale settaggio quando la macchinina<br>
     * E' passata abbastanza vicino al checkpoint di metà percorso, così da evitare che il giocatore "tagli" il giro<br>
     * <br>
     * Solo in questo caso, il metodo percorre tutti i pixel della macchinina e, nonppena uno di quelli non trasperenti sta toccando<br>
     * un'area bianca (0xFFFFFFFF) dell'immagine relativa al tracciato, considera che essa sti transitando sulla linea di traguardo<br>
     * <br>
     * Al verificarsi di queste condizioni, sono modificati gli attributi privati:<br>
     * <strong>-shortLap</strong>, immediatamente posto a true per evitare che questo metodo sia erroneamente calcolato due o più volte per giro<br>
     * <strong>-lap</strong>, che naturalemnte é incrementato di uno<br>
     * 
     * @param currentTrackImage BufferedImage del tracciato su cui la macchinina gareggia<br>
     * 
     */
    public void checkForCompletedLap(BufferedImage currentTrackImage){
        if(!shortLap)
            for (int y = 1; y < rImg.getWidth(); y++)
                for (int x = 1; x < rImg.getHeight(); x++)
                    if(((rImg.getRGB(x, y) & 0xFF000000) != 0x00) && (currentTrackImage.getRGB(posX + x, posY + y) == 0xFFFFFFFF)){ 
                        shortLap = true;
                        lap++;
                        return;                        
                    }
    }
    /**
     * 
     * Questo metodo verifica che la macchinina passi vicino ad un punto di controllo (coordinate specificate in input.)<br>
     * Quando questo accade, é posto a false l'attributo privato <strong>shortLap</strong>,<br>
     * condizione necessaria perché il metodo <strong>checkForCompletedLap()</strong> validi il compeletamento di un giro.<br>
     * <br>
     * <Strong style="color:green;">Esercizio 5:</strong>Migliorare la profondità e la flessiblità della logica di controllo dei giri percorsi.<br>
     * <strong>1.</strong> Studiare questo metodo, comprendere come esso usi una variante della formula per la distanza fra due punti,<br>
     * verificando in realtà il <strong>quadrato</strong> della distanza fra due punti dacché l'operazione di<br> 
     * radice quadrata é computazionalemnte onerosa e, qui, senza valore aggiunto<br>
     * <strong>2.</strong> Studiare questo metodo, e comprenderne i limiti:<br>
     * <strong>-</strong>Il quadrato della distanza é arbitrariamente confrontato con una costante (15.000).<br>
     * <strong>-</strong>L'impiego di un solo punto di controllo risolve alcuni dei problemi (es: caso in cui il giocatore tenti di "tgliare" il percorso)<br>
     * <strong>-</strong>Altri problemi restano aperti (es. é possibile percorrere il percorso in senso inverso a quello di marcia previsto)<br>
     * <strong>3.</strong>Modificare:<br>
     * <strong>-</strong>la logica di questa classe perché sappia impiegare due punti di controllo e non solo uno<br>
     * <strong>-</strong>la logica di questa classe perché la distanza di passaggio dal punto di controllo diventi parametrica<br>
     * <strong>-</strong>la struttura di <a href="circuitimage.html">CircuitImage</a> perché possa gestire due punti di controllo e non solo uno<br>
     * <strong>-</strong>Il formato dei file descrittori dei percorsi (<strong>"data/tracks/"+trackChoice+"Track.csv"</strong>) perché portino anche le informazioni aggiuntiva di cui sopra<br>
     * <strong>4.</strong>Documentare bene il tutto, ad uso futuro<br>
     * 
     * @param checkPointX int con la coordinata X del punto di controllo
     * @param checkPointY int con la coordinata X del punto di controllo
     * 
     */
    public void checkAgainstShortLap(int checkPointX, int checkPointY){
        
        if(shortLap){
            int distanceSquared = (checkPointX - posX) * (checkPointX - posX) + (checkPointY - posY) * (checkPointY - posY);
            if(distanceSquared < 15000)
                shortLap = false;            
        }
                
    }
    /**
     * 
     * Questo metodo ruota la macchinina di 30° in senso orario.<br>
     * La logica interna verifica prima che essa si stia muovendo.<br>
     * Sono aggiornati sia il modello interno (angolo di rotazione in this.angle),<br>
     * che la rappresentazioe grafica, in this.rImg<br>
     * 
     */
    public void rotateClockWise(){
        
        if(integrity > 0 && speed > 0){
            angle += 30;
            rImg = MicroCarSprite.rotate(dImg, angle);
        }
            
    }
    /**
     * 
     * Questo metodo ruota la macchinina di 30° in senso antiorario.<br>
     * La logica interna verifica prima che essa si stia muovendo.<br>
     * Sono aggiornati sia il modello interno (angolo di rotazione in this.angle),<br>
     * che la rappresentazioe grafica, in this.rImg<br>
     * 
     */
    public void rotateCounterClockWise(){
        
        if(integrity > 0 && speed > 0){
            angle -= 30;
            rImg = MicroCarSprite.rotate(dImg, angle);
        }
    
    }
    /**
     * 
     * Questo metodo accelera la macchinina di una tacca.<br>
     * La velocità é misurata in pixel/thick<br>
     * La logica interna verifica prima che essa non stia eccedendo la propria velocità massima.<br>
     * 
     */
    public void accelerate(){
        
        if(speed < maxSpeed)
            speed++;
    
    }
    /**
     * 
     * Questo metodo decelera la macchinina di una tacca.<br>
     * La logica interna verifica prima che essa non stia già ferma.<br>
     * 
     */
    public void decelerate(){
        
        if(speed > 0)
            speed--;
    
    }
    /**
     * 
     * Questo metodo serve per rallentare la macchinina quando si sta muovendo sopra un ostacolo<br>
     * Esso decelera la macchinina didue tacche ma la logica interna verifica prima<br>
     * che essa non stia già muovendosi più lentamente della matà della propria velocità massima<br>
     * 
     */
    public void slowDown(){
        if(speed > maxSpeed / 2)
            speed-=2;
    }
    /**
     * 
     * Questo metodo simula il progressivo usurarsi della macchinina,<br>
     * deponendo ogni tanto una macchia d'olio e diminuendone l'integrità di un punto percentuale<br>
     * La logica interna determina tale evento con probabilità pari a 1% ogni volta che il metodo é invocato.
     * 
     * @return boolean con true se la macchinina rilascia una macchia d'olio, false altrimenti<br>
     */
    public boolean considerToDropOil(){
        if (Math.random() < 0.01)        
            {
                hurt();
                return true;
            }
        return false;
    }
    /**
     * 
     * Questo metodo permette di simulare uno slittamento della macchinina<br>
     * La logica interna:
     * <strong>-</strong>il 30% delle volte ruota la macchininain senso orario,<br>
     * <strong>-</strong>il 30% delle volte ruota la macchininain senso antiorario,<br>
     * <strong>-</strong>il 30% delle volte non fa nulla.<br>     * 
     * 
     */
    private void slip(){
        
        double r = Math.random();
        if(r <0.3)
            rotateCounterClockWise();
        else if(r > 0.7)
            rotateClockWise();
    
    }
    /**
     * 
     * Questo metodo permette di danneggiare la macchinina<br>
     * La logica interna:<br>
     * <strong>-</strong>Diminuisce di un punto ercentuale l'integrità strutturale della stessa.<br>
     * <strong>-</strong>Diminuisce la velocità massima al 20% dell'integrità strutturale residua<br>
     * <strong>-</strong>Se l'integrità strutturale cala a 0, evoca il metodo per la distruzione della macchinina<br>
     * 
     */
    public void hurt(){
        
        if(integrity > 0){
            integrity--;
            maxSpeed = integrity / 5;
        }
            
        if(integrity == 0)
            destroy();
    
    }
    /**
     * 
     * Questo metodo distrugge la macchinina, portando l'integrità strutturale a zero e mostrando l'immagine dei suoi resti brucianti<br>
     * 
     */
    public void destroy(){
        
            integrity = 0;
            rImg = rotate(fImg, 0);            
    
    }
    /**
     * 
     * Questo metodo muove la machinina, aggiungendo:
     * <strong>-</strong>al vettore posizione posX, la componente orizzontale del vettore velocià: <strong>speed*coseno(angle)</strong><br>
     * <strong>-</strong>al vettore posizione posY, la componente vetrticale del vettore velocià: <strong>speed*seno(angle)</strong><br>
     * NB: il vettore posizione é misurato in pixel, quello di velocità in pixel/thick.<br>
     * Ciononostante, la somma é dimensionalmente omogenea perché l'operazione viene effettuata una volta per thick,<br>
     * moltiplicando dunque il vettore velocità per un thick [pixel/thick * thick --> Pixel].
     * 
     */
    public void move(){
        
            posX += speed * Math.cos(Math.toRadians(angle));
            posY += speed * Math.sin(Math.toRadians(angle));
    
    }
    /**
     * 
     * Questo metodo ritorna l'immagine attuale della macchinina, costantemente aggiornata in base al suo angolo<br>
     * di rotazione o scambiata, un unica volta, ocn quella dei resti brucianti della stessa<br>
     * 
     * @return BufferedImage con l'immagine attuale della macchinina
     */
    public BufferedImage getImage(){
        
        return rImg;
    
    }
    /**
     * 
     * Metodo getter per la posizione X della macchinina<br>
     * 
     * @return int con la posizione X della macchinina, misurata in pixel.
     * 
     */
    public int getPosX(){
    
        return posX;
    
    }
    /**
     * 
     * Metodo getter per la posizione Y della macchinina<br>
     * 
     * @return int con la posizione Y della macchinina, misurata in pixel.
     * 
     */
    public int getPosY(){
    
        return posY;
    
    }
    /**
     * 
     * Metodo getter per l'angolo della macchinina rispetto all'asse Y<br>
     * 
     * @return int con l'angolo della macchinina rispetto all'asse Y, misurato in gradi.
     * 
     */
    public int getDirection(){
        return angle;
    }
    /**
     * 
     * Metodo getter per la velocità istantanea della macchinina<br>
     * 
     * @return int con la velocità istantanea della macchinina, misurata in pixel/thick.
     * 
     */
    public int getSpeed(){
    
        return speed;
    
    }
    /**
     * 
     * Metodo getter per la situazione di integritàstrutturale della machinina<br>
     * 
     * @return int rappresentante il grado di integrità strutturale della macchinina, inizializzato a 100 dal costruttore.<br>
     * Quando esso cala a 0, bisogna evocare il metodo <strong>destroy()</strong>
     * 
     */
    public int getIntegrity(){
    
        return integrity;
    
    }
    /**
     * 
     * Metodo getter per il numero di giri percorsi dalla machinina<br>
     * 
     * @return int con il numero di giri percorsi dalla macchinina
     * 
     */
    public int getLap(){
    
        return lap;
    
    }
    /**
     * Metodo che definisce la posizione X in cui la macchinina rilascia una macchia d'olio<br>
     * 
     * @return  int con la coordinata X in cui la macchinina deposita la macchia d'olio
     * 
     */
    public int getDroppedOilPosX(){
        return posX + rImg.getWidth()/2 - (int) (rImg.getWidth() * Math.cos(Math.toRadians(angle)));
    }
    /**
     * Metodo che definisce la posizione Y in cui la macchinina rilascia una macchia d'olio<br>
     * 
     * @return  int con la coordinata Y in cui la macchinina deposita la macchia d'olio
     * 
     */
    public int getDroppedOilPosY(){
        return posY + rImg.getHeight()/2 - (int) (rImg.getHeight() * Math.sin(Math.toRadians(angle)));
    }
    /**
     * 
     * Override del metodo di paint che posiziona la macchinina alle sue coordiante attuali <strong>(posX, posY)</strong><br>
     * disegnandovi <strong>rImg</strong> che ne contiene l'immagine già correttamente orientata<br>
     * od allora la grafica dei resti in fiamme della stessa.<br>
     * 
     * @param g <strong>Graphics</strong> nel quale disegnare gli elementi di gioco<br>
     * 
     */ 
    @Override
    public void paint(Graphics g) {
    
        g.drawImage(rImg, posX, posY, null);
    
    }
    
} 
 