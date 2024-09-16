package services;

import implement.Implements;
import java.awt.BorderLayout;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

/**
 *
 * @author sergiopro
 */
public class ServicesMRU {
    private ServicesFX services= new ServicesFX();
    Implements implement = new Implements();
    public static double desplazamiento=0;
    public static double tiempo=0;
    
    public VBox createControlPanel(SubScene scene, PerspectiveCamera cameraFollow, PerspectiveCamera cameraExternal, Group root3D) {
        VBox controlPanel = new VBox(10);       
        controlPanel.setPadding(new Insets(10));       
        
        Box floor = new Box(10000, 10, 10000); // Tamaño del piso  
        implement.preFloor(floor, root3D);
        
        
        Sphere particula = new Sphere(200);
        implement.preParticula(particula, root3D);
        
        Button xButton = new Button("Definir Desplazamiento");
        xButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, rgb(126,188,137), rgb(162,217,206));" + // Degradado de verde
            "-fx-text-fill: white;" +  // Texto en blanco
            "-fx-font-weight: bold;" +  // Texto en negrita
            "-fx-border-color: rgb(126,188,137);" +  // Borde del botón
            "-fx-border-radius: 5;" +  // Bordes redondeados
            "-fx-background-radius: 5;"  // Bordes redondeados para el fondo
        );        
        xButton.setOnAction(e -> showXDialog(particula)); 
        
        Button tButton = new Button("Definir Tiempo");
        tButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, rgb(126,188,137), rgb(162,217,206));" + // Degradado de verde
            "-fx-text-fill: white;" +  // Texto en blanco
            "-fx-font-weight: bold;" +  // Texto en negrita
            "-fx-border-color: rgb(126,188,137);" +  // Borde del botón
            "-fx-border-radius: 5;" +  // Bordes redondeados
            "-fx-background-radius: 5;"  // Bordes redondeados para el fondo
        );
        tButton.setOnAction(e -> showTDialog(particula));
        
        Button x_tButton = new Button("x/t");
        x_tButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, rgb(126,188,137), rgb(162,217,206));" + // Degradado de verde
            "-fx-text-fill: white;" +  // Texto en blanco
            "-fx-font-weight: bold;" +  // Texto en negrita
            "-fx-border-color: rgb(126,188,137);" +  // Borde del botón
            "-fx-border-radius: 5;" +  // Bordes redondeados
            "-fx-background-radius: 5;"  // Bordes redondeados para el fondo
        );
        x_tButton.setOnAction(e -> x_t());        
        
        Button v_tButton = new Button("v/t");
        v_tButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, rgb(126,188,137), rgb(162,217,206));" + // Degradado de verde
            "-fx-text-fill: white;" +  // Texto en blanco
            "-fx-font-weight: bold;" +  // Texto en negrita
            "-fx-border-color: rgb(126,188,137);" +  // Borde del botón
            "-fx-border-radius: 5;" +  // Bordes redondeados
            "-fx-background-radius: 5;"  // Bordes redondeados para el fondo
        );       
        v_tButton.setOnAction(e -> v_t());
        
        Button switchCameraButton = new Button("Cambiar cámara");
        switchCameraButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, rgb(126,188,137), rgb(162,217,206));" + // Degradado de verde
            "-fx-text-fill: white;" +  // Texto en blanco
            "-fx-font-weight: bold;" +  // Texto en negrita
            "-fx-border-color: rgb(126,188,137);" +  // Borde del botón
            "-fx-border-radius: 5;" +  // Bordes redondeados
            "-fx-background-radius: 5;"  // Bordes redondeados para el fondo
        ); 
        switchCameraButton.setOnAction(event -> camaraSwitch(scene, cameraExternal, cameraFollow));
        
        // Botón de iluminación
        Button volverButton = new Button("Volver");
        volverButton.setStyle(
            "-fx-background-color: rgb(245,245,220);" +  // Beige claro
            "-fx-text-fill: black;" +  // Texto en negro
            "-fx-font-weight: bold;" +
            "-fx-border-color: rgb(210,180,140);" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;"
        );
        volverButton.setOnAction(e -> volverDialog(e) );               
        
        controlPanel.setAlignment(Pos.CENTER);            
        controlPanel.getChildren().addAll(xButton, tButton, x_tButton, v_tButton, switchCameraButton, volverButton);
        controlPanel.setStyle(
            "-fx-background-color: rgb(245,245,220);" +  // Fondo beige claro
            "-fx-border-color: rgb(210,180,140);" +  // Borde color tierra
            "-fx-border-width: 2px;" +  // Grosor del borde
            "-fx-border-radius: 10px;"  // Bordes redondeados
        );        
        return controlPanel;
    }
    
    private void showXDialog(Sphere particula) {
        desplazamiento=0;
        Stage dialog = new Stage();
        dialog.setTitle("Elija el Desplazamiento en metros que desea ver reflejado");

        TextField textoDesplazamiento = new TextField("Desplazamiento (x)");
        

        Button xButton = new Button("Definir Desplazamiento");
        xButton.setOnAction(e -> {
            try {
                desplazamiento=Double.parseDouble(textoDesplazamiento.getText());
                dialog.close();
                if(desplazamiento!=0 && tiempo>0){
                            implement.setMovement(particula);
                }
            } 
            catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Valor invalido");
                alert.setContentText("Por favor ingrese un valor de tipo Double");
                alert.showAndWait();
            }
        });
            
        VBox vbox = new VBox(textoDesplazamiento, xButton);
        Scene dialogScene = new Scene(vbox, 300, 250);
        dialog.setScene(dialogScene);
        dialog.show();        
    }
    
    private void showTDialog(Sphere particula) {
        tiempo=0;
        Stage dialog = new Stage();
        dialog.setTitle("Defina el tiempo en el cual se desplazará");

        TextField textoTiempo = new TextField("Tiempo (t)");

        Button createButton = new Button("Definir Tiempo");
        createButton.setOnAction(e -> {
            try {
                tiempo=Double.parseDouble(textoTiempo.getText());
                dialog.close();
                if(desplazamiento!=0 && tiempo>0){
                            implement.setMovement(particula);
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Dimensiones inválidas");
                alert.setContentText("Por favor ingrese dimensiones válidas.");
                alert.showAndWait();
            }
        });

        VBox vbox = new VBox(textoTiempo, createButton);
        Scene dialogScene = new Scene(vbox, 300, 250);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    
    private void x_t() {
        
    }
    
    private void v_t() {
        
    }
    
    private void camaraSwitch(SubScene scene, PerspectiveCamera cameraExternal, PerspectiveCamera cameraInternal) {
         if (scene.getCamera() == cameraExternal) {
                scene.setCamera(cameraInternal);
                cameraInternal.setTranslateX(0); 
                cameraInternal.setTranslateY(0);
                cameraInternal.setTranslateZ(0);
                cameraInternal.setRotate(0);
                scene.requestFocus();
                
            } else {
                scene.setCamera(cameraExternal);
                scene.setCursor(Cursor.DEFAULT);
                scene.requestFocus();
            }
    }
    
    private void volverDialog(ActionEvent evt){
        try{
            services.LoadNewStage("/view/FXMLFisica.fxml" ,evt);
        }catch (Exception ex) {
                Logger.getLogger(ServicesMRU.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
