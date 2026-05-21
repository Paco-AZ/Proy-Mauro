package servicios;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.nio.file.Files;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class GestorAuditoria
{

    // Llave simétrica de 16 bytes para AES (En producción, ofuscar esta clave)
    private static final byte[] LLAVE_SECRETA = "ClaveSegura12345".getBytes();
    private static final String RUTA_ARCHIVO_LOG = "auditoria_seguridad.dat";

    public static void registrarAccion(Connection conn, int idUsuario, String accion, String tabla, String detalle)
    {
        // Usamos timestamp de Java para evitar conflictos de formato y dejar que SQL lo maneje
        java.sql.Timestamp fechaHora = new java.sql.Timestamp(System.currentTimeMillis());

        // 1. Guardar en Base de Datos (Ahora con columna separada para detalle)
        guardarEnBaseDeDatos(conn, idUsuario, accion, tabla, detalle, fechaHora);

        // 2. Guardar en Archivo Cifrado Local
        String logText = String.format("[%s] User:%d | Accion:%s | Tabla:%s | Detalle:%s\n",
                fechaHora, idUsuario, accion, tabla, detalle);
        guardarEnArchivoCifrado(logText);
    }

    private static void guardarEnBaseDeDatos(Connection conn, int idUsuario, String accion, String tabla, String detalle, java.sql.Timestamp fecha)
    {
        // Ajusta tu tabla para incluir 'Detalle' si aún no lo haces
        String sql = "INSERT INTO bitacora_log (Id_Usuario, Accion, Tabla_Afectada, Detalle, Fecha_Hora) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, idUsuario);
            pstmt.setString(2, accion);
            pstmt.setString(3, tabla);        // Ahora es solo el nombre de la tabla (ej. "Persona")
            pstmt.setString(4, detalle);      // Aquí va lo largo (ej. "Modificación de ID: 12345")
            pstmt.setTimestamp(5, fecha);     // Usamos setTimestamp para mayor precisión
            pstmt.executeUpdate();
        } catch (Exception e)
        {
            System.err.println("Error al guardar auditoría en BD: " + e.getMessage());
        }
    }

    private static void guardarEnArchivoCifrado(String logText)
    {
        try
        {
            // Configurar el cifrado AES
            SecretKeySpec secretKey = new SecretKeySpec(LLAVE_SECRETA, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Cifrar el texto
            byte[] textoCifrado = cipher.doFinal(logText.getBytes("UTF-8"));

            // Escribir en modo "append" (añadir al final)
            try (FileOutputStream fos = new FileOutputStream(RUTA_ARCHIVO_LOG, true))
            {
                // Escribir un separador de tamaño o marcador si es necesario, 
                // aquí simplificamos escribiendo los bytes directamente
                fos.write(textoCifrado);
            }
        } catch (Exception e)
        {
            System.err.println("Error al guardar log cifrado local: " + e.getMessage());
        }
    }

    public static String leerLogCifrado()
    {
        File archivoLog = new File(RUTA_ARCHIVO_LOG); // "auditoria_seguridad.dat"

        if (!archivoLog.exists())
        {
            return "No hay registros de auditoría locales todavía.";
        }

        try
        {
            // Leer todos los bytes cifrados del archivo
            byte[] bytesCifrados = Files.readAllBytes(archivoLog.toPath());

            // Configurar el descifrado AES con la misma llave
            SecretKeySpec secretKey = new SecretKeySpec(LLAVE_SECRETA, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Descifrar a bytes planos y convertir a String
            byte[] bytesDescifrados = cipher.doFinal(bytesCifrados);
            return new String(bytesDescifrados, "UTF-8");

        } catch (Exception e)
        {
            e.printStackTrace();
            return "Error al intentar descifrar el log: " + e.getMessage();
        }
    }
}
