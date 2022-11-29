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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class JobPostController implements Initializable {

    int companyId = 0;

    Database db = new Database();
    @FXML
    public TextField candidateSkill, candidateExp, candidateSector, candidatePost, candidateSalary;
    public ComboBox<String> age, skill, exp, sector, post;
    public Label error;

    @FXML
    private void addSkillAction(ActionEvent event) throws IOException {
        if (candidateSkill.getText().isEmpty()) {
            candidateSkill.setText(skill.getValue());
        } else {
            candidateSkill.setText(candidateSkill.getText() + ", " + skill.getValue());
        }
    }

    @FXML
    private void addExpAction(ActionEvent event) throws IOException {
        candidateExp.setText(exp.getValue());
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
    private void jobPostAction(ActionEvent event) throws IOException, SQLException {
        System.out.println(companyId);
        try {
            String tpost, tskill, texp, tsector;
            int tage, tsalary;
            tpost = candidatePost.getText();
            tskill = candidateSkill.getText();
            texp = candidateExp.getText();
            tsector = candidateSector.getText();
            tage = Integer.parseInt(age.getValue());
            tsalary = Integer.parseInt(candidateSalary.getText());

            PreparedStatement stmt = db.getCon().prepareStatement("insert into JOB(JobPost,CandidateRequiredSkill,CandidateAgeRange,JobSalary,CandidateRequiredExperience,CompanyId,JobSector) VALUES(?,?,?,?,?,?,?)");
            stmt.setString(1, tpost);
            stmt.setString(2, tskill);
            stmt.setInt(3, tage);
            stmt.setInt(4, tsalary);
            stmt.setString(5, texp);
            stmt.setInt(6, companyId);
            stmt.setString(7, tsector);

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

    public void setCompanyId(int id) {
        companyId = id;
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
