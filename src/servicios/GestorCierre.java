package servicios;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GestorCierre
{

    // Estos contadores los puedes actualizar desde tu Controlador cada vez que una operación sea exitosa
    public static int altas = 0;
    public static int bajas = 0;
    public static int cambios = 0;

    public static void generarResumen()
    {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "resumen_sesion_" + timestamp + ".txt";

        try (FileWriter writer = new FileWriter(fileName))
        {
            writer.write("=== RESUMEN DE CIERRE DE SESIÓN ===\n");
            writer.write("Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "\n");
            writer.write("Altas realizadas: " + altas + "\n");
            writer.write("Bajas realizadas: " + bajas + "\n");
            writer.write("Cambios realizados: " + cambios + "\n");
            writer.write("------------------------------------\n");
            writer.write("Estado: Conexión cerrada correctamente.\n");
            System.out.println("Resumen de cierre generado: " + fileName);
        } catch (IOException e)
        {
            System.err.println("Error al generar resumen de cierre: " + e.getMessage());
        }
    }
}
