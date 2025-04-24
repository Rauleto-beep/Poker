module Poker.App {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens Poker.App to javafx.fxml;
    exports Poker.App;
}
