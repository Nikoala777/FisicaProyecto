package implement;

import java.util.function.DoubleUnaryOperator;
import javax.swing.JOptionPane;
import services.ServicesMRU;

public class MRUCalculator {

    // Función x(t): Desplazamiento en función del tiempo
    private DoubleUnaryOperator desplazamientoFuncion = (t) -> {
        return ServicesMRU.velocidad * t; // Movimiento rectilíneo uniforme (x(t) = v * t)
    };

    // Derivada numérica de la función de desplazamiento para calcular la velocidad
    public double derivada(DoubleUnaryOperator funcion, double t, double h) {
        return (funcion.applyAsDouble(t + h) - funcion.applyAsDouble(t)) / h;
    }

    // Integral numérica de la función de velocidad entre t0 y t
    public double integral(DoubleUnaryOperator funcion, double t0, double t, double deltaT) {
        double area = 0.0;
        for (double i = t0; i < t; i += deltaT) {
            area += funcion.applyAsDouble(i) * deltaT;
        }
        return area;
    }

    // Método para calcular la velocidad instantánea en un punto t
    public double calcularVelocidadInstantanea(double t) {
        double h = 1e-5; // Paso pequeño para aproximar la derivada numérica
        return derivada(desplazamientoFuncion, t, h);
    }

    // Método para calcular el desplazamiento acumulado entre t0 y t
    public double calcularDesplazamientoAcumulado(double t0, double t) {
        return integral((tiempo) -> calcularVelocidadInstantanea(tiempo), t0, t, 0.01); // Paso de integración deltaT = 0.01
    }

    public void calculosMRU() {

        double t = ServicesMRU.tiempo; // Ejemplo de tiempo para evaluar
        double velocidadInstantanea = calcularVelocidadInstantanea(t);
        double desplazamientoAcumulado = calcularDesplazamientoAcumulado(0, t);
        

        JOptionPane.showMessageDialog(null, "Velocidad instantánea en t = " + t +
                "s: " + velocidadInstantanea +
                " m/s", "Derivando X(t)...", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null, "Desplazamiento acumulado entre t0 = 0 y t = " +
                t + "s: " + desplazamientoAcumulado +
                " m", "Integrando V_i(t) entre t_0 y t...", JOptionPane.INFORMATION_MESSAGE);
    }
}
