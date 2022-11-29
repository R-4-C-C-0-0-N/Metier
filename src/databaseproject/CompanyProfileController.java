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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CompanyProfileController implements Initializable {
    int flag = 0;
    int cid = 0;
    int caid = 0;
    @FXML
    Label companyId, companyName, companyEmail, companyContact, companyType, companyAddress;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setCompanyId(String toString) {
        companyId.setText("Id : "+toString);
    }

    void setCompanyName(String string) {
        companyName.setText("Company Name : "+string);
    }

    void setCompanyEmail(String string) {
        companyEmail.setText("Email : "+string);
    }

    void setCompanyContact(String string) {
        companyContact.setText("Contact Number : "+string);
    }

    void setCompanyType(String string) {
        companyType.setText("Organization Type : "+string);
    }

    void setCompanyAddress(String string) {
        companyAddress.setText("Office Address : "+string);
    }
    
    void setFlag(int i ) {
        flag = i;
    }
    
    void setCId(int i ) {
        cid = i;
    }
    
    void setCaId(int i ) {
        caid = i;
    }
    
    @FXML
    private void backAction(ActionEvent event) throws IOException {
        if(flag == 1){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CandidateHomePage.fxml"));
        Parent root = loader.load();
        CandidateHomePageController controller = (CandidateHomePageController) loader.getController();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        controller.setCandidateId(caid);
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CompanyHomePage.fxml"));
        Parent root = loader.load();
        CompanyHomePageController controller = (CompanyHomePageController) loader.getController();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        controller.setCompanyId(cid);
        }
    }

    @FXML
    private void exitAction(ActionEvent event) throws IOException {
        Platform.exit();
    }
    
}
