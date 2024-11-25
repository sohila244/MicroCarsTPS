/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microcars;

/**
 *
 * <h2>Questa classe di servizio implementa un meccanismo di scambio di messaggi sincronizzati tra processi in esecuzione parallela asincorna.</h2>
 * <br>
 * Essa é ripresa integralemnte dal testo <strong>Tecnologia e Progettazione di Sistemi InFormatici e di Telecomunicazioni</strong><br>
 * Casa Editrice <strong>Edizioni Atlas</strong>, nella sua 10' edizione del 2018<br>
 * Il <strong>capitolo 1: "Esecuzione Concorrente di Processi"</strong> rappresenta un'ottima introduzione teorica alla programmazione Multi-Processo<br>
 * La sezione laboratoriale e' ricca di esempi e questo codice é tratto dal <strong>Progetto 7: Ricezione garantita via inter-process communication</strong><br>
 * <br>
 * Ivi é possibile trovarne una descrizione più dettagliata.<br>
 * 
 * @author Agostino Lorenzi, Enrico Cavalli, Andrea Colleoni<br>
 * @version 1.0<br>
 * 
 */
public class AlertMessage {
    
    // Messaggio da trasmettere
    private String message;
    // Flag per verifica presenza messaggio da trasmettere
    private boolean isMessagePresent;

    /**
 * 
 * Il costruttore fa ben poco, limitandosi ad:<br>
 * -istanziare il messaggio (message) ad una stringa vuota<br> 
 * -coerentemente, mettere a falso il flag (isMessagePresent) di presenza dello stesso<br>
 * 
 */  
    public AlertMessage(){
        message = "";
        isMessagePresent = false;        
    }
 /**
 * 
 * Metodo <strong>sincronizzato</strong>, getter del messaggio. 
 * <br><br>
 * Poiché questo metodo é sincronizzato, esso può essere chiamato solo da un contesto per volta.<br>
 * Anche quando un contesto si appropria del metodo, in caso nessun messaggio sia già presente, il processo é messo in wait.<br>
 * <br>
 * Solo una chiamata a setMessage() può allora risvegliarlo, effettuando, in successione:<br>
 * <strong>-</strong>il deposito di un messaggio da prendere<br>
 * <strong>-</strong>il corrispettivo settaggio del flag di presenza messaggio (isMessagePresent) a vero<br>
 * <strong>-</strong>la generazione del corispondente notify()<br>
 * <br>
 * Assolte queste pre-condizioni, il metodo:<br>
 * <strong>-</strong>prende il messaggio, ormai disponibile e lo rende al contesto<br>
 * <strong>-</strong>setta il flag di presenza messaggio (isMessagePresent) a falso<br>
 * <strong>-</strong>genera il notify() necessario per attivare il prossimo setMessage() (che arriverà o già in attesa)<br>
 * 
    * @return message il messaggio reso al contesto, una <strong>String</strong> spesso con una codifica precisa e decisa dal contesto<br>
 */
    synchronized public String getMessage(){
        if(!isMessagePresent)
            try{
                wait();
            } catch (InterruptedException e){}
        isMessagePresent = false;
        notify();
        return message;
    }
 /**
 * 
 * 
 * Metodo <strong>sincronizzato</strong>, setter del messaggio. 
 * <br><br>
 * Poiché questo metodo é sincronizzato, esso può essere chiamato solo da un contesto per volta.<br>
 * Anche quando un contesto si appropria del metodo, in caso un messaggio sia già presente, il processo é messo in wait.<br>
 * <br>
 * Solo una chiamata a getMessage() può allora risvegliarlo, effettuando, in successione:<br>
 * <strong>-</strong>il ritiro del messagio precedentemente depositato<br>
 * <strong>-</strong>il corrispettivo settaggio dell flag di presenza messaggio (isMessagePresent) a falso<br>
 * <strong>-</strong>la generazione del corispondente notify()<br>
 * <br>
 * Assolte queste pre-condizioni, il mmetodo:<br>
 * <strong>-</strong>rende disponibile il messaggio fornito in input dal contesto<br>
 * <strong>-</strong>setta il flag di presenza messaggio (isMessagePresent) a vero<br>
 * <strong>-</strong>genera il notify() necessario per risvegliare getMessage()<br>
 * 
    * @param message il messaggio da scambiare, spesso una stringa con una codifica precisa e decisa dal contesto<br>
 * 
 */    
    synchronized public void setMessage(String message){
        if(isMessagePresent)
            try{
                wait();
            } catch (InterruptedException e){}
        this.message = message;
        isMessagePresent = true;
        notify();      
    }

}
