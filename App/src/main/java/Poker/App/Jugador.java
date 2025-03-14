package Poker.App;

import java.util.ArrayList;
/*
 * @Rauleto developer
 */
public class Jugador {


    private String nombre;
    private int puntuacion;
    ManoJugador mano_jugador;

    //Constructor
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntuacion = 0;
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

	public void asignarMano(ManoJugador mano) {
		this.mano_jugador = mano;
	}

	
}
