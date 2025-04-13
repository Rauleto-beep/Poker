module Poker.App {
    requires javafx.controls;
    requires javafx.fxml;

    opens Poker.App to javafx.fxml;
    exports Poker.App;
}
