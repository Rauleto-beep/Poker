/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

/**
 *
 * @author developer
 */
public class Mazo {
    //Metodos estaticos porque son valores constantes,nuncan cambian
    static String[] palos = {"Picas", "Corazones", "Treboles", "Diamantes"};
    static String[] numeros = {"A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"};
    static int deck_size;
    static ArrayList<Carta> mazo;

    //Consructor
    public Mazo() {
        this.deck_size = 13 * 4;
        this.mazo = new ArrayList<>();
    }

    //Shuffle
     public void barajeo() {
         Collections.shuffle(this.mazo);
    }
    
    //Ultima carta del mazo
     public Carta ultimaCarta(){
         return this.mazo.get((mazo.size() -1));
     }
     
     //Elimiar carta
     public void elimiarUltimaCartaMazo(){
         this.mazo.remove(mazo.get(mazo.size() -1));
     }
     
     
    //Agregacion de cartas al mazo
    public void agregarCartasAlMazo(Carta... carta) {
        for (Carta c : carta) {
            this.mazo.add(c);
        }
    }

    //Getter
    static int getDeck_size() {
        return deck_size;
    }

    public ArrayList<Carta> getMazo() {
        return mazo;
    }

}
