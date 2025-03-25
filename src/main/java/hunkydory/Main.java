package hunkydory;

import hunkydory.ui.MainScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principal que inicia a aplicação JavaFX.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Sistema de Gerenciamento Hunky Dory");

        // Tela principal (menu)
        MainScreen mainScreen = new MainScreen(stage);

        Scene scene = new Scene(mainScreen, 800, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
