package Poker.App;

import java.util.ArrayList;
/*
 * @Rauleto developer
 */
public class ManoJugador {


    static int DEFAULT_SIZE = 5; //Constante = estatico
    private ArrayList <Carta> mano_jugador;

    public ManoJugador() {
        this.mano_jugador = new ArrayList<>();
    }
   
    //Collect cards into the player's hand
    public void recibirCarta(Carta cartas_jugador) {
        this.mano_jugador.add(cartas_jugador);
    }
    
    //Replace the selected cards,this is for the discards
    public void reemplazarCarta(int index, Carta nuevaCarta) {
        	mano_jugador.set(index, nuevaCarta);
        
    }

    //Getter
    public int getSize() {
        return DEFAULT_SIZE;
    }

    public Carta getCarta(int n){
        return this.mano_jugador.get(n);
    }
    
    public ArrayList <Carta> getMano(){
        return this.mano_jugador;
    }

	@Override
    public String toString() {
        return "Mano=" + mano_jugador + '}';
    }
}
