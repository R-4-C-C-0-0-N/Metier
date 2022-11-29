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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TopJobsController implements Initializable {

    ObservableList<Jobs> list = FXCollections.observableArrayList();
    Database db = new Database();
    int candidateId = 0;

    @FXML
    private TableView<Jobs> jobResults;
    @FXML
    private TableColumn<Jobs, String> tJobSector;
    @FXML
    private TableColumn<Jobs, String> tJobPost;
    @FXML
    private TableColumn<Jobs, String> tJobRequiredSkill;
    @FXML
    private TableColumn<Jobs, String> tJobExperience;
    @FXML
    private TableColumn<Jobs, Integer> tJobId;
    @FXML
    private TableColumn<Jobs, Integer> tCompanyId;
    @FXML
    private TableColumn<Jobs, Integer> tJobSalary;
    @FXML
    private TableColumn<Jobs, Integer> tJobMinimumAge;

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CandidateHomePage.fxml"));
        Parent root = loader.load();
        CandidateHomePageController controller = (CandidateHomePageController) loader.getController();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        controller.setCandidateId(candidateId);
    }

    @FXML
    private void exitAction(ActionEvent event) throws IOException {
        Platform.exit();
    }

    public void setCandidateId(int id) {
        candidateId = id;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColumns();
        try {
            db.dbConnect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CandidateLogInController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CandidateLogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        topJobs();
    }

    private void initColumns() {
        tJobId.setCellValueFactory(new PropertyValueFactory<>("jJobId"));
        tCompanyId.setCellValueFactory(new PropertyValueFactory<>("jCompanyId"));
        tJobSector.setCellValueFactory(new PropertyValueFactory<>("jJobSector"));
        tJobPost.setCellValueFactory(new PropertyValueFactory<>("jJobPost"));
        tJobSalary.setCellValueFactory(new PropertyValueFactory<>("jJobSalary"));
        tJobRequiredSkill.setCellValueFactory(new PropertyValueFactory<>("jJobRequiredSkill"));
        tJobExperience.setCellValueFactory(new PropertyValueFactory<>("jJobExperience"));
        tJobMinimumAge.setCellValueFactory(new PropertyValueFactory<>("jJobMinimumAge"));
    }

    public void topJobs() {

        try {

            PreparedStatement stmt = db.getCon().prepareStatement("select JobSector,JobPost,CandidateRequiredSkill,CandidateRequiredExperience,JobId,CompanyId,JobSalary,CandidateAgeRange from JOB where JobSalary in(select max(JobSalary) from JOB group by JobSector)");

            ResultSet resultSet = stmt.executeQuery();;

            while (resultSet.next()) {
                //initColumns();
                list.addAll(new Jobs(resultSet.getInt("JobId"), resultSet.getInt("CompanyId"), resultSet.getString("JobSector"), resultSet.getString("JobPost"), resultSet.getInt("JobSalary"), resultSet.getString("CandidateRequiredSkill"), resultSet.getString("CandidateRequiredExperience"), resultSet.getInt("CandidateAgeRange")));
                System.out.println(resultSet.getString("JobId") + resultSet.getString("JobSector"));
            }
            stmt.close();
            //initColumns();
            jobResults.getItems().addAll(list);
            //initColumns();
        } catch (Exception e) {

        }
    }

    public static class Jobs {

        private final SimpleStringProperty jJobSector;
        private final SimpleStringProperty jJobPost;
        private final SimpleStringProperty jJobRequiredSkill;
        private final SimpleStringProperty jJobExperience;
        private final SimpleIntegerProperty jJobId;
        private final SimpleIntegerProperty jCompanyId;
        private final SimpleIntegerProperty jJobSalary;
        private final SimpleIntegerProperty jJobMinimumAge;

        public Jobs(int jJobId, int jCompanyId, String jJobSector, String jJobPost, int jJobSalary, String jJobRequiredSkill, String jJobExperience, int jJobMinimumAge) {
            this.jJobId = new SimpleIntegerProperty(jJobId);
            this.jCompanyId = new SimpleIntegerProperty(jCompanyId);
            this.jJobSector = new SimpleStringProperty(jJobSector);
            this.jJobPost = new SimpleStringProperty(jJobPost);
            this.jJobSalary = new SimpleIntegerProperty(jJobSalary);
            this.jJobRequiredSkill = new SimpleStringProperty(jJobRequiredSkill);
            this.jJobExperience = new SimpleStringProperty(jJobExperience);
            this.jJobMinimumAge = new SimpleIntegerProperty(jJobMinimumAge);
        }

        public String getJJobSector() {
            return jJobSector.get();
        }

        public String getJJobPost() {
            return jJobPost.get();
        }

        public String getJJobRequiredSkill() {
            return jJobRequiredSkill.get();
        }

        public String getJJobExperience() {
            return jJobExperience.get();
        }

        public int getJJobId() {
            return jJobId.get();
        }

        public int getJCompanyId() {
            return jCompanyId.get();
        }

        public int getJJobSalary() {
            return jJobSalary.get();
        }

        public int getJJobMinimumAge() {
            return jJobMinimumAge.get();
        }

    }
}
