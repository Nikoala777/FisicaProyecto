package services;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase de servicios para gestionar la navegación en la aplicación.
 */
public class ServicesFX {
    
    public void LoadNewStage(String url, Event evt) throws Exception {
        ((Node)(evt.getSource())).getScene().getWindow().hide();
        
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene scene= new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.show();
        
    }
}
