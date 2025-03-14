package Poker.App;
/*
 * @Rauleto developer
 */
public class Carta {

    private String carta_palo;
    private String carta_numero;

    //Constructor
    public Carta(String carta_numero,String carta_palo) {
        this.carta_numero = carta_numero;
        this.carta_palo = carta_palo;
    }
    public Carta() {
        
    }

    //toString
    @Override
    public String toString() {
        return carta_numero + " de " + carta_palo;
    }
}
