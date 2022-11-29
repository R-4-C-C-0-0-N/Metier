/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 6sand
 */
public class CompanySignUpController implements Initializable {

    Database db = new Database();
    @FXML
    public TextField companyName, companyEmail, companyPassword, companyContact;
    public ComboBox organizationType;
    public TextArea companyAddress;
    public Label error;

    @FXML
    private void companySignUpSubmitAction(ActionEvent event) throws IOException, SQLException {
        try {
            String tname, temail, tpassword, taddress, tcontact, type;
            tname = companyName.getText();
            temail = companyEmail.getText();
            tpassword = companyPassword.getText();
            taddress = companyAddress.getText();
            tcontact = companyContact.getText();
            type = (String) organizationType.getValue();
            PreparedStatement stmt = db.getCon().prepareStatement("insert into company(CompanyName,OrganizationType,CompanyEmail,CompanyPassword,CompanyContactNumber,OfficeAddress) VALUES(?,?,?,?,?,?)");
            stmt.setString(1, tname);
            stmt.setString(2, type);
            stmt.setString(3, temail);
            stmt.setString(4, tpassword);
            stmt.setString(5, tcontact);
            stmt.setString(6, taddress);
            int s = stmt.executeUpdate();
            if (s > 0) {
                Parent root = FXMLLoader.load(getClass().getResource("CompanyHomePage.fxml"));
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
            stmt.close();
        } catch (Exception e) {
            error.setText("Please fill up all the data properly !!");
        }
    }
    
    @FXML
    private void backAction(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("UserType.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

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
        organizationType.getItems().addAll("IT", "Commercial", "Security");
    }

}
