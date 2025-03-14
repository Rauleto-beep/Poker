package Poker.App;

import java.util.ArrayList;
import java.util.Collections;
/*
 * @Rauleto developer
 */
public class Mazo {

    //Statics methods due to their constant value
    static String[] palos = {"Picas", "Corazones", "Treboles", "Diamantes"};
    static String[] numeros = {"A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"};
    static int deck_size;
    static ArrayList<Carta> mazo;

    //Constructor
    public Mazo() {
        this.deck_size = 13 * 4;
        this.mazo = new ArrayList<>();
    }

    //Shuffle
     public void barajeo() {
         Collections.shuffle(this.mazo);
    }
    
    //Last deck's card
     public Carta ultimaCarta(){
         return this.mazo.get((mazo.size() -1));
     }
     
     //Delete the last deck's card
     public void eliminarUltimaCartaMazo(){
         this.mazo.remove(mazo.get(mazo.size() -1));
     }
     
    //Add card to the deck
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
