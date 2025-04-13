/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.ArrayList;

/**
 *
 * @author developer
 */
public class Jugador {

    private String nombre;
    private int puntuacion;
    ArrayList<ManoJugador> mano_jugador;

    //Constructor
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntuacion = 0;
        this.mano_jugador = new ArrayList<>();
    }

    //Agregacion ArrayList
    public void agregarMano(ManoJugador ... mano_jugador) {
        for (ManoJugador mj : mano_jugador){
            this.mano_jugador.add(mj);
        }
    }

    //Getter
    public String getNombre() {
        return nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    //Setter
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

}
