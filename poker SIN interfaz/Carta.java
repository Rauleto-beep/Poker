/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author developer
 */
import static poker.Mazo.numeros;
import static poker.Mazo.palos;

public class Carta {

    private int cartaPalo;
    private int cartaNumero;
    private String carta_palo;
    private String carta_numero;

    //Constructor
    public Carta(String carta_numero,String carta_palo) {
        this.carta_numero = carta_numero;
        this.carta_palo = carta_palo;
    }
    public Carta() {
        
    }
    
    //Getter
    public int getCartaPalo() {
        return cartaPalo;
    }
    
    public int getCartaNumero() {
    return cartaNumero;
    }
    
    /*
    public Carta getCarta(){
    return carta;
    }*/

    /*//Set ultima carta
    public Carta setCarta(Carta cartaUltima){
    return this.carta = cartaUltima;
    }*/
    
    //Eliminar ultima carta
    public void eliminarUltimaCarta(){
        
    }

    //toString
    @Override
    public String toString() {
        return "Carta " + carta_numero + " de " + carta_palo;
    }

}
