package at.htlhl.seniorennotfallsystemfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Client client = new Client();
    public static ClientController clientController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("baseView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 200);
        clientController=fxmlLoader.getController();
        client.init();
        stage.setTitle("Senioren Notfall System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}