/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microcars;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * <h2>Questa é la classe che implementa gli ostacoli sul percorso ed il loro comportamento.</h2>
 * Per ora é implementato solo quello della chiazza d'olio che, eventualmente, può infiammarsi<br>
 *  
 * @author Giulio Frandi
 * @version 1.0
 * 
 */
public class Dangers extends Component{
    
    /** Immagine dell'ostacolo */
    private BufferedImage img;
    /** Tipo dell'ostacolo per ora, é implementato solo l'olio ('O'), eventualmente poi infiammabile ('F') */
    private char type;
    /** Coordinate X ed Y dell'ostacolo */
    private int posX, posY;
    /**
     * Il costruttore genera un pericolo (<strong>Danger</strong>), secondo i parametri passatigli.
     * <br><br>
     * Il parametro type permette di specificare il tipo di pericolo, anche se,<br>
     * per ora, é implementato solo l'olio ('O'), eventualmente poi infiammabile ('F')<br>
     * L'immagine dell'olio é caricata da <strong>data/img/oil.png</strong> e poi ridimensionata secondo necessità.
     * 
     * @param type char con il tipo di pericolo. ('O' per olio)
     * @param posX int con le coordinate X del pericolo (<strong>danger</strong>)
     * @param posY int con le coordinate Y del pericolo (<strong>danger</strong>)
     * 
     */
    public Dangers(char type, int posX, int posY){
        try {
            this.type = type;
            this.posX = posX;
            this.posY = posY;
            if(type == 'O'){
                img = ImageIO.read(new File("data/img/oil.png"));
                img = Dangers.resizeImage(img, 25, 25);
            }
        } catch (IOException e) {
        }
    }
    /**
     * 
     * Metodo che trasforma il pericolo corrente in un incendio localizzato (this.type = 'F').
     * <br><br>
     * L'immagine dell'incendio é caricata da <strong>data/img/trackFire.png</strong><br>
     * e poi ridimensionata secondo necessità.
     * 
     */
    public void flameOil(){
        try{
            this.img = ImageIO.read(new File("data/img/trackFire.png"));
            this.img = Dangers.resizeImage(img, 25, 25);
            this.type = 'F';
        }catch(IOException e){            
        }
    }
    
    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
    
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
        
    }
    /**
     * metodo getter della posizione X del pericolo (<strong>Danger</strong>
     * 
     * @return int con posizione X del pericolo (<strong>Danger</strong>)
     */
    public int getPosX(){
        return posX;
    }
    /**
     * metodo getter della posizione Y del pericolo (<strong>Danger</strong>)
     * 
     * @return int con posizione Y del pericolo (<strong>Danger</strong>)
     */
    public int getPosY(){
        return posY;
    }
    /**
     * metodo getter della posizione Y del pericolo (<strong>Danger</strong>)
     * 
     * @return int con posizione Y del pericolo (<strong>Danger</strong>)
     */
    public boolean isOil(){
        return type == 'O';
    }
    /**
     * metodo getter dell'immagine corrente del pericolo (<strong>Danger</strong>)
     * 
     * @return BuffereImage con immagine corrente del pericolo (<strong>Danger</strong>)
     */
    public BufferedImage getImg(){
        return img;
    }
    /**
     * Override del metodo paint per disegnare l'immagine del pericolo (<strong>this.img</strong>)
     * ove opportuno (<strong>this.posX, this.posY)</strong><br>
     * 
     * @param g Graphics proveniente dal contesto ove disegnare il pericolo (<strong>Danger</strong>)
     * 
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(this.img, this.posX, this.posY, null);
    }
        
}
