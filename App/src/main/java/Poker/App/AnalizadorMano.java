
package Poker.App;

import java.util.*;

/**
 *
 * @Rauleto developer
 */
public class AnalizadorMano {

	private ArrayList<Carta> mano_jugador;
	private int numero_max; // Save the highest number for the High Card
	public int[] numeros_mano_jugador; // Array to convert the rank array's string into int
	public static ArrayList<Integer> numeros_points; // To calculate the points of played hand that has those numbers

	private String[] carta_string; // Array to split the card into a string (suit)
	private String[] carta_numero;// Array to split the card into a string (rank)

	public AnalizadorMano(ManoJugador mano_jugador) {
		this.mano_jugador = mano_jugador.getMano();
		this.numeros_mano_jugador = new int[5];
		this.numeros_points = new ArrayList<>();

		this.carta_numero = new String[5];
		this.carta_string = new String[5];
	}

	public void setHand(ManoJugador mano_jugador) {
		this.mano_jugador = new ArrayList<>(mano_jugador.getMano());
	}

	/*
	 * With this method I split the player's hand into rank,and suit so I can be
	 * able to work with them just to check the hand's info. Then I do a checker of
	 * the special cards "A,K,Q,J" and save them as a number in a different array
	 * with int variable type to manage them for the hand's info if there's no
	 * special character,its just saved as an Integer on that array
	 */
	// Split
	private void separar() {
		for (int i = 0; i < mano_jugador.size(); i++) {
			Carta carta = this.mano_jugador.get(i);
			String[] carta_separada = carta.toString().split(" de ");
			carta_numero[i] = carta_separada[0];
			carta_string[i] = carta_separada[1];
		}

		// Special character's checker
		for (int i = 0; i < carta_numero.length; i++) {
			if (carta_numero[i].equals("A")) {
				this.numeros_mano_jugador[i] = 14;
			} else if (carta_numero[i].equals("K")) {
				this.numeros_mano_jugador[i] = 13;
			} else if (carta_numero[i].equals("Q")) {
				this.numeros_mano_jugador[i] = 12;
			} else if (carta_numero[i].equals("J")) {
				this.numeros_mano_jugador[i] = 11;
			} else {
				this.numeros_mano_jugador[i] = Integer.parseInt(carta_numero[i]); // Save normal numberss
			}
		}
	}

	// High card
	public int highCard() {
		separar();
		numero_max = numeros_mano_jugador[0];
		for (int i = 0; i < numeros_mano_jugador.length; i++) {
			if (numeros_mano_jugador[i] >= numero_max) {
				numero_max = numeros_mano_jugador[i];
			}
		}
		return numero_max;

	}

	// Pair
	public int pair() {
		separar();
		int contador = 0;
		Arrays.sort(numeros_mano_jugador);
		
		// Clean the arrayList to manage the numbers of the pair
		for (int i = 0; i < numeros_points.size(); i++) {
			numeros_points.remove(i);
		}

		for (int i = 0; i < numeros_mano_jugador.length - 1; i++) {
			if (numeros_mano_jugador[i] == numeros_mano_jugador[i + 1]) {
				contador++;
				numeros_points.add(numeros_mano_jugador[i]);

			}
		}
		if (contador == 1) {
			return 1;
		} else {
			return 0;
		}
	}

	// Two pair
	public int twoPair() {
		separar();
		int contador = 0;
		Arrays.sort(numeros_mano_jugador);

		// Clean the arrayList to manage the numbers of the 2 pair
		for (int i = 0; i < numeros_points.size(); i++) {
			numeros_points.remove(i);
		}

		for (int i = 0; i < numeros_mano_jugador.length - 1; i++) {
			if (numeros_mano_jugador[i] == numeros_mano_jugador[i + 1]) {
				contador++;
				
				// Add the numbers for the points
				numeros_points.add(numeros_mano_jugador[i]);
				numeros_points.add(numeros_mano_jugador[i+1]); 
			}
		}
		if (contador == 2) {
			return 1;
		} else {
			return 0;
		}
	}

	// Trio
	public int triple() {
		separar();
		Arrays.sort(numeros_mano_jugador);

		// Clean the arrayList to manage the numbers of the triple
		for (int i = 0; i < numeros_points.size(); i++) {
			numeros_points.remove(i);
		}

		for (int i = 0; i < numeros_mano_jugador.length - 2; i++) {

			if (numeros_mano_jugador[i] == numeros_mano_jugador[i + 1]
					&& numeros_mano_jugador[i] == numeros_mano_jugador[i + 2]) {
				
				// Add the 3 numbers for the points
				numeros_points.add(numeros_mano_jugador[i]);
				numeros_points.add(numeros_mano_jugador[i+1]); 
				numeros_points.add(numeros_mano_jugador[i+2]);
				return 1;

			}
			

		}
		return 0;
	}

	// Straight
	public int straight() {
		separar();
		Arrays.sort(numeros_mano_jugador);
		// Clean the arrayList to manage the numbers of the straight
		for (int i = 0; i < numeros_points.size(); i++) {
			numeros_points.remove(i);
		}
		for (int i = 0; i < numeros_mano_jugador.length - 1; i++) {
			if (numeros_mano_jugador[i] + 1 != numeros_mano_jugador[i + 1]) {
				return 0; // THey aren't consecutive = no straight, return 0
			}

		}
		for (int i = 0; i < numeros_mano_jugador.length; i++) {
			numeros_points.add(numeros_mano_jugador[i]);
		}
		return 1; // If they are consecutive = straight, return 1

	}

	// Flush
	public int flush() {
		separar();
		int contadorPicas = 0;
		int contadorTreboles = 0;
		int contadorCorazones = 0;
		int contadorDiamantes = 0;
		// Clean the arrayList to manage the numbers of the flush
		for (int i = 0; i < numeros_points.size(); i++) {
			numeros_points.remove(i);
		}

		for (int i = 0; i < numeros_mano_jugador.length; i++) {
			if (carta_string[i].equals("Picas")) {
				contadorPicas++;
			} else if (carta_string[i].equals("Treboles")) {
				contadorTreboles++;
			} else if (carta_string[i].equals("Corazones")) {
				contadorCorazones++;
			} else if (carta_string[i].equals("Diamantes")) {
				contadorDiamantes++;
			}
		}
		// Spades flush
		if (contadorPicas == 5) {
			for (int i = 0; i < numeros_mano_jugador.length; i++) {
				numeros_points.add(numeros_mano_jugador[i]);
			}
			return 1;
		}
		// Club flush
		if (contadorTreboles == 5) {
			for (int i = 0; i < numeros_mano_jugador.length; i++) {
				numeros_points.add(numeros_mano_jugador[i]);
			}
			return 1;
		}
		// Heart flush
		if (contadorCorazones == 5) {
			for (int i = 0; i < numeros_mano_jugador.length; i++) {
				numeros_points.add(numeros_mano_jugador[i]);
			}
			return 1;
		}
		// Diamond flush
		if (contadorDiamantes == 5) {
			for (int i = 0; i < numeros_mano_jugador.length; i++) {
				numeros_points.add(numeros_mano_jugador[i]);
			}
			return 1;
		}
		return 0;
	}

	// Flush house
	public int fullHouse() {
		Arrays.sort(numeros_mano_jugador);
		boolean hayTrio = false;
		boolean hayPareja = false;
		int pos = 0;
		// Clean the arrayList to manage the numbers of the full house
		for (int i = 0; i < numeros_points.size(); i++) {
			numeros_points.remove(i);
		}

		// Chech triple inside the full house
		for (int i = 0; i < numeros_mano_jugador.length - 2; i++) {
			if (numeros_mano_jugador[i] == numeros_mano_jugador[i + 1]
					&& numeros_mano_jugador[i] == numeros_mano_jugador[i + 2]) {
				hayTrio = true;
				pos = numeros_mano_jugador[i];
			}
		}
		// Check pair inside the full house
		for (int i = 0; i < numeros_mano_jugador.length - 1; i++) {
			if (numeros_mano_jugador[i] == numeros_mano_jugador[i + 1] && numeros_mano_jugador[i + 1] != pos) {
				hayPareja = true;
			}
		}

		if (hayTrio == true && hayPareja == true) {
			//Add the numbers for the points
			for (int i = 0; i < numeros_mano_jugador.length; i++) {
				numeros_points.add(numeros_mano_jugador[i]);
			}
			
			return 1;
		} else {
			return 0;
		}
	}

	// Four of a kind
	public int fourOfAKind() {
		separar();
		Arrays.sort(numeros_mano_jugador);
		

		// Clean the arrayList to manage the numbers of the full house
		for (int i = 0; i < numeros_points.size(); i++) {
			numeros_points.remove(i);
		}


		if (numeros_mano_jugador.length < 4) {
			return 0;
		}

		for (int i = 0; i < numeros_mano_jugador.length - 3; i++) {
			if (numeros_mano_jugador[i] == numeros_mano_jugador[i + 1]
					&& numeros_mano_jugador[i] == numeros_mano_jugador[i + 2]
					&& numeros_mano_jugador[i] == numeros_mano_jugador[i + 3]) {
				
				numeros_points.add(numeros_mano_jugador[i]);
				numeros_points.add(numeros_mano_jugador[i+1]);
				numeros_points.add(numeros_mano_jugador[i+2]);
				numeros_points.add(numeros_mano_jugador[i+3]);
				return 1;
			}
		}
		return 0;
	}

	// Straight flush
	public int straightFLush() {

		for (int i = 0; i < numeros_mano_jugador.length - 3; i++) {
			// If there's straight and flush = straight flush
			if ((straight() == 1) && (flush() == 1)) {
				for (int j = 0; j < numeros_mano_jugador.length; j++) {
					numeros_points.add(numeros_mano_jugador[j]);
				}
				
				return 1;
			}
		}
		
		
		return 0;
	}
}