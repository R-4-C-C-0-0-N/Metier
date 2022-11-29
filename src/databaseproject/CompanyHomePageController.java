
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
import javafx.stage.Stage;

public class CompanyHomePageController implements Initializable {
    int companyId = 0;
    Database db = new Database();

    @FXML
    private void homeAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserType.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        System.out.println(companyId);
    }
    
    @FXML
    private void applicationPostAction(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("JobPost.fxml"));
        Parent root = loader.load();
        JobPostController controller = (JobPostController) loader.getController();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        controller.setCompanyId(companyId);
    }
    
    @FXML
    private void candidateSearchAction(ActionEvent event) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("CandidateSearch.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        //System.out.println(candidateId);
    }
    
    @FXML
    private void applicationsAction(ActionEvent event) throws IOException, SQLException {
        System.out.println(companyId);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Applications.fxml"));
        Parent root = loader.load();
        ApplicationsController controller = (ApplicationsController) loader.getController();
        controller.setCompanyId(companyId);
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        //controller.setCompanyId(companyId);
    }
    
    @FXML
    private void companyProfileAction(ActionEvent event) throws IOException, SQLException {

        try {
 
            PreparedStatement stmt = db.getCon().prepareStatement("select CompanyId,CompanyName,CompanyEmail,CompanyContactNumber,OrganizationType,OfficeAddress from COMPANY where CompanyId = ?");
            stmt.setInt(1, companyId);
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
                controller.setFlag(2);
            }
            stmt.close();

        } catch (Exception e) {

        }
    }

    @FXML
    private void companyProfileEditAction(ActionEvent event) throws IOException, SQLException {

        try {
            PreparedStatement stmt = db.getCon().prepareStatement("select CompanyName,CompanyEmail,CompanyContactNumber,CompanyPassword,OfficeAddress from COMPANY where CompanyId = ?");
            stmt.setInt(1, companyId);
            ResultSet resultSet = stmt.executeQuery();;
            System.out.println(companyId);
            while (resultSet.next()) {
                System.out.println("lala");
                //list.addAll(new CandidateSearchController.Candidates(resultSet.getInt("CandidateId"), resultSet.getString("CandidateName"), resultSet.getInt("CandidateAge"), resultSet.getString("CandidateEmail"), resultSet.getString("CandidateSkill"), resultSet.getString("CandidateExperience")));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CompanyProfileEdit.fxml"));
                Parent root = loader.load();
                CompanyProfileEditController controller = (CompanyProfileEditController) loader.getController();
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
                //System.out.println(resultSet.getString("CandidateExperience"));
                //controller.setCompanyId(Integer.toString(resultSet.getInt("CompanyID")));
                controller.setCompanyName(resultSet.getString("CompanyName"));
                //controller.setCandidateAge(Integer.toString(resultSet.getInt("CandidateAge")));
                controller.setCompanyEmail(resultSet.getString("CompanyEmail"));
                controller.setCompanyContact(resultSet.getString("CompanyContactNumber"));
                controller.setCompanyPassword(resultSet.getString("CompanyPassword"));
                controller.setCompanyAddress(resultSet.getString("OfficeAddress"));
                controller.setCompanyId(companyId);
            }
            stmt.close();

        } catch (Exception e) {

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
    }

}
