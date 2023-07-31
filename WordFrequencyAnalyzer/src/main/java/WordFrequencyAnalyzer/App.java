package WordFrequencyAnalyzer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * GUI for the Word Frequency Analyzer
 */
public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Home view
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("woa-home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 440, 218);
        Image icon = new Image("file:icons8-book-64.png");

        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Word Occurrence");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}