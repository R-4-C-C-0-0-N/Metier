package databaseproject;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CandidateSearchController implements Initializable {

    ObservableList<Candidates> list = FXCollections.observableArrayList();
    Database db = new Database();

    @FXML
    public ComboBox<String> vCandidateMinimumAge, vSkill1, vSkill2, vSkill3, vCandidateExperience, vAcademic;

    @FXML
    private TableView<Candidates> candidateResults;
    @FXML
    private TableColumn<Candidates, Integer> tCandidateId;
    @FXML
    private TableColumn<Candidates, String> tCandidateName;
    @FXML
    private TableColumn<Candidates, Integer> tCandidateAge;
    @FXML
    private TableColumn<Candidates, String> tCandidateEmail;
    @FXML
    private TableColumn<Candidates, String> tCandidateSkill;
    @FXML
    private TableColumn<Candidates, String> tCandidateExperience;

    @FXML
    private void candidateSearchAction(ActionEvent event) throws IOException, SQLException {
        initColumns();
        try {
            String sSkill1, sSkill2, sSkill3, sCandidateExperience, sAcademic;
            int sCandidateMinimumAge;
            
            sCandidateMinimumAge = Integer.parseInt(vCandidateMinimumAge.getValue());
            sSkill1 = vSkill1.getValue();
            sSkill2 = vSkill2.getValue();
            sSkill3 = vSkill3.getValue();
            sCandidateExperience = vCandidateExperience.getValue();
            sAcademic = vAcademic.getValue();
            

            PreparedStatement stmt = db.getCon().prepareStatement("select CandidateId,CandidateName,CandidateAge,CandidateEmail,CandidateSkill,CandidateExperience from CANDIDATE where CandidateAge >= ? AND (CandidateSkill like ? OR CandidateSkill like ? OR CandidateSkill like ?) AND CandidateExperience LIKE ? AND CandidateAcademicQualification LIKE ?");
            stmt.setInt(1, sCandidateMinimumAge);
            stmt.setString(2, sSkill1);
            stmt.setString(3, sSkill2);
            stmt.setString(4, sSkill3);
            stmt.setString(5, sCandidateExperience);
            stmt.setString(6, sAcademic);
            ResultSet resultSet = stmt.executeQuery();;

            while (resultSet.next()) {
                System.out.println("lala");
                list.addAll(new Candidates(resultSet.getInt("CandidateId"), resultSet.getString("CandidateName"), resultSet.getInt("CandidateAge"), resultSet.getString("CandidateEmail"), resultSet.getString("CandidateSkill"), resultSet.getString("CandidateExperience")));
               
            }
            stmt.close();
            candidateResults.getItems().addAll(list);
        } catch (Exception e) {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColumns();
        //loadData();
        try {
            db.dbConnect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CandidateLogInController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CandidateLogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        vCandidateMinimumAge.getItems().addAll("18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40",
                              "41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59");
        vSkill1.getItems().addAll("Programming C", "Programming c#", "Programming Java", "Programming Python", "Database", "Web Design", "MS Office",
                               "Server Management", "Cyber Security", "Leadership", "Graphics Design");
        vSkill2.getItems().addAll("Programming C", "Programming c#", "Programming Java", "Programming Python", "Database", "Web Design", "MS Office",
                               "Server Management", "Cyber Security", "Leadership", "Graphics Design");
        vSkill3.getItems().addAll("Programming C", "Programming c#", "Programming Java", "Programming Python", "Database", "Web Design", "MS Office",
                               "Server Management", "Cyber Security", "Leadership", "Graphics Design");
        vCandidateExperience.getItems().addAll("None", "One year", "Two years", "Three years", "Four years", "Five years");
        vAcademic.getItems().addAll("SSC passed", "HSC passed", "BSc graduate", "MSc graduate", "Post Doctorate");
    }
    
    private void initColumns(){
        tCandidateId.setCellValueFactory(new PropertyValueFactory<>("jCandidateId"));
        tCandidateName.setCellValueFactory(new PropertyValueFactory<>("jCandidateName"));
        tCandidateAge.setCellValueFactory(new PropertyValueFactory<>("jCandidateAge"));
        tCandidateEmail.setCellValueFactory(new PropertyValueFactory<>("jCandidateEmail"));
        tCandidateSkill.setCellValueFactory(new PropertyValueFactory<>("jCandidateSkill"));
        tCandidateExperience.setCellValueFactory(new PropertyValueFactory<>("jCandidateExperience"));
    }
    
    public void loadData(){
        list.addAll(new Candidates(1,"lala",1,"lala","lala","lala"));
        candidateResults.getItems().addAll(list);
    }
    
    public static class Candidates {

        private final SimpleIntegerProperty jCandidateId;
        private final SimpleStringProperty jCandidateName;
        private final SimpleIntegerProperty jCandidateAge;
        private final SimpleStringProperty jCandidateEmail;
        private final SimpleStringProperty jCandidateSkill;
        private final SimpleStringProperty jCandidateExperience;
        

        public Candidates(int jCandidateId, String jCandidateName, int jCandidateAge, String jCandidateEmail, String jCandidateSkill, String jCandidateExperience) {
            this.jCandidateId = new SimpleIntegerProperty(jCandidateId);
            this.jCandidateName = new SimpleStringProperty(jCandidateName);
            this.jCandidateAge = new SimpleIntegerProperty(jCandidateAge);
            this.jCandidateEmail = new SimpleStringProperty(jCandidateEmail);
            this.jCandidateSkill = new SimpleStringProperty(jCandidateSkill);
            this.jCandidateExperience = new SimpleStringProperty(jCandidateExperience);
        }

        public int getJCandidateId() {
            return jCandidateId.get();
        }

        public String getJCandidateName() {
            return jCandidateName.get();
        }

        public int getJCandidateAge() {
            return jCandidateAge.get();
        }

        public String getJCandidateEmail() {
            return jCandidateEmail.get();
        }

        public String getJCandidateSkill() {
            return jCandidateSkill.get();
        }

        public String getJCandidateExperience() {
            return jCandidateExperience.get();
        }
        
        
    }

}
