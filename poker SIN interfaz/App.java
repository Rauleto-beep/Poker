/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

/**
 *
 * @author developer
 */
public class App {

    public static void main(String[] args) {
        //Create the 52 cards and add it to the deck
        Mazo mazo = new Mazo();
        String[] palos = {"Picas", "Corazones", "Treboles", "Diamantes"};
        String[] numeros = {"A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"};

        for (String palo : palos) {
            for (String numero : numeros) {
                
                Carta cartas = new Carta(numero, palo);
                mazo.agregarCartasAlMazo(cartas);
            }
        }
        
        mazo.barajeo(); //Shuffle deck
        
        //With this for you can see the deck shuffled
        
        /*for (Carta m : mazo.getMazo()) {
        System.out.println(m.toString());
        }*/
        
        
        Jugador player1 = new Jugador("Raul"); //Create player
        ManoJugador mano_jugador = new ManoJugador(); //Create the player's hand
        
        /* Save the last deck's card and save it into the object "last_card",
        * delete it from the deck after saving it, then it adds the last card
        * at the player's hands.
        *
        * ManoJugador.DEFAULT_SIZE it's a static attribute of hand's size (= 5).
        */
        
        for (int i = 0; i < ManoJugador.DEFAULT_SIZE; i++) {
            
            Carta ultima_carta = mazo.ultimaCarta();
            mano_jugador.recibirCarta(ultima_carta);  
            mazo.elimiarUltimaCartaMazo();
            
            mano_jugador.mano(); 
        }
        
        System.out.println(mano_jugador.toString()); //Print player's hand
            
        //Hand analyser
        AnalizadorMano analizador = new AnalizadorMano(mano_jugador); //Analise the play

        analizador.separar(); //Split method
        int highCard = analizador.highCard();

        if (analizador.pair() == 1) {System.out.println("PAREJA");}
        else if(analizador.twoPair() == 1){System.out.println("DOBLE PAREJA");}
        else if (analizador.triple() == 1) {System.out.println("TRIO");} 
        else if (analizador.straight() != 0){System.out.println("ESCALERA");}
        else if (analizador.flush() == 1){System.out.println("COLOR");}
        else {System.out.println("CARTA ALTA "+highCard);}
    
    }

}
