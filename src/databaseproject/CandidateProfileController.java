/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 * FXML Controller class
 *
 * @author 6sand
 */
public class CandidateProfileController implements Initializable {

    int eCandidateId = 0;
    
    @FXML
    Label candidateId, candidateName, candidateAge, candidateEmail, candidateContact, candidateSkill, candidateExp, candidateAddress, candidateAcademic, candidateExtra, candidatePost, candidateSector;
    
    @FXML
    private void backAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CandidateHomePage.fxml"));
            Parent root = loader.load();
            CandidateHomePageController controller = (CandidateHomePageController)loader.getController();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        controller.setCandidateId(eCandidateId);
        System.out.println(eCandidateId);
    }
    
    @FXML
    private void exitAction(ActionEvent event) throws IOException {
        Platform.exit();
    }
    
    public void setCandidateId(int id) {
        eCandidateId = id;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void setCandidateId(String toString) {
        
        candidateId.setText("ID : "+toString);
    }

    void setCandidateName(String string) {
        candidateName.setText("Name : "+string);
    }

    void setCandidateAge(String toString) {
        candidateAge.setText("Age : "+toString);
    }

    void setCandidateEmail(String string) {
        candidateEmail.setText("Email : "+string);
    }

    void setCandidateContact(String string) {
        candidateContact.setText("Contact Number : "+string);
    }

    void setCandidateExp(String string) {
        candidateExp.setText("Job experience : "+string);
    }

    void setCandidateAddress(String string) {
        candidateAddress.setText("Present Address : "+string);
    }

    void setCandidateAcademic(String string) {
        candidateAcademic.setText("Highest academic certificate achieved : "+string);
    }

    void setCandidateExtra(String string) {
        candidateExtra.setText("Extra curricular acievement/s : "+string);
    }

    void setCandidateSkill(String string) {
        candidateSkill.setText("Skill/s : "+string);
    }

    void setCandidatePost(String string) {
        candidatePost.setText("Interested job post : "+string);
    }

    void setCandidateSector(String string) {
        candidateSector.setText("Interested Job Sector : "+string);
    }
    
}
