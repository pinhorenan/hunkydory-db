package hunkydory;

import hunkydory.ui.MainScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Classe principal que inicia a aplicação JavaFX.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Sistema de Gerenciamento Hunky Dory");

        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/database_icon.png"))));

        stage.setScene(new Scene(new MainScreen(stage), 800, 600));

        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
