/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microcars;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.logging.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * <h2>Questa é la classe che genera/gestisce l'immagine del circuito, compresi gli {@link com.mycompany.microcars.Dangers ostacoli/pericoli} - <u style="color:green;">Es. 2a, 2b e 2c</u></h2>
 * Essa, dapprima compone il circuito, a partire da un insieme di tessere (<strong>BufferedImage[] tilesImage</strong>) standard, poi vi sovrapone i <a href="dangers.html">pericoli</a>.<br>
 * Essa estende ed eredita da Component<br>
 * <br>
 * <br>
 * Della generazione del circuito é direttamente responsabile il costruttore.<br>
 * La classe mette poi a disposizione appropriati metodi per generare e gestire degli {@link com.mycompany.microcars.Dangers ostacoli/pericoli}, ad esso sovrapposti<br>
 * <br>
 * Infine, il metodo rebuildImageFromTiles() ricostituisce il tutto, rendendolo disponibile tramite i metodi getImage() e convertToIcon()<br>
 * <br>
 * La descrizione del circuito richiede in input un file .csv, opportunamene formattato.<br>
 * Si riporta, a titolo di esempio, il formato del file <strong>basicTrack.csv</strong><br>
 * <br>
 * <table border="1" style="table-layout: fixed ; width: 100%; margin-left: auto; margin-right: auto; text-align: center;">
 *  <caption style="caption-side: bottom; text-align: right;">Esempio basicTrack.csv</caption>
 *  <tr>
 *      <td> </td><td>A</td><td>B</td><td>C</td><td>D</td><td>E</td><td>F</td><td>G</td><td>H</td><td>I</td><td>J</td>
 *      <td>K</td><td>L</td><td>M</td><td>N</td><td>O</td><td>P</td><td>Q</td><td>R</td><td>S</td><td>T</td>
 *  </tr>
 *  <tr>
 *      <td>1</td><td> </td><td>PosX</td><td>400</td><td>PosY</td><td>110</td><td>CheckX</td><td>732</td><td>CheckY</td><td>332</td>
 *      <td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td>
 *  </tr>
 *  <tr>
 *      <td>2</td><td> </td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td><td>7</td><td>8</td><td>9</td>
 *      <td>10</td><td>11</td><td>12</td><td>13</td><td>14</td><td>15</td><td>16</td><td>17</td><td>18</td><td> </td>
 *  </tr>
 *  <tr>
 *      <td>3</td><td>1</td><td>B/F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td>
 *      <td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>F\B</td><td>1</td>
 *  </tr>
 *  <tr>
 *      <td>4</td><td>2</td><td>B|F</td><td>F</td><td>F/T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td>
 *      <td>S</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T\F</td><td>F</td><td>F|B</td><td>2</td>
 *  </tr>
 *  <tr>
 *      <td>5</td><td>3</td><td>B|F</td><td>F/T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td>
 *      <td>S</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T\F</td><td>F|B</td><td>3</td>
 *  </tr>
 *  <tr>
 *      <td>6</td><td>4</td><td>B|F</td><td>T</td><td>T</td><td>T/F</td><td>F</td><td>F</td><td>F</td><td>F</td><td>F</td>
 *      <td>F</td><td>F</td><td>F</td><td>F</td><td>F</td><td>F\T</td><td>T</td><td>T</td><td>F|B</td><td>4</td>
 *  </tr>
 *  <tr>
 *      <td>7</td><td>5</td><td>B|F</td><td>T</td><td>T</td><td>F</td><td>F</td><td>F</td><td>F</td><td>F</td><td>F</td>
 *      <td>F</td><td>F</td><td>F</td><td>F</td><td>F</td><td>F</td><td>T</td><td>T</td><td>F|B</td><td>4</td>
 *  </tr>
 *  <tr>
 *      <td>8</td><td>6</td><td>B|F</td><td>T</td><td>T</td><td>F</td><td>F</td><td>F</td><td>F</td><td>F</td><td>F</td>
 *      <td>F</td><td>F</td><td>F</td><td>F</td><td>F</td><td>F</td><td>T</td><td>T</td><td>F|B</td><td>4</td>
 *  </tr>
 *  <tr>
 *      <td>9</td><td>7</td><td>B|F</td><td>T</td><td>T</td><td>T\F</td><td>F</td><td>F</td><td>F</td><td>F</td><td>F</td>
 *      <td>F</td><td>F</td><td>F</td><td>F</td><td>F</td><td>F/T</td><td>T</td><td>T</td><td>F|B</td><td>4</td>
 *  </tr>
 *  <tr>
 *      <td>10</td><td>8</td><td>B|F</td><td>F\T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td>
 *      <td>S</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T/F</td><td>F|B</td><td>8</td>
 *  </tr>
 *  <tr>
 *      <td>11</td><td>9</td><td>B|F</td><td>F</td><td>F\T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td>
 *      <td>S</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T</td><td>T/F</td><td>F</td><td>F|B</td><td>9</td>
 *  </tr>
 *  <tr>
 *      <td>12</td><td>10</td><td>B\F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td>
 *      <td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>B-F</td><td>F/B</td><td>10</td>
 *  </tr>
 *  <tr>
 *      <td>13</td><td> </td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td><td>7</td><td>8</td><td>9</td>
 *      <td>10</td><td>11</td><td>12</td><td>13</td><td>14</td><td>15</td><td>16</td><td>17</td><td>18</td><td> </td>
 *  </tr>
 * </table>
 * <br>
 * Ad ogni stringa nel rettangolo con estremi le celle B3 ed S10, corrisponde univocamente una tessera, secondo la tabella qui riportata:<br>
 * <br>
 * <table border="1" style="table-layout: fixed ; width: 40%; margin-left: auto; margin-right: auto; text-align: center;">
 *  <caption style="caption-side: bottom; text-align: right;">Relazione fra la Stringa nel file CSV di descrizione percorso e la sua rappresentazione grafica</caption>
 *  <tr><th>Stringa</th><th>Posizione in tilesImage</th><th>Tessera</th></tr>
 *  <tr><td>F</td><td>0</td><td><img src="../../../../../../data/img/tiles/Field.png" alt="Field Tile"></td></tr>
 *  <tr><td>T</td><td>1</td><td><img src="../../../../../../data/img/tiles/Track.png" alt="Track Tile"></td></tr>
 *  <tr><td>S</td><td>2</td><td><img src="../../../../../../data/img/tiles/TrackStart.png" alt="Track Start Tile"></td></tr>
 *  <tr><td>Lasciata volontariamente<br>vuota per usi futuri</td><td>3</td><td>Lasciata volontariamente<br>vuota per usi futuri</td></tr>
 *  <tr><td>F\T</td><td>4</td><td><img src="../../../../../../data/img/tiles/Corner2-4.png" alt="Corner NW/SE Tile"></td></tr>
 *  <tr><td>F/T</td><td>5</td><td><img src="../../../../../../data/img/tiles/Corner1-3.png" alt="Corner NE/SW Tile"></td></tr>
 *  <tr><td>T\F</td><td>6</td><td><img src="../../../../../../data/img/tiles/Corner4-2.png" alt="Corner SE/NW Tile"></td></tr>
 *  <tr><td>T/F</td><td>7</td><td><img src="../../../../../../data/img/tiles/Corner3-1.png" alt="Corner SW/NE Tile"></td></tr>
 *  <tr><td>B-F</td><td>8</td><td><img src="../../../../../../data/img/tiles/BorderUp.png" alt="Upper Border Tile"></td></tr>
 *  <tr><td>F-B</td><td>9</td><td><img src="../../../../../../data/img/tiles/BorderDown.png" alt="Lower Border Tile"></td></tr>
 *  <tr><td>B|F</td><td>10</td><td><img src="../../../../../../data/img/tiles/BorderLeft.png" alt="Left Border Tile"></td></tr>
 *  <tr><td>F|B</td><td>11</td><td><img src="../../../../../../data/img/tiles/BorderRight.png" alt="Right Border Tile"></td></tr>
 *  <tr><td>B/F</td><td>12</td><td><img src="../../../../../../data/img/tiles/CornerBorder2.png" alt="NW Border Tile"></td></tr>
 *  <tr><td>F\B</td><td>13</td><td><img src="../../../../../../data/img/tiles/CornerBorder1.png" alt="NE Border Tile"></td></tr>
 *  <tr><td>B\F</td><td>14</td><td><img src="../../../../../../data/img/tiles/CornerBorder3.png" alt="SW Border Tile"></td></tr>
 *  <tr><td>F/B</td><td>15</td><td><img src="../../../../../../data/img/tiles/CornerBorder4.png" alt="SE Border Tile"></td></tr>
 * </table>
 * <br>
 * <Strong style="color:green;">Esercizio 2a:</strong> Aggiungere uno o due nuovi percorsi, con un tracciato a scelta<br>
 * <Strong style="color:green;">Esercizio 2b:</strong> Idealmente, uno di questi percorsi dovrebbe includere una tessera "TrackGraffiti" con una nuova grafica.<br>
 * <strong>NB</strong>: per evitare che i nuovi elementi siano intercettati dal rilevatore di contatto, che studieremo in seguito,<br>
 * meglio partire da una copia della tessera <strong>"data\img\tiles\Track.png"</strong>, prestando attenzione a disegnare il graffito con un colore non ancora mai usato nel gioco<br>
 * <Strong style="color:green;">Esercizio 2c:</strong> Programmare un configuratore, che permetta, in un'apposita, separata, finestra, di caricare uno dei circuiti già presenti sul disco,<br>
 * di modificarlo, tramite un'apposita GUI ed usando le tessere dipsobibili, nonché poi di salvarlo/riaprirlo in/da un file con il formato opportuno.<br>
 * Modificare {@link com.mycompany.microcars.BasicOptionsChoiceFrame BasicOptionsChoiceFrame} così che permetta di visualizzare, anche a rotazione, tutti i circuiti progressivamente disponibili sul disco, nonché di richiamare questa nuova funzione<br>
 * Documentare, tramite Javadoc, tutto il lavoro effettuato<br>
 * 
 * @author Giulio Frandi
 * @version 1.0<br>
 */
public class CircuitImage extends Component{    
    
    /** Numero Tessere Orizzontali */
    private final int SIZEX = 18;
    /** Numero Tessere Verticali */
    private final int SIZEY = 10;
    /** Informazioni di setUp caricate dal file csv descrittivo del circuito*/
    private String setUp = "";
    /** Coordinate X ed Y della posizione di partenza della macchinina e del checkPoint */
    private int startPositionX, startPositionY, checkPointX, checkPointY;
    /** Mappa della descrizione delle tessere componenti il percorso */
    private final String[][] tilesDescription = new String[SIZEY+2][SIZEX+1];
    /** Mappa dei numero di tessera con cui comporre il circuito */
    private final int[][] tilesMap = new int[SIZEX][SIZEY];
    /** Array delle Immagini delle Tessere */
    private final BufferedImage[] tilesImage = new BufferedImage[16];
    /** Numero massimo di ostacoli */
    private final int MAXDANGERS = (int) (SIZEX * SIZEY * 0.4);
    /** Array degli ostacoli */
    private final Dangers[] dangers;
    /** Numero di ostacoli progresivamente generati */
    private int numDangersOnTrack = 0;
    /** Immagine del circuito generata a partire dal mosaico delle tessere prescelte */
    private final BufferedImage trackImage;
    /**
    * 
    * Il costruttore permette di generare un circuito a partire da un file .csv opportunamente formattato
    * e da un corripondente insieme di tessere con formato grafico predefinito.
    * <br><br>
    * I circuiti sono dunque facilmente parametrizzabili con excel, descrivendoli con un file .csv e ricordando che:<br> 
    * <br>
    * <ol>
    * <li>Il file descrittivo va salvato nella directory: <strong>"data/tracks/"</strong><br>
    * <li>Il suo nome deve rispettare il formato <strong>nome+"Track.csv"</strong><br> 
    * </ol>
    * <br>
    * Dopo aver inizializzato l'array degli {@link com.mycompany.microcars.Dangers ostacoli/pericoli} e l'immagine (inizialmente vuota) del prcorso,
    * la prima operazione svolta dal costruttore é quella di caricare in memoria un array di immagini dal disco.<br>
    * La grafica delle singole tessere é caricata da file png, presenti nella directrory <strong>"data/img/tiles/"</strong><br>
    * Di questo si occupa il metodo <strong>loadTiles()</strong>, memorizzandoli nell' array <strong>BufferedImage[] tilesImage</strong><br>
    * <br>
    * Queste immagini vanno a costitutire un mosaico di tessere che permettono di assemblare dei percorsi a partire da un file .csv descrittivo.<br>
    * Il costruttore tratta tale file chiamando <strong>loadGridFromFile()</strong> e leggendovi:<br>
    * <br>
    * <ol>
    * <li>Le coordinate iniziali X ed Y della macchinina, rispettivamente in C1 ed E1<br>
    * <li>Le coordinate iniziali X ed Y del checkpoint, rispettivamente in G1 ed I1<br>
    * </ol>
    * <br>
    * Le coordinate di posizionamento iniziali della macchina sono rese disponibili al contesto<br>
    * tramite i metodi <strong>getStartPositionX()</strong> e <strong>getStartPositionY()</strong><br>
    * Il checkpoint non é mostrato, ma le sue coordinate sono rinvenibili dal contesto<br> 
    * tramite i metodi <strong>getCheckPointX()</strong> e <strong>getCheckPointY()</strong><br>
    * <br>
    * Successivamente, <strong>loadGridFromFile()</strong> tratta il rettangolo di celle con estremi B3 ed S10, 
    * assegnando ad ogni stringa rinvenutavi la corrispondente tessera nella matrice <strong>int[][] tilesMap</strong><br>
    * <br>
    * Il metodo rebuildImageFromTiles() ricostruisce il tutto, aggiungendovi anche gli {@link com.mycompany.microcars.Dangers ostacoli/pericoli} via via creati<br>
    * <img src="../../../resources/Diagramma Sequenza CircuitImage.jpg" alt="Diagramma di Sequenza per la generazione dell'Immagine del Circuito"><br>
    * <br>
    * @param trackChoice String che contiene la parte specifica del nome del circuito,<br>
    * così da poterne rinvenire i dati nel file: <strong>"data/tracks/"+trackChoice+"Track.csv"</strong>
    * @param fWidth larghezza dell'immagine generata
    * @param fHeight altezza dell'immagine generata
    * 
    */
    public CircuitImage(String trackChoice, int fWidth, int fHeight){

        // Inizializza l'array degli ostacoli/pericoli
        dangers = new Dangers[MAXDANGERS];
        // Inizializza l'immagine del percorso (inizialmente vuota)
        trackImage = new BufferedImage(fWidth, fHeight, BufferedImage.TYPE_INT_ARGB);
        // Carica l'array delle tessere che permetteranno di costituire il percorso
        loadTiles();
        // Carica la griglia descrittiva degli elementi del percorso dal file opportuno
        loadGridFromFile("data/tracks/"+trackChoice+"Track.csv");
        // Ricostruisce l'insieme
        rebuildImageFromTiles();
        
    }

    private void loadTiles(){
        
        try {        
            tilesImage[0] = ImageIO.read(new File("data/img/tiles/Field.png"));
            tilesImage[1] = ImageIO.read(new File("data/img/tiles/Track.png"));
            tilesImage[2] = ImageIO.read(new File("data/img/tiles/TrackStart.png"));
            tilesImage[4] = ImageIO.read(new File("data/img/tiles/Corner2-4.png"));
            tilesImage[5] = ImageIO.read(new File("data/img/tiles/Corner1-3.png"));
            tilesImage[6] = ImageIO.read(new File("data/img/tiles/Corner4-2.png"));
            tilesImage[7] = ImageIO.read(new File("data/img/tiles/Corner3-1.png"));
            tilesImage[8] = ImageIO.read(new File("data/img/tiles/BorderUp.png"));
            tilesImage[9] = ImageIO.read(new File("data/img/tiles/BorderDown.png"));
            tilesImage[10] = ImageIO.read(new File("data/img/tiles/BorderLeft.png"));
            tilesImage[11] = ImageIO.read(new File("data/img/tiles/BorderRight.png"));
            tilesImage[12] = ImageIO.read(new File("data/img/tiles/CornerBorder2.png"));
            tilesImage[13] = ImageIO.read(new File("data/img/tiles/CornerBorder1.png"));
            tilesImage[14] = ImageIO.read(new File("data/img/tiles/CornerBorder3.png"));
            tilesImage[15] = ImageIO.read(new File("data/img/tiles/CornerBorder4.png"));

        } catch (IOException ex) {
            Logger.getLogger(CircuitImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  
    private void loadGridFromFile(String file){
        
        String lineRead;
        int y = 0;
        
        try{
            // Apertura file descrittivo del percorso
            BufferedReader br = new BufferedReader(new FileReader(file));
            setUp = br.readLine();
            // Coordinate iniziali X ed Y della macchinina
            startPositionX = Integer.parseInt(setUp.split(";", -1)[2]);
            startPositionY = Integer.parseInt(setUp.split(";", -1)[4]);
            // Coordinate X ed Y del checkpoint
            checkPointX = Integer.parseInt(setUp.split(";", -1)[6]);
            checkPointY = Integer.parseInt(setUp.split(";", -1)[8]);
            // Selezione tessera a partire dal file 
            while((lineRead = br.readLine()) != null){
                tilesDescription[y] = lineRead.split(";", -1);
                for(int x = 0; x < SIZEX+1; x++){
                    switch (tilesDescription[y][x]) {
                        case "F":
                            tilesMap[x-1][y-1] = 0;
                            break;
                        case "T":
                            tilesMap[x-1][y-1] = 1;
                            break;
                        case "S":
                            tilesMap[x-1][y-1] = 2;
                            break;
                        case "F\\T":
                            tilesMap[x-1][y-1] = 4;
                            break;
                        case "F/T":
                            tilesMap[x-1][y-1] = 5;
                            break;
                        case "T\\F":
                            tilesMap[x-1][y-1] = 6;
                            break;
                        case "T/F":
                            tilesMap[x-1][y-1] = 7;
                            break;
                        case "B-F":
                            tilesMap[x-1][y-1] = 8;
                            break;
                        case "F-B":
                            tilesMap[x-1][y-1] = 9;
                            break;
                        case "B|F":
                            tilesMap[x-1][y-1] = 10;
                            break;
                        case "F|B":
                            tilesMap[x-1][y-1] = 11;
                            break;
                        case "B/F":
                            tilesMap[x-1][y-1] = 12;
                            break;
                        case "F\\B":
                            tilesMap[x-1][y-1] = 13;
                            break;
                        case "B\\F":
                            tilesMap[x-1][y-1] = 14;
                            break;
                        case "F/B":
                            tilesMap[x-1][y-1] = 15;
                            break;
                        default:
                            break;
                    }
                }
                y++;
            }
        } catch (Exception e){
            System.out.println(e);
        }
        
    }
    
    /**
     * Metodo che aggiorna l'immagine del percorso in <strong>BufferedImage trackImage</strong>.
     * <br><br>
     * Esso procede: <br>
     * <ol>
     * <li>disegnandovi l'insieme delle tessere (<strong>BufferedImage[] tilesImage</strong>), così come programmato in <strong>int[][] tilesMap</strong>,<br>
     * <li>poi sovrapponendovi le immagini degli {@link com.mycompany.microcars.Dangers ostacoli/pericoli}, così come descritti in <strong>Hazard[] hazards</strong> 
     * </ol>
     */
    final public void rebuildImageFromTiles(){
        // Creazione dell'oggetto Grafico, inizialmente vuoto
        Graphics2D trackGraphics = trackImage.createGraphics();
        // Disegno del mosaico di tessere
        for(int x = 0; x < SIZEX; x++)
            for (int y = 0; y < SIZEY; y++)
                trackGraphics.drawImage(tilesImage[tilesMap[x][y]], null, x * tilesImage[0].getWidth(), y * tilesImage[0].getHeight());  
        // Sovraimpressione degli ostacoli
        for (int curHazard = 0; curHazard < numDangersOnTrack; curHazard++)
            trackGraphics.drawImage(dangers[curHazard].getImg(), null, dangers[curHazard].getPosX(), dangers[curHazard].getPosY());
    
    }
    
    /**
     * Metodo che aggiorna la situazione degli {@link com.mycompany.microcars.Dangers ostacoli/pericoli} lungo il percorso.
     * <br><br>
     * La logica attuale prevede che la machinina, a volte perda olio e che, talvolta, questo si infiammi<br>
     * 
     * @param thisCar il metodo necessita dell'oggetto macchina in ingresso perché é questa che eventualmente decide se perdere olio<br> 
     */
    public void updateDangersSituation(MicroCarSprite thisCar){
        if(this.canStillAcceptDangers() && thisCar.considerToDropOil())
            this.addOilHazard(thisCar.getDroppedOilPosX(), thisCar.getDroppedOilPosY());
        considerToInflameOil();
    }
    
    // Metodo che verifica che il percorso possa ancora accettare degli ostacoli/pericoli}.
    // Il numero di ostacoli/pericoli sovrapponibili ad un percorso é limitato
    // dalla dimensione MAXDANGERS di Hazard[MAXDANGERS] dangers.
    // Per questo motivo, la classe offre al contesto un metodo per sapere se non se ne é
    // ancora superato il numero massimo consentito.
    // @return boolean True se il percorso può ancora accettare degli ostacoli/pericoli. False altrimenti 
    private boolean canStillAcceptDangers(){
    
        return numDangersOnTrack < MAXDANGERS;
    
    }
    
    // Metodo che permette di aggiungere un ostacolo/pericolo di tipo "Olio" ('O') al percorso corrente.
    // @param oilX Coordinata X della posizione dell'ostacolo/pericolo da creare sul percorso corrente
    // @param oilY Coordinata Y della posizione dell'ostacolo/pericolo da creare sul percorso corrente
    private void addOilHazard(int oilX, int oilY){
        
        Graphics2D trackGraphics = trackImage.createGraphics();
        dangers[numDangersOnTrack] = new Dangers('O', oilX , oilY);
        trackGraphics.drawImage(dangers[numDangersOnTrack].getImg(), null, dangers[numDangersOnTrack].getPosX(), dangers[numDangersOnTrack].getPosY());
        numDangersOnTrack++;
        
    }
    
    // Metodo che permette di percorrere l'array degli ostacoli/pericoli sul percorso e, su base percentuale, 
    // eventualmente infiammare alcuni di quelli di tipo "Olio" ('O')
    private void considerToInflameOil(){
    
        for (int curHazard = 0; curHazard < numDangersOnTrack; curHazard++)
            if(dangers[curHazard].isOil())
                if(Math.random() < 0.001)
                    dangers[curHazard].flameOil();
        
    }
    /**
     * Metodo getter della posizione iniziale X della macchinina.
     * <br><br>
     * @return posizione iniziale X della macchinina
     */
    public int getStartPositionX(){
    
        return startPositionX;
    
    }
     /**
     * Metodo getter della posizione iniziale Y della macchinina.
     * <br><br>
     * @return posizione iniziale X della macchinina
     */
    public int getStartPositionY(){
    
        return startPositionY;
    
    }
    
    /**
     * Metodo getter della posizione X del checkpont.
     * <br><br>
     * NB; La classe permette al contesto la definizione di un checkpoint,
     * utile per evitare che il giocatore "tagli" parte del percorso<br>
     * <br>
     * 
     * @return posizione iniziale X della macchinina
     */
    public int getCheckPointX(){
    
        return checkPointX;
    
    }
    
    /**
     * Metodo getter della posizione Y del checkpont.
     * <br><br>
     * NB; La classe permette al contesto la definizione di un checkpoint,
     * utile per evitare che il giocatore "tagli" parte del percorso<br>
     * <br>
     * 
     * @return posizione iniziale X della macchinina
     */    
    public int getCheckPointY(){
    
        return checkPointY;
    
    }
    /**
     * Metodo getter dell'imagine del circuito generata<br>
     * 
     * @return <strong>BufferedImage trackImage</strong>, immagine del percorso, inclusiva dei suoi <a href="dangers.html">pericoli</a> ivi presenti  
     */
    public BufferedImage getImage(){
    
        return trackImage;
    
    }
    /**
     * Metodo getter dell'icona generata a partire dall'imagine del circuito elaborata<br>
     * 
     * @return <strong>(Icon) trackImage</strong>, icona con immagine del percorso, in formato ridotto al 20%  
     */    
    public Icon convertToIcon(){
        
        BufferedImage basicTrackBareImage = this.getImage();    
        basicTrackBareImage = resizeImage(basicTrackBareImage, (int) (0.2 * basicTrackBareImage.getWidth()), (int) (0.2 * basicTrackBareImage.getHeight()));    
        return new ImageIcon(basicTrackBareImage);
        
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight){
    
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
        
    }    
    
}
