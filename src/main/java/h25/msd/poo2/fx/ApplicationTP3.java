package h25.msd.poo2.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class ApplicationTP3 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("tp3.fxml"));
        Parent racine = loader.load();
        TP3Controller controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setScene(new Scene(racine));
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(375);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
