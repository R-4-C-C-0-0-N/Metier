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

public class CandidateHomePageController implements Initializable {

    Database db = new Database();
    int candidateId = 0;

    @FXML
    private void applicationPostAction(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ApplicationPost.fxml"));
        Parent root = loader.load();
        ApplicationPostController controller = (ApplicationPostController) loader.getController();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        controller.setCandidateId(candidateId);
    }

    @FXML
    private void jobSearchAction(ActionEvent event) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("JobSearch.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        System.out.println(candidateId);
    }

    @FXML
    private void topJobsAction(ActionEvent event) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("TopJobs.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void candidateProfileAction(ActionEvent event) throws IOException, SQLException {

        try {
            String id, age, name, email, contact, skill, exp, address, academic, extra, post, sector;
            System.out.println(candidateId);

            PreparedStatement stmt = db.getCon().prepareStatement("select CandidateId,CandidateName,CandidateAge,CandidateEmail,CandidateContactNumber,CandidateSkill,CandidateExperience,CandidatePresentAddress,CandidateAcademicQualification,CandidateExtraCurricularAchievement,CandidateInterestedPost,CandidateInterestedSector from CANDIDATE where CandidateId = ?");
            stmt.setInt(1, candidateId);
            ResultSet resultSet = stmt.executeQuery();;

            while (resultSet.next()) {
                System.out.println("lala");
                //list.addAll(new CandidateSearchController.Candidates(resultSet.getInt("CandidateId"), resultSet.getString("CandidateName"), resultSet.getInt("CandidateAge"), resultSet.getString("CandidateEmail"), resultSet.getString("CandidateSkill"), resultSet.getString("CandidateExperience")));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CandidateProfile.fxml"));
                Parent root = loader.load();
                CandidateProfileController controller = (CandidateProfileController) loader.getController();
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
                System.out.println(resultSet.getString("CandidateExperience"));
                controller.setCandidateId(Integer.toString(resultSet.getInt("CandidateID")));
                controller.setCandidateName(resultSet.getString("CandidateName"));
                controller.setCandidateAge(Integer.toString(resultSet.getInt("CandidateAge")));
                controller.setCandidateEmail(resultSet.getString("CandidateEmail"));
                controller.setCandidateContact(resultSet.getString("CandidateContactNumber"));
                controller.setCandidateExp(resultSet.getString("CandidateExperience"));
                controller.setCandidateAddress(resultSet.getString("CandidatePresentAddress"));
                controller.setCandidateAcademic(resultSet.getString("CandidateAcademicQualification"));
                controller.setCandidateExtra(resultSet.getString("CandidateExtraCurricularAchievement"));
                controller.setCandidateSkill(resultSet.getString("CandidateSkill"));
                controller.setCandidatePost(resultSet.getString("CandidateInterestedPost"));
                controller.setCandidateSector(resultSet.getString("CandidateInterestedSector"));
                controller.setCandidateId(candidateId);
            }
            stmt.close();

        } catch (Exception e) {

        }
    }
    
    @FXML
    private void candidateProfileEditAction(ActionEvent event) throws IOException, SQLException {

        try {
            

            PreparedStatement stmt = db.getCon().prepareStatement("select CandidatePassword,CandidateName,CandidateAge,CandidateEmail,CandidateContactNumber,CandidateSkill,CandidateExperience,CandidatePresentAddress,CandidateAcademicQualification,CandidateExtraCurricularAchievement,CandidateInterestedPost,CandidateInterestedSector from CANDIDATE where CandidateId = ?");
            stmt.setInt(1, candidateId);
            ResultSet resultSet = stmt.executeQuery();;

            while (resultSet.next()) {
                System.out.println("lala");
                //list.addAll(new CandidateSearchController.Candidates(resultSet.getInt("CandidateId"), resultSet.getString("CandidateName"), resultSet.getInt("CandidateAge"), resultSet.getString("CandidateEmail"), resultSet.getString("CandidateSkill"), resultSet.getString("CandidateExperience")));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CandidateProfileEdit.fxml"));
                Parent root = loader.load();
                CandidateProfileEditController controller = (CandidateProfileEditController) loader.getController();
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
                //System.out.println(resultSet.getString("CandidateExperience"));
                controller.setCandidatePassword(resultSet.getString("CandidatePassword"));
                controller.setCandidateName(resultSet.getString("CandidateName"));
                //controller.setCandidateAge(Integer.toString(resultSet.getInt("CandidateAge")));
                controller.setCandidateEmail(resultSet.getString("CandidateEmail"));
                controller.setCandidateContact(resultSet.getString("CandidateContactNumber"));
                controller.setCandidateExp(resultSet.getString("CandidateExperience"));
                controller.setCandidateAddress(resultSet.getString("CandidatePresentAddress"));
                controller.setCandidateAcademic(resultSet.getString("CandidateAcademicQualification"));
                controller.setCandidateCurricular(resultSet.getString("CandidateExtraCurricularAchievement"));
                controller.setCandidateSkill(resultSet.getString("CandidateSkill"));
                controller.setCandidatePost(resultSet.getString("CandidateInterestedPost"));
                controller.setCandidateSector(resultSet.getString("CandidateInterestedSector"));
                controller.setCandidateId(candidateId);
            }
            stmt.close();

        } catch (Exception e) {

        }
    }

    @FXML
    private void profileEditAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserType.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    public void setCandidateId(int id) {
        candidateId = id;
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
