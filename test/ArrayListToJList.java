import javax.swing.*;
import java.util.ArrayList;

public class ArrayListToJList {
    public static void main(String[] args) {
        // Crear un JFrame
        JFrame frame = new JFrame("Agregar Tercer Elemento de Object[] a JList");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Crear un ArrayList<Object[]> con datos
        ArrayList<Object[]> listaDatos = new ArrayList<>();
        listaDatos.add(new Object[]{"Juan", 25, "Ingeniero"});
        listaDatos.add(new Object[]{"Ana", 30, "Doctora"});
        listaDatos.add(new Object[]{"Carlos", 28, "Abogado"});

        // Crear un modelo dinámico para JList
        DefaultListModel<String> modelo = new DefaultListModel<>();

        // Agregar solo el tercer elemento de cada Object[] al modelo
        for (Object[] fila : listaDatos) {
            if (fila.length > 2) { // Validar que el arreglo tenga al menos 3 elementos
                modelo.addElement(fila[2].toString()); // Convertir a String antes de agregar
            }
        }

        // Crear un JList con el modelo
        JList<String> jList = new JList<>(modelo);

        // Agregar el JList a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(jList);

        // Agregar el JScrollPane al JFrame
        frame.add(scrollPane);
        frame.setVisible(true);
    }
}