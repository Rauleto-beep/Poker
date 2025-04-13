/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author developer
 */
public class ManoJugador {

    static int DEFAULT_SIZE = 5; //Constante = estatico
    private ArrayList<Carta> mano_jugador;

    public ManoJugador() {
        this.mano_jugador = new ArrayList<>();
    }

    //La mano del jugador para luego imprimirla con toString
    public void mano() {
        this.mano_jugador.clone();

    }

    //Recibir cartas
    public void recibirCarta(Carta cartas_jugador) {
        this.mano_jugador.add(cartas_jugador);
    }

    //Getter
    public int getSize() {
        return DEFAULT_SIZE;
    }

    @Override
    public String toString() {
        return "Mano=" + mano_jugador + '}';
    }

}
