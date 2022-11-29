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

public class ApplicationsController implements Initializable {

    int companyId = 0;
    ObservableList<Applications> list = FXCollections.observableArrayList();
    Database db = new Database();

    @FXML
    private TableView<Applications> applicationResults;
    @FXML
    private TableColumn<Applications, Integer> tJobId;
    @FXML
    private TableColumn<Applications, Integer> tCompanyId;
    @FXML
    private TableColumn<Applications, Integer> tApplicationId;
    @FXML
    private TableColumn<Applications, Integer> tCandidateId;

    public void setCompanyId(int id) {
        companyId = id;
    }

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CompanyHomePage.fxml"));
        Parent root = loader.load();
        CompanyHomePageController controller = (CompanyHomePageController) loader.getController();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        controller.setCompanyId(companyId);
        //System.out.println(eCandidateId);
    }

    @FXML
    private void exitAction(ActionEvent event) throws IOException {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(companyId);

        try {
            db.dbConnect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CandidateLogInController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CandidateLogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        applications();
    }

    @FXML
    private void printAction(ActionEvent event) throws IOException {
        System.out.println(companyId);
        initColumns();
        applications();
    }

    private void initColumns() {
        tApplicationId.setCellValueFactory(new PropertyValueFactory<>("jApplicationId"));
        tJobId.setCellValueFactory(new PropertyValueFactory<>("jJobId"));
        tCompanyId.setCellValueFactory(new PropertyValueFactory<>("jCompanyId"));
        tCandidateId.setCellValueFactory(new PropertyValueFactory<>("jCandidateId"));
    }

    public void applications() {

        try {
            System.out.println(companyId);
            PreparedStatement stmt = db.getCon().prepareStatement("select ApplicationId,JobId,CompanyId,CandidateId from APPLICATION where CompanyId = ?");
            stmt.setInt(1, companyId);
            ResultSet resultSet = stmt.executeQuery();;

            while (resultSet.next()) {
                //initColumns();
                list.addAll(new Applications(resultSet.getInt("ApplicationId"), resultSet.getInt("JobId"), resultSet.getInt("CompanyId"), resultSet.getInt("CandidateId")));
                //System.out.println(resultSet.getString("JobId")+resultSet.getString("JobSector"));
            }
            stmt.close();
            //initColumns();
            applicationResults.getItems().addAll(list);
            //initColumns();
        } catch (Exception e) {

        }
    }

    public static class Applications {

        private final SimpleIntegerProperty jApplicationId;
        private final SimpleIntegerProperty jJobId;
        private final SimpleIntegerProperty jCompanyId;
        private final SimpleIntegerProperty jCandidateId;

        public Applications(int jApplicationId, int jJobId, int jCompanyId, int jCandidateId) {
            this.jApplicationId = new SimpleIntegerProperty(jApplicationId);
            this.jJobId = new SimpleIntegerProperty(jJobId);
            this.jCompanyId = new SimpleIntegerProperty(jCompanyId);
            this.jCandidateId = new SimpleIntegerProperty(jCandidateId);
        }

        public int getJApplicationId() {
            return jApplicationId.get();
        }

        public int getJJobId() {
            return jJobId.get();
        }

        public int getJCompanyId() {
            return jCompanyId.get();
        }

        public int getJCandidateId() {
            return jCandidateId.get();
        }

    }

}
