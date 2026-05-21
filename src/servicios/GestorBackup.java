package servicios;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class GestorBackup
{

    public static void generarRespaldoUSB()
    {
        String rutaDestino = detectarUSB();
        System.out.println(rutaDestino);
        if (rutaDestino == null)
        {
            JOptionPane.showMessageDialog(null, "No se detectó ninguna unidad USB externa conectada.", "Error de Backup", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Generar un nombre de archivo con la fecha actual
        String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String archivoBackup = rutaDestino + File.separator + "Backup_Clinico_" + fecha + ".sql";

        // Configuración de MySQL (Ajusta el usuario y contraseña de tu BD)
        String dbUser = "root";
        String dbPass = "";
        String dbName = "gestor_medico";

        // Comando para ejecutar mysqldump
        // NOTA: mysqldump debe estar en las variables de entorno (PATH) del sistema operativo
        // 1. Preparamos el comando SIN el parámetro -r
        ProcessBuilder pb = new ProcessBuilder(
                "C:\\xampp\\mysql\\bin\\mysqldump.exe",
                "-u", dbUser,
                "-p" + dbPass,
                dbName
        );

// 2. LA SOLUCIÓN: Hacemos que Java intercepte la salida y cree el archivo
        java.io.File archivoDestino = new java.io.File(archivoBackup);
        pb.redirectOutput(archivoDestino);

// 3. Redirigimos los errores a la consola de NetBeans por si la contraseña está mal
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try
        {
            // Iniciamos el proceso
            Process proceso = pb.start();

            // Esperamos a que termine
            int exitCode = proceso.waitFor();

            if (exitCode == 0)
            {
                JOptionPane.showMessageDialog(null, "Copia de seguridad guardada exitosamente en:\n" + archivoBackup, "Backup Exitoso", JOptionPane.INFORMATION_MESSAGE);
            } else
            {
                // Si falla, el archivo creado podría estar vacío, lo borramos por limpieza
                if (archivoDestino.exists())
                {
                    archivoDestino.delete();
                }
                JOptionPane.showMessageDialog(null, "Error al generar el volcado. Código: " + exitCode + "\nRevisa la consola de NetBeans para más detalles.", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(exitCode);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Excepción al ejecutar el backup: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para detectar la ruta de un USB según el Sistema Operativo
    private static String detectarUSB()
    {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win"))
        {
            // Lógica para Windows (Busca unidades removibles D:, E:, etc.)
            File[] roots = File.listRoots();
            for (File root : roots)
            {
                // Simplificación: omitimos C:\ asumiendo que es el disco principal
                if (!root.getAbsolutePath().startsWith("C"))
                {
                    return root.getAbsolutePath(); // Retorna la primera unidad secundaria encontrada
                }
            }
        } else if (os.contains("nix") || os.contains("nux"))
        {
            // Lógica para distribuciones Linux (Fedora, Mint, etc.)
            String userName = System.getProperty("user.name");
            File mediaDir = new File("/run/media/" + userName); // Ruta típica en Fedora/GNOME

            if (!mediaDir.exists())
            {
                mediaDir = new File("/media/" + userName); // Ruta en Mint/Ubuntu
            }

            if (mediaDir.exists() && mediaDir.isDirectory())
            {
                File[] usbs = mediaDir.listFiles();
                if (usbs != null && usbs.length > 0)
                {
                    return usbs[0].getAbsolutePath(); // Retorna el primer USB montado
                }
            }
        }
        return null; // No se encontró nada
    }
}
