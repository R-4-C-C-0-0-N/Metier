package databaseproject;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CandidateSignUpController implements Initializable {

    Database db = new Database();

    @FXML
    public TextField candidateName, candidateEmail, candidatePassword, candidateContact, candidateAddress, candidateAcademic, candidateSkill, candidateCurricular, candidateSector, candidatePost;
    public ComboBox<String> age, academic, skill, exp, sector, post;
    public Label error;

    @FXML
    private void addAcademicAction(ActionEvent event) throws IOException {
        candidateAcademic.setText(academic.getValue());
    }

    @FXML
    private void addSkillAction(ActionEvent event) throws IOException {
        if (candidateSkill.getText().isEmpty()) {
            candidateSkill.setText(skill.getValue());
        } else {
            candidateSkill.setText(candidateSkill.getText() + ", " + skill.getValue());
        }
    }

    @FXML
    private void addSectorAction(ActionEvent event) throws IOException {
        if (candidateSector.getText().isEmpty()) {
            candidateSector.setText(sector.getValue());
        } else {
            candidateSector.setText(candidateSector.getText() + ", " + sector.getValue());
        }
    }

    @FXML
    private void addPostAction(ActionEvent event) throws IOException {
        if (candidatePost.getText().isEmpty()) {
            candidatePost.setText(post.getValue());
        } else {
            candidatePost.setText(candidatePost.getText() + ", " + post.getValue());
        }
    }

    @FXML
    private void candidateSignUpSubmitAction(ActionEvent event) throws IOException, SQLException {
        try {
            String tname, temail, tpassword, taddress, tcontact, tacademic, tskill, texp, tcurricular, tsector, tpost;
            int tage;
            tname = candidateName.getText();
            temail = candidateEmail.getText();
            tpassword = candidatePassword.getText();
            tage = Integer.parseInt(age.getValue());
            taddress = candidateAddress.getText();
            tcontact = candidateContact.getText();
            tacademic = candidateAcademic.getText();
            tskill = candidateSkill.getText();
            texp = exp.getValue();
            tcurricular = candidateCurricular.getText();
            tsector = candidateSector.getText();
            tpost = candidatePost.getText();
            PreparedStatement stmt = db.getCon().prepareStatement("insert into CANDIDATE(CandidateName,CandidateExperience,CandidateAge,CandidateContactNumber,CandidateEmail,CandidateSkill,CandidatePresentAddress,CandidateAcademicQualification,CandidateExtraCurricularAchievement,CandidateInterestedPost,CandidateInterestedSector,CandidatePassword) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, tname);
            stmt.setString(2, texp);
            stmt.setInt(3, tage);
            stmt.setString(4, tcontact);
            stmt.setString(5, temail);
            stmt.setString(6, tskill);
            stmt.setString(7, taddress);
            stmt.setString(8, tacademic);
            stmt.setString(9, tcurricular);
            stmt.setString(10, tpost);
            stmt.setString(11, tsector);
            stmt.setString(12, tpassword);
            int s = stmt.executeUpdate();
            if (s > 0) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CandidateLogIn.fxml"));
                Parent root = loader.load();
                CandidateLogInController controller = (CandidateLogInController) loader.getController();
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
                controller.signUpSuccess();
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
        age.getItems().addAll("18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", 
                "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59");
        academic.getItems().addAll("SSC", "HSC", "BSc", "MSc");
        skill.getItems().addAll("Programming C", "Programming c#", "Programming Java", "Programming Python", "Database", "Web Design", "MS Office",
                "Server Management", "Cyber Security", "Leadership", "Graphics Design", "Human psychology analysis");
        exp.getItems().addAll("None", "One year", "Two years", "Three years", "Four years", "Five years", "Six years", "Seven years", "Eight years", 
                "Nine years", "Ten years");
        sector.getItems().addAll("Accounting/Finance", "Coomercial", "Education", "Engineer/Architects", "Garments/Textile", "Design/Creative", 
                "IT/Telecommunication", "Marketing/Sales", "Media", "Agro", "Research", "Security", "Law/Legal");
        post.getItems().addAll("Manager", "Chief Executive", "Financial Manager", "Accountant", "Auditor", "Physician", "Lawyer", "Sales people", 
                "Software Developer", "Secretary", "Web designer", "Materials ngineer", "Reliability engineer", "R&D engineer", 
                "Customer relationship specialist");
    }
}
