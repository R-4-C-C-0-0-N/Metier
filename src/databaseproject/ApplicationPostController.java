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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ApplicationPostController implements Initializable {

    int candidateId = 0;
    Database db = new Database();

    @FXML
    public TextField jobId, candidateId1, companyId;
    public Label error;

    @FXML
    private void applicationPostAction(ActionEvent event) throws IOException, SQLException {
        try {
            int tjobid, tcandidateid, tcompanyid;
            tjobid = Integer.parseInt(jobId.getText());
            //tcandidateid = Integer.parseInt(candidateId1.getText());
            tcompanyid = Integer.parseInt(companyId.getText());
            PreparedStatement stmt = db.getCon().prepareStatement("insert into APPLICATION(JobId,CandidateId,CompanyId) VALUES(?,?,?)");
            stmt.setInt(1, tjobid);
            stmt.setInt(2, candidateId);
            stmt.setInt(3, tcompanyid);
            int s = stmt.executeUpdate();
            if (s > 0) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CandidateHomePage.fxml"));
                Parent root = loader.load();
                CandidateHomePageController controller = (CandidateHomePageController) loader.getController();
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
                controller.setCandidateId(candidateId);
            }
            stmt.close();
        } catch (Exception e) {
            error.setText("Please fill up all the data properly !!");
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
