/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Consulta;
import modelo.dao.PersonaDAO;
import modelo.entidades.DatosTabla;
import modelo.seguridad.SesionActual;
import servicios.GestorCierre;
import vista.Principal;

/**
 *
 * @author HP
 */
public class Controlador
{

    private PersonaDAO personaDAO;

    public Controlador()
    {
        this.personaDAO = new PersonaDAO();
    }

    // El Controlador recibe la Vista (el JTable) y los parámetros seleccionados por el usuario
    public void aplicarFiltrosYActualizarTabla(String sexo, String vc, String c, String e,
            Boolean desnutricion, Boolean sobreP, Boolean alergias, Boolean obesidad, Boolean diabetes,
            Boolean otras, String orden, String tipo, JTable tablaDestino)
    {

        try
        {
            // 1. Pide los datos a la base de datos (Modelo/DAO)
            DatosTabla resultados = personaDAO.obtenerDatosFiltrados(
                    sexo, vc, c, e, desnutricion, sobreP, alergias, obesidad, diabetes, otras, orden, tipo
            );

            // 2. Dibuja los datos en la interfaz (Vista)
            DefaultTableModel modelo = (DefaultTableModel) tablaDestino.getModel();
            modelo.setRowCount(0); // Limpiar filas
            modelo.setColumnCount(0); // Limpiar columnas

            // Setear encabezados
            modelo.setColumnIdentifiers(resultados.getEncabezados());

            // Agregar filas
            for (Object[] fila : resultados.getFilas())
            {
                modelo.addRow(fila);
            }

        } catch (SQLException ex)
        {
            // El Controlador decide cómo mostrar el error
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al obtener los datos de la base de datos: " + ex.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Controlador: Pide al DAO el nombre completo y maneja la respuesta para la
     * Vista.
     */
    public Object[] obtenerNombreCompleto(String cve, JFrame jf)
    {
        String tipoStr = (Principal.tipo == 1) ? "A" : "P";

        try
        {
            Object[] resultado = personaDAO.obtenerNombreCompleto(cve, tipoStr);
            if (resultado == null)
            {
                // El Controlador decide mostrar el mensaje de error en la Vista (jf)
                JOptionPane.showMessageDialog(jf, "Clave o Matrícula no encontrada.", "Búsqueda", JOptionPane.WARNING_MESSAGE);
            }
            return resultado;

        } catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(jf, "Error de base de datos al buscar: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Controlador: Pide al DAO todos los datos y maneja la respuesta para la
     * Vista.
     */
    public Object[] obtenerTodosLosDatos(String cve, JFrame jf)
    {
        String tipoStr = (Principal.tipo == 1) ? "A" : "P";

        try
        {
            Object[] resultado = personaDAO.obtenerTodosLosDatos(cve, tipoStr);
            if (resultado == null)
            {
                JOptionPane.showMessageDialog(jf, "Clave o Matrícula no encontrada.", "Búsqueda", JOptionPane.WARNING_MESSAGE);
            }
            return resultado;

        } catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(jf, "Error de base de datos al extraer detalles: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Coordina la validación y actualización de la vista del Historial Clínico.
     */
    public void cargarHistorial(String cve, JLabel lblNom, JLabel lblAp, JLabel lblSegAp, JList<Consulta> listCitas)
    {
        String tipoStr = (Principal.tipo == 1) ? "A" : "P";

        try
        {
            // 1. Validar si la persona existe (Reutilizamos el método del DAO)
            Object[] persona = personaDAO.obtenerNombreCompleto(cve, tipoStr);
            if (persona == null)
            {
                JOptionPane.showMessageDialog(null, "Clave incorrecta o no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Cortamos la ejecución aquí
            }

            // 2. Buscar el historial
            List<Consulta> historial = personaDAO.obtenerHistorialClinico(cve, tipoStr);
            int i = 0;

            if (historial.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "La clave ingresada no tiene Historial Clínico registrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Si llegamos aquí, sí hay historial. Actualizamos la Vista.
            // Los datos de la persona están en los índices 1, 2 y 3 del primer registro del historial
            Consulta primerRegistro = historial.get(0);
            lblNom.setText(primerRegistro.getNombrePaciente());
            lblNom.setVisible(true);

            lblAp.setText(primerRegistro.getApellidoP());
            lblAp.setVisible(true);

            lblSegAp.setText(primerRegistro.getApellidoM());
            lblSegAp.setVisible(true);
            // 4. Llenamos la lista de citas (La fecha estaba en el índice 10 de tu consulta original)
            DefaultListModel<Consulta> listModel = new DefaultListModel<>();
            for (Consulta fila : historial)
            {
                // Asumiendo que fila[10] es la Fecha u otra información que mostrabas
                listModel.addElement(fila);
            }
            listCitas.setModel(listModel);

        } catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al buscar historial: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Traduce los datos de la Vista y coordina con el DAO.
     */
    public void aplicarFiltrosYActualizarTabla(
            int indexSexo, int indexEstatus, int indexFrecuencia, int indexViveCon, int indexCarreras,
            boolean desnutricion, boolean sobreP, boolean alergias, boolean obesidad, boolean diabetes, boolean otras,
            int tipoPersona, JTable tablaDestino)
    {

        String sexo = null, estatus = null, frecuencia = null, viveCon = null, carrera = null;
        String tipoStr = (tipoPersona == 1) ? "A" : "P";

        // --- TRADUCCIÓN DE ÍNDICES A STRINGS PARA LA BD ---
        switch (indexSexo)
        {
            case 1:
                sexo = "M";
                break;
            case 2:
                sexo = "F";
                break;
        }

        switch (indexEstatus)
        {
            case 1:
                estatus = "Base";
                break;
            case 2:
                estatus = "Temporal";
                break;
        }

        switch (indexFrecuencia)
        {
            case 1:
                frecuencia = "ASC";
                break;
            case 2:
                frecuencia = "DESC";
                break;
        }

        switch (indexViveCon)
        {
            case 1:
                viveCon = "Papá";
                break;
            case 2:
                viveCon = "Mamá";
                break;
            case 3:
                viveCon = "Ambos";
                break;
        }

        switch (indexCarreras)
        {
            case 1:
                carrera = "LISW";
                break;
            case 2:
                carrera = "LIP";
                break;
            case 3:
                carrera = "LSC";
                break;
            case 4:
                carrera = "LIPI";
                break;
            case 5:
                carrera = "LIM";
                break;
            case 6:
                carrera = "LIC";
                break;
        }

        // --- COMUNICACIÓN CON LA BASE DE DATOS ---
        try
        {
            // El DAO devuelve un objeto limpio (sin dependencias gráficas)
            DatosTabla resultados = personaDAO.obtenerDatosFiltrados(
                    sexo, viveCon, carrera, estatus,
                    desnutricion, sobreP, alergias, obesidad, diabetes, otras,
                    frecuencia, tipoStr
            );

            // --- ACTUALIZACIÓN DE LA INTERFAZ GRÁFICA ---
            DefaultTableModel modelo = (DefaultTableModel) tablaDestino.getModel();
            modelo.setRowCount(0); // Limpiar filas antiguas
            modelo.setColumnCount(0); // Limpiar columnas antiguas

            // 1. Poner los nombres de las columnas
            modelo.setColumnIdentifiers(resultados.getEncabezados());

            // 2. Insertar los datos
            for (Object[] fila : resultados.getFilas())
            {
                modelo.addRow(fila);
            }

        } catch (SQLException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al aplicar los filtros en la base de datos: " + ex.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarTodosLosDatos(int tipoPersona, JTable tablaDestino)
    {
        String tipoStr = (tipoPersona == 1) ? "A" : "P";

        try
        {
            // 1. Pide la matriz completa de datos al DAO
            DatosTabla resultados = personaDAO.obtenerTodos(tipoStr);

            // 2. Dibuja la tabla en la Vista
            DefaultTableModel modelo = (DefaultTableModel) tablaDestino.getModel();
            modelo.setRowCount(0);
            modelo.setColumnCount(0);

            modelo.setColumnIdentifiers(resultados.getEncabezados());

            for (Object[] fila : resultados.getFilas())
            {
                modelo.addRow(fila);
            }

        } catch (SQLException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al cargar todos los registros: " + ex.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Valida permisos, confirma auditoría y ejecuta la eliminación.
     *
     * @return true si se eliminó con éxito, false si se canceló o falló.
     */
    public boolean eliminarRegistro(int cve, int tipoPersona, JFrame vistaParent)
    {

        // 1. REGLA DE NEGOCIO: Seguridad (Control de Roles)
        String rolUsuario = SesionActual.getInstance().getRol(); // Ajusta a la variable donde guardes el rol
        if (!rolUsuario.equals("Administrador") && !rolUsuario.equals("Medico"))
        {
            JOptionPane.showMessageDialog(vistaParent,
                    "Acceso Denegado: Su rol actual ('" + rolUsuario + "') no tiene privilegios para eliminar registros.",
                    "Bloqueo de Seguridad", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 2. REGLA DE NEGOCIO: Trazabilidad y Confirmación de Auditoría
        String nombreUsuario = SesionActual.getInstance().getNombreUsuario();
        int confirmacion = JOptionPane.showConfirmDialog(vistaParent,
                "¿Está completamente seguro de eliminar el registro con Clave/ID " + cve + "?\n\n"
                + "ATENCIÓN: Esta acción es irreversible y quedará registrada en la bitácora \n"
                + "bajo la responsabilidad del usuario: " + nombreUsuario,
                "Confirmación de Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION)
        {
            return false; // El usuario se arrepintió
        }

        // 3. CAPA DE DATOS (Llamada al DAO que ya habíamos creado)
        try
        {
            int idUsuario = SesionActual.getInstance().getIdUsuario();
            // El método eliminarPersona del PersonaDAO ya tiene la transacción y el insert en la bitácora
            boolean exito = personaDAO.eliminarPersona(cve, idUsuario, tipoPersona);

            if (exito)
            {
                JOptionPane.showMessageDialog(vistaParent, "Expediente eliminado correctamente del sistema.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                GestorCierre.altas++;
                return true;
            } else
            {
                JOptionPane.showMessageDialog(vistaParent, "No se encontró el registro o no se pudo eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vistaParent, "Fallo crítico en la base de datos al eliminar: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Orquesta el guardado de un nuevo registro.
     *
     * @return true si se guardó, false si falló o ya existe.
     */
    public boolean guardarRegistroNuevo(Object[] datosPersona, int tipoPersona, JFrame vistaParent)
    {
        try
        {
            boolean insertado = personaDAO.insertarRegistro(datosPersona, tipoPersona);

            if (insertado)
            {
                JOptionPane.showMessageDialog(vistaParent, "Registro guardado exitosamente en el sistema.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                GestorCierre.altas++;
                return true;
            } else
            {
                JOptionPane.showMessageDialog(vistaParent, "La Clave o Matrícula ingresada ya existe en la base de datos.", "Duplicado", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vistaParent, "Fallo al guardar en la base de datos: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean agregarConsultaMedica(int idPaciente, String padecimiento, String antecedentes, String medicamento, String plan, JFrame vistaParent)
    {
        try
        {
            boolean guardado = personaDAO.insertarConsultaMedica(idPaciente, padecimiento, antecedentes, medicamento, plan);

            if (guardado)
            {
                JOptionPane.showMessageDialog(vistaParent, "Consulta médica guardada y añadida al historial exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                GestorCierre.altas++;
                return true;
            } else
            {
                JOptionPane.showMessageDialog(vistaParent, "No se pudo guardar la consulta.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vistaParent, "Fallo al conectar con la base de datos: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Orquesta la modificación de un registro.
     *
     * @return true si se actualizó, false si falló.
     */
    public boolean modificarRegistro(Object[] datosPersona, int tipoPersona, JFrame vistaParent)
    {
        try
        {
            boolean actualizado = personaDAO.modificarRegistro(datosPersona, tipoPersona);

            if (actualizado)
            {
                JOptionPane.showMessageDialog(vistaParent, "Expediente actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                GestorCierre.altas++;
                return true;
            } else
            {
                JOptionPane.showMessageDialog(vistaParent, "No se pudo actualizar. El registro no existe.", "Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vistaParent, "Error crítico al actualizar la base de datos: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean validarMayuscula(String texto)
    {
        return texto != null && !texto.isEmpty() && Character.isUpperCase(texto.charAt(0));
    }

    public static String capitalizarPrimerasLetras(String input)
    {
        // Dividir la cadena en palabras usando el espacio como delimitador
        String[] palabras = input.split(" ");
        StringBuilder resultado = new StringBuilder();

        // Iterar sobre cada palabra y capitalizar la primera letra
        for (String palabra : palabras)
        {
            if (!palabra.isEmpty())
            {
                char primeraLetra = Character.toUpperCase(palabra.charAt(0));
                String restoPalabra = palabra.substring(1);
                resultado.append(primeraLetra).append(restoPalabra).append(" ");
            }
        }

        // Eliminar el espacio adicional al final y devolver el resultado
        return resultado.toString().trim();
    }

    public String obtenerPredominante(List<Object[]> listaPersonas, int tipoPersona)
    {
        if (listaPersonas == null || listaPersonas.isEmpty())
        {
            return "N/A";
        }

        Map<String, Integer> conteo = new HashMap<>();
        String primero = null;

        for (Object[] p : listaPersonas)
        {
            // En tu consulta original, el índice 12 o 13 suele tener el dato (vivecon, carrera, estatus)
            // Ajusta el índice según la posición real en tu arreglo Object[]
            String valor = (tipoPersona == 1) ? p[13].toString() : p[12].toString();

            if (primero == null)
            {
                primero = valor; // Guardamos el primero para el caso de empate
            }
            conteo.put(valor, conteo.getOrDefault(valor, 0) + 1);
        }

        String ganador = primero;
        int max = 0;

        // Buscamos el que tiene mayor conteo
        for (Map.Entry<String, Integer> entry : conteo.entrySet())
        {
            if (entry.getValue() > max)
            {
                max = entry.getValue();
                ganador = entry.getKey();
            }
        }

        return ganador; // Si hay empate, por la lógica del bucle, se mantiene el primero encontrado
    }

    /**
     * Devuelve la lista de objetos actual basándose en los filtros aplicados.
     */
    public List<Object[]> obtenerListaFiltrada(int indexSexo, int indexEstatus, int indexFrecuencia,
            int indexViveCon, int indexCarreras, boolean desnutricion,
            boolean sobreP, boolean alergias, boolean obesidad,
            boolean diabetes, boolean otras, int tipoPersona) throws SQLException
    {

        String sexo = null, estatus = null, frecuencia = null, viveCon = null, carrera = null;
        String tipoStr = (tipoPersona == 1) ? "A" : "P";

        // --- TRADUCCIÓN DE ÍNDICES A STRINGS PARA LA BD ---
        switch (indexSexo)
        {
            case 1:
                sexo = "M";
                break;
            case 2:
                sexo = "F";
                break;
        }

        switch (indexEstatus)
        {
            case 1:
                estatus = "Base";
                break;
            case 2:
                estatus = "Temporal";
                break;
        }

        switch (indexFrecuencia)
        {
            case 1:
                frecuencia = "ASC";
                break;
            case 2:
                frecuencia = "DESC";
                break;
        }

        switch (indexViveCon)
        {
            case 1:
                viveCon = "Papá";
                break;
            case 2:
                viveCon = "Mamá";
                break;
            case 3:
                viveCon = "Ambos";
                break;
        }

        switch (indexCarreras)
        {
            case 1:
                carrera = "LISW";
                break;
            case 2:
                carrera = "LIP";
                break;
            case 3:
                carrera = "LSC";
                break;
            case 4:
                carrera = "LIPI";
                break;
            case 5:
                carrera = "LIM";
                break;
            case 6:
                carrera = "LIC";
                break;
        }

        return personaDAO.obtenerListaDatosFiltrados(sexo, viveCon, carrera, estatus,
                desnutricion, sobreP, alergias, obesidad,
                diabetes, otras, frecuencia, tipoStr);
    }
}
