package implement;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import services.ServicesMRU;

/**
 *
 * @author sergiopro
 */
public class Implements {

    
    public void preFloor(Box floor, Group root3D){      
        PhongMaterial matFloor = new PhongMaterial();
        matFloor.setDiffuseColor(Color.GREEN); // Color del piso
        floor.setMaterial(matFloor);
        floor.setTranslateY(-5); // Mover el piso para que esté centrado en el eje Y
        floor.setTranslateX(0);
        floor.setTranslateZ(0);
        root3D.getChildren().add(floor);
    }
    
    public void preParticula(Sphere particula, Group root3D){   
        PhongMaterial matParticula = new PhongMaterial();
        matParticula.setDiffuseColor(Color.AQUA);
        particula.setMaterial(matParticula);
        particula.setTranslateX(0);
        particula.setTranslateZ(0);
        particula.setTranslateY(-300);
        root3D.getChildren().add(particula);
    }
    
    public void preCameraExt(PerspectiveCamera cameraExterna, SubScene scene){
        cameraExterna.setTranslateY(-4000);
        cameraExterna.setTranslateZ(0);
        cameraExterna.setNearClip(1);
        cameraExterna.setFarClip(10000);
        cameraExterna.setFieldOfView(100);
        cameraExterna.setRotationAxis(Rotate.X_AXIS); 
        cameraExterna.setRotate(-90);
        scene.setCamera(cameraExterna);
    }
    
    public void preCameraFollow(PerspectiveCamera cameraFollow, SubScene scene){
        cameraFollow.setNearClip(1);
        cameraFollow.setFarClip(10000);
        cameraFollow.setTranslateX(0); // Inicia dentro del cuarto
        cameraFollow.setTranslateY(0);
        cameraFollow.setTranslateZ(0);
        cameraFollow.setRotationAxis(Rotate.Y_AXIS);  // Eje de rotación
        cameraFollow.setRotate(0);  // Inicializa la rotación
    }
    
    public void setMovement(Sphere particula){
         // Crear la animación usando Timeline
        Timeline movimiento = new Timeline();
        
        // Crear un KeyFrame que se repite cada 16ms (~60 FPS)
        KeyFrame frame = new KeyFrame(Duration.millis(16), event -> {
            // Calcular el desplazamiento por segundo
            double desplazamientoPorSegundo = ServicesMRU.desplazamiento / ServicesMRU.tiempo;
            
            // Obtener el tiempo transcurrido desde el inicio de la animación
            double deltaTime = 16 / 1000.0; // Duración del KeyFrame en segundos

            // Calcular el desplazamiento actual
            double desplazamientoActual = desplazamientoPorSegundo * deltaTime;

            // Actualizar la posición en el eje X
            particula.setTranslateX(particula.getTranslateX() + desplazamientoActual);
        });

        // Establecer la duración total de la animación
        movimiento.getKeyFrames().add(frame);
        
        // Definir la duración de la animación y que se repita durante ese tiempo
        movimiento.setCycleCount((int) (ServicesMRU.tiempo * 1000 / 16));
        
        // Iniciar la animación
        movimiento.play();
        System.out.println("Me invocarooooooooooooooooooooooon");
    }
}
