package view;

import implement.Implements;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import services.ServicesMRU;

/**
 *
 * @author sergiopro
 */
public class MRUScene extends Application{
    private static PerspectiveCamera cameraExterna, cameraFollow;
    private double mouseX, mouseY;
    private double rotateX = 0, rotateY = 0;
    private double rotateSpeed = 0.2;
    private double zoomSpeed = 1.1;
    
    private Group root3D;
    private static SubScene subScene;
    private Shape3D selectedObject;
    private double objectMouseOffsetX, objectMouseOffsetY, objectMouseOffsetZ;
    
    ServicesMRU mru = new ServicesMRU();
    Implements implement = new Implements();
 
    
    @Override
    public void start(Stage primaryStage) {
        // Crear la escena 3D
        root3D = new Group();
        subScene = new SubScene(root3D, 1080, 720, true, javafx.scene.SceneAntialiasing.BALANCED);
        subScene.setFill(Color.SKYBLUE);
        
        //Objetos en Escena
        

        // Configurar la cámara 1
        cameraExterna = new PerspectiveCamera(true);
        implement.preCameraExt(cameraExterna, subScene);
        
        // Cámara interna
        cameraFollow = new PerspectiveCamera(true);
        implement.preCameraFollow(cameraFollow, subScene);
        
        // Crear panel de control
        VBox controlPanel = mru.createControlPanel(subScene, cameraFollow, cameraExterna, root3D);

        // Configurar los eventos del mouse para la subescena
        subScene.setOnMousePressed(this::handleMousePressed);
        subScene.setOnMouseDragged(this::handleMouseDragged);
        subScene.setOnScroll(this::handleScroll); 

        // Crear layout principal
        HBox mainLayout = new HBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(subScene, controlPanel);
        mainLayout.setPrefHeight(1080);
        mainLayout.setPrefWidth(720);
        
        mainLayout.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, rgb(245,245,220), rgb(162,217,206)); " +  // Fondo degradado de beige a verde claro
            "-fx-border-color: rgb(210,180,140); " +  // Color del borde tierra
            "-fx-border-width: 2px; " +  // Grosor del borde
            "-fx-border-radius: 10px; " +  // Bordes redondeados
            "-fx-background-radius: 10px; " +  // Bordes redondeados para el fondo
            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0.5, 0, 0);"  // Efecto de sombra
        );        

        // Crear la escena principal
        Scene mainScene = new Scene(mainLayout, 1280, 720);
        
        // Solicitar el enfoque en el nodo raíz
        root3D.requestFocus();
        
        //Reconocer subEscena
        subScene.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                System.out.println("La subescena tiene el enfoque.");
            } else {
                System.out.println("La subescena perdió el enfoque.");
            }
        });
    }
    
    private void handleMousePressed(MouseEvent event) {
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
    }

    private void handleMouseDragged(MouseEvent event) {
        if (selectedObject == null) {
            double deltaX = event.getSceneX() - mouseX;
            double deltaY = event.getSceneY() - mouseY;
            rotateGroup(deltaX, deltaY);
        }

        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
    }
    
    private void handleScroll(ScrollEvent event) {
        double delta = event.getDeltaY();
        cameraExterna.setTranslateZ(cameraExterna.getTranslateZ() + delta * zoomSpeed);
    }
    
    private void rotateGroup(double deltaX, double deltaY) {
        rotateX += deltaX * rotateSpeed;
        rotateY -= deltaY * rotateSpeed;
        rotateY = Math.max(-89, Math.min(89, rotateY));

        root3D.getTransforms().clear();
        root3D.getTransforms().addAll(
            new Rotate(rotateX, Rotate.Y_AXIS),
            new Rotate(rotateY, Rotate.X_AXIS)
        );
    }
    
    public static SubScene getSubScene() {
        return subScene;
    }
}
