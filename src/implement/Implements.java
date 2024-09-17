package implement;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import static services.ServicesMRU.desplazamiento;
import static services.ServicesMRU.tiempo;
import static services.ServicesMRU.velocidad;

/**
 *
 * @author sergiopro
 */
public class Implements {
    public static XYChart.Series<Number, Number> serieXT = new XYChart.Series<>();
    public static XYChart.Series<Number, Number> serieVT = new XYChart.Series<>();

    
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
        cameraFollow.setFieldOfView(100);
        cameraFollow.setTranslateY(-2000);
        cameraFollow.setTranslateZ(-1000);
        cameraFollow.setRotationAxis(Rotate.X_AXIS);  // Eje de rotación
        cameraFollow.setRotate(-60);  // Inicializa la rotación
    }
    
    public static void setMovement(Sphere particula) {
        // Variables para controlar el desplazamiento
        velocidad = desplazamiento / tiempo; // metros/segundo
        final long[] previousTime = {0};
        final double[] tiempoTranscurrido = {0}; // Mantiene el tiempo acumulado

        // Usar AnimationTimer en lugar de Timeline para controlar el frame rate
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (previousTime[0] == 0) {
                    previousTime[0] = now;
                    return;
                }


                // Calcular el tiempo transcurrido entre frames (en segundos)
                double deltaTime = (now - previousTime[0]) / 1_000_000_000.0;
                previousTime[0] = now;
                tiempoTranscurrido[0] += deltaTime;

                // Calcular el desplazamiento
                double desplazamientoActual = velocidad * deltaTime;
                particula.setTranslateX(particula.getTranslateX() + desplazamientoActual);
                
                // Actualizar la gráfica x(t)
                serieXT.getData().add(new XYChart.Data<>(tiempoTranscurrido[0], particula.getTranslateX()));

                // Actualizar la gráfica v(t) (en este caso la velocidad es constante)
                serieVT.getData().add(new XYChart.Data<>(tiempoTranscurrido[0], velocidad));

                // Detener la animación cuando se haya alcanzado el desplazamiento total
                if (particula.getTranslateX() >= desplazamiento) {
                    this.stop();
                    MRUCalculator calculadora = new MRUCalculator();
                    calculadora.calculosMRU();
                }
            }
        };

        // Iniciar la animación
        timer.start();
        System.out.println("Animación iniciada");
    }
    
    public static void updateGraphicXT(LineChart<Number, Number> graficoXT){
        graficoXT.getData().add(serieXT);
    }
    
    public static void updateGraphicVT(LineChart<Number, Number> graficoVT){
        graficoVT.getData().add(serieVT);
    }
}
