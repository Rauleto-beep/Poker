package Poker.App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;
/*
 * @Rauleto developer
 */
public class PrimaryController {

	//All the GUI things
	@FXML
    private SplitPane splitpane;
    @FXML
    private HBox card_panel;
    @FXML
    private ToggleButton carta1;
    @FXML
    private ToggleButton carta2;
    @FXML
    private ToggleButton carta3;
    @FXML
    private ToggleButton carta4;
    @FXML
    private ToggleButton carta5;
    @FXML
    private Button discard;
    @FXML
    private Button select_all_cards;
    @FXML
    private Button play;
    @FXML
    private HBox playable_buttons;
    @FXML
    private StackPane button_all_card_panel;
    @FXML
    private Label remaining_discards;
    @FXML
    private Label remaining_plays;
    @FXML
    private Label necessary_points;
    @FXML
    private Label player_score_gui;  
    private Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    
    //Class creation
    private Jugador player1 = new Jugador("Player1");
    private Jugador boss = new Jugador("Player1");
    private Mazo mazo;
    private ManoJugador mano_jugador;
    private AnalizadorMano hand_analiser;
    private Carta ultima_carta;

    //Variable,array of buttons
    private ToggleButton[] cartas; 
    
    //Counters for limiting the interactions and to show them as text
    private static int contador = 0;//For discard limit
    private static int play_contador = 0; //For play limit
    private static int play_count = 3; //For the remaining plays left text
    private static int discard_count = 3; //For the remaining discards left text
    
    //Score counters
    private int boss_score = 150;
    private int player_score = 0;
    private int number_score = 0;

    @FXML
    private void initialize() throws IOException {
        cartas = new ToggleButton[]{carta1, carta2, carta3, carta4, carta5};
        //carta1.setId("A_picas");
        //carta2.setId("A_corazones");
        //carta3.setId("A_treboles");
        //carta4.setId("A_diamantes");
        //Set up buttons
        javaGUI();

        //Generate deck and players hand
        mazo = generarMazo();
        mano_jugador = playersHand();
        hand_analiser = new AnalizadorMano(mano_jugador);
        
        //Assign the data to the buttons and the text
        for (int i = 0; i < cartas.length; i++) {
            cartas[i].setUserData(mano_jugador.getCarta(i));
        }
        //CSS images
        images();
        
        //Game discard and play logic
        discardLogic();
        playLogic();        
        
    }
    //Generate the deck (total of 52 cards)
    private Mazo generarMazo() {
        Mazo mazo = new Mazo();
        String[] palos = {"Picas", "Corazones", "Treboles", "Diamantes"};
        String[] numeros = {"A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"};

        for (String palo : palos) {
            for (String numero : numeros) {
                Carta carta = new Carta(numero, palo);
                mazo.agregarCartasAlMazo(carta);
            }
        }

        mazo.barajeo(); //shuffle the deck
        return mazo;
    }
    
    //Create all the GUI things
    private void javaGUI() {
    	//Play button creation and configuration
        play.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        play.setOnMouseEntered(e -> play.setCursor(Cursor.HAND));
        play.setMinSize(100, 100);
        play.setMaxSize(200, 100);
        play.setTextFill(Color.WHITE);
        play.setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, new CornerRadii(15), null)));
        
        //Select all cards button to make it easier discarding 5 cards,creation and configuration
        select_all_cards.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        select_all_cards.setWrapText(true);
        select_all_cards.setTextAlignment(TextAlignment.CENTER);
        select_all_cards.setTextFill(Color.WHITE);
        select_all_cards.setBackground(new Background(new BackgroundFill(Color.TAN, new CornerRadii(10), null)));
        select_all_cards.setOnMouseEntered(e -> play.setCursor(Cursor.HAND));
        select_all_cards.setOnAction(e -> {
        	for (int i = 0; i < cartas.length; i++) {
        		cartas[i].setSelected(true);
        	}
        });
        
        //Discard button creation and configuration
        discard.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        discard.setOnMouseEntered(e -> discard.setCursor(Cursor.HAND));
        discard.setMinSize(100, 100);
        discard.setMaxSize(200, 100);
        discard.setTextFill(Color.WHITE);
        discard.setBackground(new Background(new BackgroundFill(Color.FIREBRICK, new CornerRadii(15), null)));
        
        //Discard text info
        remaining_discards.setFont(Font.font("Times New Roman", FontWeight.BOLD , 20));
        remaining_discards.setText("Descartes restantes: "+discard_count);
        
        //Play info
        remaining_plays.setFont(Font.font("Times New Roman", FontWeight.BOLD , 20));
        remaining_plays.setText("Jugadas restantes: "+play_count);
        
        //Necessary points to win
        necessary_points.setFont(Font.font("Times New Roman", FontWeight.BOLD , 20));
        necessary_points.setText("SMALL BLIND SCORE: "+boss_score);
        
        //Player score
        player_score_gui.setFont(Font.font("Times New Roman", FontWeight.BOLD , 20));
        player_score_gui.setText("YOUR SCORE: "+player_score);
    }
    
    //Create players hand
    private ManoJugador playersHand() {
        ManoJugador mano = new ManoJugador();
        for (int i = 0; i < ManoJugador.DEFAULT_SIZE; i++) {
            ultima_carta = mazo.ultimaCarta(); //Pick up the last card from the shuffled deck
            mano.recibirCarta(ultima_carta);   //Give it to the hand
            mazo.eliminarUltimaCartaMazo();	   //Deletes the last card from the deck,so always when we draw,the last card its going to be different
           
        }
        
        player1.asignarMano(mano); // Assign the hand to the Object player
        return mano;
    }

    //Discard logic
    private void discardLogic() {
    		
            discard.setOnAction(e -> {
                try {	
                	//With "contador" I limit the discards to have only 3 possible 
                	if(contador < 3) {

                		for (int i = 0; i < cartas.length; i++) {
                			//If the ToggleButton array "cartas" where I have the 5 buttons,if x card is selected then
                            if (cartas[i].isSelected()) {
                                //Obtains the last deck's cards and delete it
                                ultima_carta = mazo.ultimaCarta();
                                mazo.eliminarUltimaCartaMazo();
                                
                                //Updates the hand by replacing the selected card with the new one and we assign changed hand to the analyzer
                                mano_jugador.reemplazarCarta(i, ultima_carta);
                                hand_analiser.setHand(mano_jugador);
                                
                                //Updates the graphic interface,updating the cards data and the text of the button
                                cartas[i].setUserData(ultima_carta);

                                //This is for making the game more comfortable, deselect the select cards
                                cartas[i].setSelected(false);
                                
                                
                            } 
                        }  		
                		//Update the images
                		images();
                		
                		contador++; //Manage the discards limit
                		discard_count-=1; //Manage the discards remaining from the GUI
                		remaining_discards.setText("Descartes restantes: "+discard_count); //Discards remaining GUI
                	}else {
                		//If "contador" > 3,no more discards available 
            			System.out.println("NO QUEDAN DESCARTES");
            			
            		}
 
                } catch (Exception ex) {
                	/*With this exception I manage the case we can't draw more cards,it's because there's no more cards in the deck 
                	*(it's practicably impossible to this been showed up)
                	*/
                    System.out.println("Mazo vacío");
                }
            	
            });
        }
    	
    //Play logic
    private void playLogic() {    	
    	play.setOnAction(e->{
    		//With "play_contador" I limit the plays to have only 3 possible
    			if (play_contador < 3) {
    				
    				int highCard = hand_analiser.highCard(); //I assign the HighCard to this variable

    	            if (hand_analiser.straightFLush() == 1){alerta.setContentText("ESCALERA DE COLOR");}	//Straight flush
    	            else if (hand_analiser.fourOfAKind() == 1) {alerta.setContentText("POKER");} 			//Four of a kind
    	            else if (hand_analiser.fullHouse() == 1) {alerta.setContentText("FULL");}				//Full house
    	            else if (hand_analiser.flush() == 1){alerta.setContentText("COLOR");} 					//Flush
    	            else if (hand_analiser.straight() != 0){alerta.setContentText("ESCALERA");} 			//Straight
    	            else if (hand_analiser.triple() == 1) {alerta.setContentText("TRIO");} 					//Three of a kind
    	            else if(hand_analiser.twoPair() == 1){alerta.setContentText("DOBLE PAREJA");}			//Two pair
    	            else if (hand_analiser.pair() == 1) {alerta.setContentText("PAREJA");} 					//Pair
    	            else {
    	            	//Return the high card as letter instead of number
    	            	if (highCard == 14) {alerta.setContentText("CARTA ALTA A");}
    	            	if (highCard == 13) {alerta.setContentText("CARTA ALTA K");}
    	            	if (highCard == 12) {alerta.setContentText("CARTA ALTA Q");}
    	            	if (highCard == 11) {alerta.setContentText("CARTA ALTA J");}
    	            }
    	            /* It's necessary to discard the played card so it's not chetable the score
    	             *  by spamming the same hand,this is the same logic as the discard one
    	             */
    	            
    	            for (int i = 0; i < cartas.length; i++) {
                        if (cartas[i].isSelected()) {
                        	//Obtains the last deck's cards and delete it
                            ultima_carta = mazo.ultimaCarta();
                            mazo.eliminarUltimaCartaMazo();
                            
                            //Updates the hand by replacing the selected card with the new one and we assign changed hand to the analyzer
                            mano_jugador.reemplazarCarta(i, ultima_carta);
                            hand_analiser.setHand(mano_jugador);
                            
                            //Updates the graphic interface,updating the cards data and the text of the button
                            cartas[i].setUserData(ultima_carta);

                            //This is for making the game more comfortable, deselect the select cards
                            cartas[i].setSelected(false);
                            
                            
                        } 
                    }  		

    	            //Update the images
    	            images();
    	            
    	            //Accumulate points of played cards
    	            for (int i = 0; i < AnalizadorMano.numeros_points.size(); i++) {
    	            	number_score += AnalizadorMano.numeros_points.get(i);
    	            }
    	            
    	            //Game points function which manage all the score logic and maths
    	            points();
    	            
    	            //Assign the score to the player
    				player1.setPuntuacion(player_score);
    				boss.setPuntuacion(boss_score);
    	            
    	            //Player score text
    	            player_score_gui.setText("YOUR SCORE: "+player1.getPuntuacion());
    	            
    	            //Manage the counters
    				play_contador++; //Manage the plays
    				play_count-=1;   //Manage the remaining plays GUI
    				
    				//Remaining plays
    				remaining_plays.setText("Jugadas restantes: "+play_count);
    	            
    	            //Manage the alert
    				alerta.setHeaderText(null);
    				alerta.setTitle("MANO JUGADA");
    				    
    	            //Show the alert
    	            alerta.showAndWait();
    	            
    	            //Place the alert in the middle of the Window
    	            alerta.setX(400);
    	            alerta.setY(250);
    	            
    	           
    	            //Scores
    	            if (player_score >= boss_score) {
    	            	alerta.setTitle("JUEGO FINALIZADO!!");
    	            	alerta.setContentText("GG!");
    	            	alerta.setX(400);
    	                alerta.setY(250);
    	            	alerta.showAndWait();
    	            }
    	            //Game over alert
    	            else if(player1.getPuntuacion() < boss.getPuntuacion() && play_contador == 3) {
    	            	alerta.setTitle("JUEGO FINALIZADO!!");
    	            	alerta.setContentText("GAME OVER");
    	            	alerta.setX(400);
    	                alerta.setY(250);
    	            	alerta.showAndWait();
    	            }
    			}
    			else {
    				//If play_contador > 3,no more plays available
    				System.out.println("No quedan jugadas");
    			}
    	});
    }
    
    //Score logic
    private void points() {
    	int highCard = hand_analiser.highCard();
    	
    	//Chips * multiplicator (as balatro)
    	
    	if (hand_analiser.straightFLush() == 1){player_score += (number_score+100) * 8;} 
        else if (hand_analiser.fourOfAKind() == 1) {player_score += (number_score+60) * 7;}
        else if (hand_analiser.fullHouse() == 1) {player_score += (number_score+40) * 4;}
        else if (hand_analiser.flush() == 1){player_score += (number_score+35) * 4;}
        else if (hand_analiser.straight() != 0){player_score += (number_score+30) * 4;}
        else if (hand_analiser.triple() == 1) {player_score += (number_score+30) * 3;}
        else if(hand_analiser.twoPair() == 1){player_score += (number_score+20) * 2;}
        else if (hand_analiser.pair() == 1) {player_score += (number_score+10) * 2;}
        else {
        	//High Card
        	player_score += (highCard+5) * 1;
        }
    }
    
    //CSS manage
    private void images() {
    	for (ToggleButton tb : cartas) {
    		//Spades
    		if (tb.getUserData().toString().equals("K de Picas")) {tb.setId("K_picas");}
    		else if (tb.getUserData().toString().equals("Q de Picas")) {tb.setId("Q_picas");}
    		else if (tb.getUserData().toString().equals("J de Picas")) {tb.setId("J_picas");}
    		else if (tb.getUserData().toString().equals("10 de Picas")) {tb.setId("10_picas");}
    		else if (tb.getUserData().toString().equals("9 de Picas")) {tb.setId("9_picas");}
    		else if (tb.getUserData().toString().equals("8 de Picas")) {tb.setId("8_picas");}
    		else if (tb.getUserData().toString().equals("7 de Picas")) {tb.setId("7_picas");}
    		else if (tb.getUserData().toString().equals("6 de Picas")) {tb.setId("6_picas");}
    		else if (tb.getUserData().toString().equals("5 de Picas")) {tb.setId("5_picas");}
    		else if (tb.getUserData().toString().equals("4 de Picas")) {tb.setId("4_picas");}
    		else if (tb.getUserData().toString().equals("3 de Picas")) {tb.setId("3_picas");}
    		else if (tb.getUserData().toString().equals("2 de Picas")) {tb.setId("2_picas");}
    		else if (tb.getUserData().toString().equals("A de Picas")) {tb.setId("A_picas");}
    		
    		//Hearts
    		if (tb.getUserData().toString().equals("K de Corazones")) {tb.setId("K_corazones");}
    		else if (tb.getUserData().toString().equals("Q de Corazones")) {tb.setId("Q_corazones");}
    		else if (tb.getUserData().toString().equals("J de Corazones")) {tb.setId("J_corazones");}
    		else if (tb.getUserData().toString().equals("10 de Corazones")) {tb.setId("10_corazones");}
    		else if (tb.getUserData().toString().equals("9 de Corazones")) {tb.setId("9_corazones");}
    		else if (tb.getUserData().toString().equals("8 de Corazones")) {tb.setId("8_corazones");}
    		else if (tb.getUserData().toString().equals("7 de Corazones")) {tb.setId("7_corazones");}
    		else if (tb.getUserData().toString().equals("6 de Corazones")) {tb.setId("6_corazones");}
    		else if (tb.getUserData().toString().equals("5 de Corazones")) {tb.setId("5_corazones");}
    		else if (tb.getUserData().toString().equals("4 de Corazones")) {tb.setId("4_corazones");}
    		else if (tb.getUserData().toString().equals("3 de Corazones")) {tb.setId("3_corazones");}
    		else if (tb.getUserData().toString().equals("2 de Corazones")) {tb.setId("2_corazones");}
    		else if (tb.getUserData().toString().equals("A de Corazones")) {tb.setId("A_corazones");}
    		
    		//Clubs
    		if (tb.getUserData().toString().equals("K de Treboles")) {tb.setId("K_treboles");}
    		else if (tb.getUserData().toString().equals("Q de Treboles")) {tb.setId("Q_treboles");}
    		else if (tb.getUserData().toString().equals("J de Treboles")) {tb.setId("J_treboles");}
    		else if (tb.getUserData().toString().equals("10 de Treboles")) {tb.setId("10_treboles");}
    		else if (tb.getUserData().toString().equals("9 de Treboles")) {tb.setId("9_treboles");}
    		else if (tb.getUserData().toString().equals("8 de Treboles")) {tb.setId("8_treboles");}
    		else if (tb.getUserData().toString().equals("7 de Treboles")) {tb.setId("7_treboles");}
    		else if (tb.getUserData().toString().equals("6 de Treboles")) {tb.setId("6_treboles");}
    		else if (tb.getUserData().toString().equals("5 de Treboles")) {tb.setId("5_treboles");}
    		else if (tb.getUserData().toString().equals("4 de Treboles")) {tb.setId("4_treboles");}
    		else if (tb.getUserData().toString().equals("3 de Treboles")) {tb.setId("3_treboles");}
    		else if (tb.getUserData().toString().equals("2 de Treboles")) {tb.setId("2_treboles");}
    		else if (tb.getUserData().toString().equals("A de Treboles")) {tb.setId("A_treboles");}
    		
    		//Diamonds
    		if (tb.getUserData().toString().equals("K de Diamantes")) {tb.setId("K_diamantes");}
    		else if (tb.getUserData().toString().equals("Q de Diamantes")) {tb.setId("Q_diamantes");}
    		else if (tb.getUserData().toString().equals("J de Diamantes")) {tb.setId("J_diamantes");}
    		else if (tb.getUserData().toString().equals("10 de Diamantes")) {tb.setId("10_diamantes");}
    		else if (tb.getUserData().toString().equals("9 de Diamantes")) {tb.setId("9_diamantes");}
    		else if (tb.getUserData().toString().equals("8 de Diamantes")) {tb.setId("8_diamantes");}
    		else if (tb.getUserData().toString().equals("7 de Diamantes")) {tb.setId("7_diamantes");}
    		else if (tb.getUserData().toString().equals("6 de Diamantes")) {tb.setId("6_diamantes");}
    		else if (tb.getUserData().toString().equals("5 de Diamantes")) {tb.setId("5_diamantes");}
    		else if (tb.getUserData().toString().equals("4 de Diamantes")) {tb.setId("4_diamantes");}
    		else if (tb.getUserData().toString().equals("3 de Diamantes")) {tb.setId("3_diamantes");}
    		else if (tb.getUserData().toString().equals("2 de Diamantes")) {tb.setId("2_diamantes");}
    		else if (tb.getUserData().toString().equals("A de Diamantes")) {tb.setId("A_diamantes");}
    	}
    }
    
}