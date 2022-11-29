package databaseproject;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class CandidateLogInController implements Initializable {
    Database db = new Database();
    
    @FXML
    public TextField candidateEmail;
    public PasswordField candidatePassword;
    public Label error, success;

    @FXML
    private void candidateLogInSubmitAction(ActionEvent event) throws IOException, SQLException {
        String email, password;
        email = candidateEmail.getText();
        password = candidatePassword.getText();
        PreparedStatement stmt = db.getCon().prepareStatement("select CandidateId,CandidateEmail,CandidatePassword from CANDIDATE where CandidateEmail = ? AND CandidatePassword = ?");
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CandidateHomePage.fxml"));
            Parent root = loader.load();
            CandidateHomePageController controller = (CandidateHomePageController)loader.getController();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            controller.setCandidateId(resultSet.getInt("CandidateId"));
        } else {
            error.setText("Invalid email or password. Please try again!!");
        }
        stmt.close();
    }
    
    @FXML
    private void backAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserType.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    private void exitAction(ActionEvent event) throws IOException {
        Platform.exit();
    }

    public void signUpSuccess(){
        success.setText("Account created successfully . Please log in to continue");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db.dbConnect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CandidateLogInController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CandidateLogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
