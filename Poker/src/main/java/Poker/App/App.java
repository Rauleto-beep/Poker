package Poker.App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @Rauleto developer
 * JavaFX Application
 */
public class App extends Application {

    private static Scene game_scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Crear la escena escalable
        game_scene = new Scene(loadFXML("primary"), 800, 500);
        game_scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());


        //Welcome text on the initial screen
        Text bienvenida = new Text();
        bienvenida.setText("WELCOME");
        bienvenida.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));

        //Quit button
        Button quit = new Button("Salir");
        quit.setOnMouseEntered(e ->{
        	quit.setCursor(Cursor.HAND); //CHange the cursor
        });
        quit.setFont(Font.font("Times New Roman", FontWeight.BOLD,30));
        quit.setMinWidth(100);
        quit.setMaxWidth(200);
        quit.setMinHeight(100);
        quit.setMaxHeight(100);
        quit.setTextFill(Color.WHITE);
        quit.setBackground(new Background(new BackgroundFill(Color.FIREBRICK, new CornerRadii(15), null))); //Corner raddi = button radius (px)
        quit.setOnAction(e ->{
        	stage.close();
        });
        
        //Play button
        Button play = new Button("Jugar");
        play.setOnMouseEntered(e ->{
        	play.setCursor(Cursor.HAND); //Change the cursor
        });
        play.setFont(Font.font("Times New Roman", FontWeight.BOLD,30));
        play.setMinWidth(100);
        play.setMaxWidth(200);
        play.setMinHeight(100);
        play.setMaxHeight(100);
        play.setTextFill(Color.WHITE);
        play.setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, new CornerRadii(15), null))); //Corner raddi = button radius (px)
        play.setOnAction(e ->{
        	stage.setScene(game_scene);	//It set the stage to the game scene
        });
        
        //Horizontal box to save both buttons
        HBox button_box  = new HBox(20);
        button_box.getChildren().addAll(play,quit);//Add the elements in the box
        button_box.setAlignment(Pos.CENTER);
        button_box.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        
        //StackPane to join the welcome text and the buttons because if they are in the same box they would have the same format
        StackPane initial_root = new StackPane();
        initial_root.getChildren().addAll(button_box,bienvenida);
        initial_root.setMargin(bienvenida, new Insets(0,50,150,50)); //Coordinates to set the text on top of the buttons

        play.setOnAction(e -> {
            stage.setScene(game_scene); 
        });
        
        //Main Scene
        Scene main_scene = new Scene(initial_root,800,500);
        stage.setScene(main_scene);
        stage.setTitle("Poker");
        stage.show();
    }
    
    static void setRoot(String fxml) throws IOException {
        game_scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}