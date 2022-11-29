package databaseproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserTypeController implements Initializable {

    

    @FXML
    private void candidateLogInAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CandidateLogIn.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void candidateSignUpAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CandidateSignUp.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void companyLogInAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CompanyLogIn.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void companySignUpAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CompanySignUp.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    private void exitAction(ActionEvent event) throws IOException {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
