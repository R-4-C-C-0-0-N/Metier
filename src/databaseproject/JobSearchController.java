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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class JobSearchController implements Initializable {

    ObservableList<Jobs> list = FXCollections.observableArrayList();
    Database db = new Database();
    int candidateId = 0;
    
    @FXML
    public ComboBox<String> vJobSector, vJobPost, vSkill1, vSkill2, vSkill3, vJobMinimumAge;
    public TextField vJobSalary, companyId;
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
    private void jobSearchAction(ActionEvent event) throws IOException, SQLException {
        
        list.removeAll(list);
        jobResults.getItems().addAll(list);
        try {
            String sJobSector, sJobPost, sSkill1, sSkill2, sSkill3;
            int sJobSalary, sJobMinimumAge;
            sJobSector = vJobSector.getValue();
            sJobPost = vJobPost.getValue();
            sSkill1 = vSkill1.getValue();
            sSkill2 = vSkill2.getValue();
            sSkill3 = vSkill3.getValue();
            sJobSalary = Integer.parseInt(vJobSalary.getText());
            sJobMinimumAge = Integer.parseInt(vJobMinimumAge.getValue());
            
            PreparedStatement stmt = db.getCon().prepareStatement("select JobSector,JobPost,CandidateRequiredSkill,CandidateRequiredExperience,JobId,CompanyId,JobSalary,CandidateAgeRange from JOB where JobSector like ? AND JobPost like ? AND JobSalary >= ? AND CandidateAgeRange >= ? AND (CandidateRequiredSkill like ? OR CandidateRequiredSkill like ? OR CandidateRequiredSkill like ?)");
            stmt.setString(1, "%"+sJobSector+"%");
            stmt.setString(2, "%"+sJobPost+"%");
            stmt.setInt(3, sJobSalary);
            stmt.setInt(4, sJobMinimumAge);
            stmt.setString(5, "%"+sSkill1+"%");
            stmt.setString(6, "%"+sSkill2+"%");
            stmt.setString(7, "%"+sSkill3+"%");
            ResultSet resultSet = stmt.executeQuery();;
            System.out.println(sJobSector+sJobPost+sSkill1+sSkill2+sSkill3+sJobSalary+sJobMinimumAge);
            while (resultSet.next()) {
                //initColumns();
                System.out.println("lala");
                list.addAll(new Jobs(resultSet.getInt("JobId"), resultSet.getInt("CompanyId"), resultSet.getString("JobSector"), resultSet.getString("JobPost"), resultSet.getInt("JobSalary"), resultSet.getString("CandidateRequiredSkill"), resultSet.getString("CandidateRequiredExperience"), resultSet.getInt("CandidateAgeRange")));
                System.out.println(resultSet.getString("JobId")+resultSet.getString("JobSector"));
            }
            stmt.close();
            //initColumns();
            jobResults.getItems().addAll(list);
            //initColumns();
        } catch (Exception e) {
            
        }
    }
    
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
    
    @FXML
    private void companyProfileAction(ActionEvent event) throws IOException, SQLException {

        try {
            int cid = Integer.parseInt(companyId.getText());
            PreparedStatement stmt = db.getCon().prepareStatement("select CompanyId,CompanyName,CompanyEmail,CompanyContactNumber,OrganizationType,OfficeAddress from COMPANY where CompanyId = ?");
            stmt.setInt(1, cid);
            ResultSet resultSet = stmt.executeQuery();;
            System.out.println(companyId);
            while (resultSet.next()) {
                System.out.println("lala");
                //list.addAll(new CandidateSearchController.Candidates(resultSet.getInt("CandidateId"), resultSet.getString("CandidateName"), resultSet.getInt("CandidateAge"), resultSet.getString("CandidateEmail"), resultSet.getString("CandidateSkill"), resultSet.getString("CandidateExperience")));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CompanyProfile.fxml"));
                Parent root = loader.load();
                CompanyProfileController controller = (CompanyProfileController) loader.getController();
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
                //System.out.println(resultSet.getString("CandidateExperience"));
                controller.setCompanyId(Integer.toString(resultSet.getInt("CompanyID")));
                controller.setCompanyName(resultSet.getString("CompanyName"));
                //controller.setCandidateAge(Integer.toString(resultSet.getInt("CandidateAge")));
                controller.setCompanyEmail(resultSet.getString("CompanyEmail"));
                controller.setCompanyContact(resultSet.getString("CompanyContactNumber"));
                controller.setCompanyType(resultSet.getString("OrganizationType"));
                controller.setCompanyAddress(resultSet.getString("OfficeAddress"));
                controller.setFlag(1);
                controller.setCId(cid);
                controller.setCaId(candidateId);
            }
            stmt.close();

        } catch (Exception e) {

        }
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
        vJobMinimumAge.getItems().addAll("18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40",
                              "41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59");
        vSkill1.getItems().addAll("Programming C", "Programming c#", "Programming Java", "Programming Python", "Database", "Web Design", "MS Office",
                "Server Management", "Cyber Security", "Leadership", "Graphics Design", "Human psychology analysis");
        vSkill2.getItems().addAll("Programming C", "Programming c#", "Programming Java", "Programming Python", "Database", "Web Design", "MS Office",
                "Server Management", "Cyber Security", "Leadership", "Graphics Design", "Human psychology analysis");
        vSkill3.getItems().addAll("Programming C", "Programming c#", "Programming Java", "Programming Python", "Database", "Web Design", "MS Office",
                "Server Management", "Cyber Security", "Leadership", "Graphics Design", "Human psychology analysis");
        vJobSector.getItems().addAll("Accounting/Finance", "Coomercial", "Education", "Engineer/Architects", "Garments/Textile", "Design/Creative", 
                "IT/Telecommunication", "Marketing/Sales", "Media", "Agro", "Research", "Security", "Law/Legal");
        vJobPost.getItems().addAll("Manager", "Chief Executive", "Financial Manager", "Accountant", "Auditor", "Physician", "Lawyer", "Sales people", 
                "Software Developer", "Secretary", "Web designer", "Materials ngineer", "Reliability engineer", "R&D engineer", 
                "Customer relationship specialist");
    }
    
    private void initColumns(){
        tJobId.setCellValueFactory(new PropertyValueFactory<>("jJobId"));
        tCompanyId.setCellValueFactory(new PropertyValueFactory<>("jCompanyId"));
        tJobSector.setCellValueFactory(new PropertyValueFactory<>("jJobSector"));
        tJobPost.setCellValueFactory(new PropertyValueFactory<>("jJobPost"));
        tJobSalary.setCellValueFactory(new PropertyValueFactory<>("jJobSalary"));
        tJobRequiredSkill.setCellValueFactory(new PropertyValueFactory<>("jJobRequiredSkill"));
        tJobExperience.setCellValueFactory(new PropertyValueFactory<>("jJobExperience"));
        tJobMinimumAge.setCellValueFactory(new PropertyValueFactory<>("jJobMinimumAge"));
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
