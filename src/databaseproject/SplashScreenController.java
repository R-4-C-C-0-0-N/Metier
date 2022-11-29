package databaseproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreenController implements Initializable {

    @FXML
    private AnchorPane ap;
    public Label loadingPercentage;
    public int i;
    public ProgressBar load;

    class ShowSplashScreen extends Thread {

        @Override
        public void run() {
            try {
                for (i = 0; i <= 100; i++) {
                    Thread.sleep(30);
                    if (i < 100) {
                        Platform.runLater(() -> {
                            load.setPrefWidth(i*6);
                            loadingPercentage.setText(i+"%");
                        });
                    } else {
                        Platform.runLater(() -> {
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("UserType.fxml"));
                            } catch (IOException ex) {
                                Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.show();
                            ap.getScene().getWindow().hide();
                        });
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new ShowSplashScreen().start();
    }

}
