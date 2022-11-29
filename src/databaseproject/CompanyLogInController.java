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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CompanyLogInController implements Initializable {
    Database db = new Database();
    @FXML
    public TextField companyEmail;
    public PasswordField companyPassword;
    public Label error;

    @FXML
    private void companyLogInSubmitAction(ActionEvent event) throws IOException, SQLException {
        String email, password, email1;
        email = companyEmail.getText();
        password = companyPassword.getText();
        PreparedStatement stmt = db.getCon().prepareStatement("select CompanyId,CompanyEmail,CompanyPassword from COMPANY where CompanyEmail = ? AND CompanyPassword = ?");
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CompanyHomePage.fxml"));
            Parent root = loader.load();
            CompanyHomePageController controller = (CompanyHomePageController)loader.getController();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            controller.setCompanyId(resultSet.getInt("CompanyId"));
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
