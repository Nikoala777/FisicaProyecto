package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.ServicesFX;
import view.MRUScene;

public class FXMLFisicaController implements Initializable {
    
    private Scene mainScene;  // Escena principal del menú
    private ServicesFX services= new ServicesFX();

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button botonStart;

    @FXML
    private void EventButtonStart(ActionEvent evt) throws Exception {
        try {
            // Crear la instancia de MRUScene
            MRUScene mruScene = new MRUScene();
            // Llamar a su método start() para abrir la nueva escena
            mruScene.start(new Stage());
            mainScene = MRUScene.getSubScene().getRoot().getScene();
            Stage currentStage = (Stage) mainPane.getScene().getWindow();
            currentStage.setScene(mainScene);
            currentStage.show(); // Asegurarse de que la ventana se actualice
            System.out.println("owo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Inicialización del controlador
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
