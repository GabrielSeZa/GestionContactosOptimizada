import vista.ventana;
import controlador.logica_ventana;

public class Main {
    public static void main(String[] args) {
        ventana v = new ventana();
        new logica_ventana(v);
        v.setVisible(true);
    }
}