package BD;

/**
 *
 * @author Francisco
 */
import cjb.ci.Mensajes;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.ucanaccess.converters.Metadata;
import poo.Principal;

public class AccessDBConnection
{

    public static void insertarR(Object[] persona, JFrame jf)
    {
        String url = "jdbc:ucanaccess://BD\\Proyecto2P.accdb";

        try (Connection conn = DriverManager.getConnection(url))
        {
            if (!existeEnTabla(conn, "Persona", (Integer) persona[0]))
            {
                insertarDatosPersona(conn, persona);
                System.out.println("Inserción completada.");
            } else
            {
                Mensajes.error(jf, "El registro ya existe en la tabla Persona. No se ha insertado nuevamente.");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static void insertarDatosPersona(Connection conn, Object[] persona) throws SQLException
    {
                String insertPersonaSql = "INSERT INTO Persona (Id, Nombre, PrimerApellido, SegundoApellido, Sexo, Desnutricion, Sobrepeso, Alergias, Obesidad, Diabetes, OtrasEnfermedades, Tipo_persona) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertPersonaSql))
        {
            pstmt.setInt(1, (Integer) persona[0]);
            pstmt.setString(2, (String) persona[1]);
            pstmt.setString(3, (String) persona[2]);
            pstmt.setString(4, (String) persona[3]);
            pstmt.setString(5, (String) persona[4]);
            pstmt.setBoolean(6, (Boolean) persona[5]);
            pstmt.setBoolean(7, (Boolean) persona[6]);
            pstmt.setBoolean(8, (Boolean) persona[7]);
            pstmt.setBoolean(9, (Boolean) persona[8]);
            pstmt.setBoolean(10, (Boolean) persona[9]);
            pstmt.setString(11, (String) persona[10]);
            pstmt.setString(12, (String) persona[11]);
            pstmt.executeUpdate();
        }

        // Insertar en Alumnos o Personal según el tipo de persona
        if (persona[11].equals("A"))
        {
            String insertAlumnosSql = "INSERT INTO Alumnos (Id, vivecon, carrera) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertAlumnosSql))
            {
                pstmt.setInt(1, (Integer) persona[0]);
                pstmt.setString(2, (String) persona[12]);
                pstmt.setString(3, (String) persona[13]);
                pstmt.executeUpdate();
            }
        } else if (persona[11].equals("P"))
        {
            String insertPersonalSql = "INSERT INTO Personal (Id, Estatus) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertPersonalSql))
            {
                pstmt.setInt(1, (Integer) persona[0]);
                pstmt.setString(2, (String) persona[12]);
                pstmt.executeUpdate();
            }
        }
    }

    public static void insertarDatosHistorial(Connection conn, Object[] persona) throws SQLException
    {
        String insertPersonaSql = "INSERT INTO Historial_clinico (Id, padecimiento_actual, antecedentes, medicamento, plandetratamiento, fecha ) VALUES (?, ?, ?, ?, ?, ?)";
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // Conviértela a java.sql.Date
        try (PreparedStatement pstmt = conn.prepareStatement(insertPersonaSql))
        {
            pstmt.setInt(1, (Integer) persona[0]);
            pstmt.setString(2, (String) persona[1]);
            pstmt.setString(3, (String) persona[2]);
            pstmt.setString(4, (String) persona[3]);
            pstmt.setString(5, (String) persona[4]);
            pstmt.setDate(6, sqlDate);
            pstmt.executeUpdate();
        }
    }

    private static boolean existeEnTabla(Connection conn, String tabla, int id) throws SQLException
    {
        String checkQuery = "SELECT 1 FROM " + tabla + " WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkQuery))
        {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery())
            {
                return rs.next(); // Devuelve true si el registro ya existe
            }
        }
    }

    public static void mostrarTabla(Connection conn, String tabla) throws SQLException
    {
        String selectSql = "SELECT * FROM " + tabla;
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectSql))
        {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Mostrar nombres de columnas
            for (int i = 1; i <= columnCount; i++)
            {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();

            // Mostrar datos de cada fila
            while (rs.next())
            {
                for (int i = 1; i <= columnCount; i++)
                {
                    System.out.print(rs.getObject(i) + "\t");
                }
                System.out.println();
            }
        }
    }

    public static void mostrarConsulta(Connection conn, String tabla, String tabla2, JTable tablaIns) throws SQLException
    {
        String selectSql = "SELECT Persona.*, COUNT([Historial_Clinico].Id) AS Citas, "
                + (tabla2.equals("Alumnos") ? "Alumnos.vivecon, Alumnos.carrera" : "Personal.estatus")
                + " FROM Persona "
                + " JOIN " + tabla2 + " ON Persona.Id = " + tabla2 + ".Id"
                + " LEFT JOIN [Historial_Clinico] ON Persona.Id = [Historial_Clinico].Id "
                + " GROUP BY Persona.Id, Persona.Nombre, Persona.PrimerApellido, Persona.SegundoApellido, "
                + " Persona.Sexo, Persona.Desnutricion, Persona.Sobrepeso, Persona.Alergias, Persona.Obesidad, "
                + "Persona.Diabetes, Persona.OtrasEnfermedades, Persona.Tipo_persona, "
                + (tabla2.equals("Alumnos") ? "Alumnos.vivecon, Alumnos.carrera" : "Personal.estatus");

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectSql))
        {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Configurar encabezados en JTable según las columnas del resultado
            DefaultTableModel modelo = (DefaultTableModel) tablaIns.getModel();
            modelo.setRowCount(0);  // Limpiar filas actuales
            modelo.setColumnCount(0);  // Limpiar encabezados actuales
            for (int i = 1; i <= columnCount; i++)
            {
                if (!metaData.getColumnName(i).equals("Tipo_persona"))
                {
                    modelo.addColumn(metaData.getColumnName(i));  // Añadir nombre de cada columna como encabezado
                }
            }

            // Mostrar datos de cada fila y llenar la JTable
            while (rs.next())
            {
                Object[] obj = new Object[columnCount];
                for (int i = 1, j = 0; i <= columnCount; i++)
                {
                    if (!metaData.getColumnName(i).equals("Tipo_persona"))
                    {
                        obj[j++] = rs.getObject(i);
                    }
                }
                modelo.addRow(obj);  // Añadir fila completa al modelo de JTable
            }
        }
    }

    public static void mostrarPersona(Connection conn, String tabla, String tabla2, String cve, JTable tablaIns) throws SQLException
    {
        String selectSql = "SELECT Persona.*, COUNT([Historial_Clinico].Id) AS Citas, "
                + (tabla2.equals("Alumnos") ? "Alumnos.vivecon, Alumnos.carrera" : "Personal.estatus")
                + " FROM Persona "
                + " JOIN " + tabla2 + " ON Persona.Id = " + tabla2 + ".Id"
                + " LEFT JOIN [Historial_Clinico] ON Persona.Id = [Historial_Clinico].Id WHERE Persona.Id = " + cve
                + // Asumiendo que la clave foránea es persona_id
                " GROUP BY Persona.Id, Persona.nombre, Persona.PrimerApellido, "
                + (tabla2.equals("Alumnos") ? "Alumnos.vivecon, Alumnos.carrera" : "Personal.estatus");

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectSql))
        {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Configurar encabezados en JTable según las columnas del resultado
            DefaultTableModel modelo = (DefaultTableModel) tablaIns.getModel();
            modelo.setRowCount(0);  // Limpiar filas actuales
            modelo.setColumnCount(0);  // Limpiar encabezados actuales
            for (int i = 1; i <= columnCount; i++)
            {
                if (!metaData.getColumnName(i).equals("Tipo_persona"))
                {
                    modelo.addColumn(metaData.getColumnName(i));  // Añadir nombre de cada columna como encabezado
                }
            }

            // Mostrar datos de cada fila y llenar la JTable
            Object[] obj = new Object[columnCount];
            while (rs.next())
            {
                for (int i = 1, j = 0; i <= columnCount; i++)
                {
                    if (!rs.getObject(i).equals((tabla2.equals("Alumnos") ? "A" : "P")))
                    {
                        obj[j++] = rs.getObject(i);
                    }
                }
                modelo.addRow(obj);  // Añadir fila completa al modelo de JTable
            }
        }
    }

    public static void ObtenerHC(Connection conn, ArrayList<String[]> Obj, String cve, String tipo) throws SQLException
    {
        String selectSql = "SELECT Persona.Id, Persona.Nombre, Persona.PrimerApellido, Persona.SegundoApellido,"
                + "[Historial_Clinico].* FROM Persona INNER JOIN [Historial_Clinico] ON "
                + "Persona.Id = [Historial_Clinico].Id " + "WHERE Persona.Id = ? AND Persona.Tipo_persona = ?";
        try (PreparedStatement stmt = conn.prepareStatement(selectSql))
        {
            // Establecer parámetros para los filtros
            stmt.setString(1, cve);
            stmt.setString(2, tipo);
            try (ResultSet rs = stmt.executeQuery())
            {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next())
                {
                    String[] obj = new String[columnCount];
                    for (int i = 1, j = 0; i <= columnCount; i++)
                    {
                        obj[j++] = rs.getObject(i).toString();
                    }
                    Obj.add(obj);  // Añadir fila completa al modelo de JTable
                }
            }
        }
    }

    public static Object[] obtenerCVE(Connection conn, String cve, JFrame jf, String tipo)
    {
        String sql = "SELECT Persona.Id, Persona.Nombre, Persona.PrimerApellido, Persona.SegundoApellido FROM Persona WHERE Persona.Id = ? AND Persona.Tipo_persona = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, cve);
            stmt.setString(2, tipo);
            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())
                {
                    Object[] obj = new Object[4];
                    for (int i = 1; i <= 4; i++)
                    {
                        obj[i - 1] = rs.getObject(i);
                    }
                    return obj;
                } else
                {
                    Mensajes.error(jf, "Clave no encontrada");
                    return null;
                }
            }
        } catch (SQLException e)
        {
            Mensajes.error(jf, "Error Matricula no encontrada");
            return null;
        }
    }

    public static Object[] obtenerTD(Connection conn, String cve, JFrame jf, String tipo)
    {
        String sql = "SELECT Persona.*, " + (tipo.equals("A") ? "Alumnos.vivecon, Alumnos.carrera"
                : "Personal.estatus") + " FROM Persona JOIN " + (tipo.equals("A") ? "Alumnos" : "Personal")
                + " ON Persona.Id =" + (tipo.equals("A") ? "Alumnos.Id" : "Personal.Id") + " WHERE Persona.Id = ? AND Persona.Tipo_persona = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, cve);
            stmt.setString(2, tipo);
            try (ResultSet rs = stmt.executeQuery())
            {
                ResultSetMetaData metaData = rs.getMetaData();
                int col = metaData.getColumnCount();
                if (rs.next())
                {
                    Object[] obj = new Object[col];
                    for (int i = 1, j = 0; i <= col; i++)
                    {
                        if (rs.getObject(i) != null)
                        {
                            if (!rs.getObject(i).equals(tipo))
                            {
                                obj[j++] = rs.getObject(i);
                            }
                        }
                    }
                    return obj;
                } else
                {
                    Mensajes.error(jf, "Clave no encontrada");
                    return null;
                }
            }
        } catch (SQLException e)
        {
            Mensajes.error(jf, "Error Matricula no encontrada");
            return null;
        }
    }

    public static void modificarD(String cve, JFrame jf, String tipo, Object[] p) throws SQLException
    {
        String sql = "UPDATE Persona SET Nombre = ? , PrimerApellido = ?, SegundoApellido = ?"
                + ", Desnutricion = ?, Sobrepeso = ?, Alergias = ?, Obesidad = ?, Diabetes = ?"
                + ", OtrasEnfermedades = ? WHERE Id = ?";
        String sqlE = ("UPDATE " + (tipo.equals("A") ? "Alumnos SET carrera = ?, vivecon = ?"
                : "Personal SET estatus = ?") + "WHERE Id = ?");
        String url = "jdbc:ucanaccess://BD\\Proyecto2P.accdb";
        try (Connection conn = DriverManager.getConnection(url))
        {
            conn.setAutoCommit(false); // Iniciar la transacción 
            try (PreparedStatement pstmtPersona = conn.prepareStatement(sql); PreparedStatement pstmtE = conn.prepareStatement(sqlE))
            {
                pstmtPersona.setString(1, (String) p[1]);
                pstmtPersona.setString(2, (String) p[2]);
                pstmtPersona.setString(3, (String) p[3]);
                pstmtPersona.setBoolean(4, (boolean) p[4]);
                pstmtPersona.setBoolean(5, (boolean) p[5]);
                pstmtPersona.setBoolean(6, (boolean) p[6]);
                pstmtPersona.setBoolean(7, (boolean) p[7]);
                pstmtPersona.setBoolean(8, (boolean) p[8]);
                pstmtPersona.setString(9, (String) p[9]);
                pstmtPersona.setInt(10, (int) p[0]); // El Id de la persona 
                pstmtPersona.executeUpdate();
                // Actualiza la tabla Alumnos 
                if (tipo.equals("A"))
                {
                    pstmtE.setString(1, (String) p[11]);
                    pstmtE.setString(2, (String) p[10]);
                    pstmtE.setInt(3, (int) p[0]); // El Id de la persona (clave foránea) 
                } else
                {
                    pstmtE.setString(1, (String) p[10]);
                    pstmtE.setInt(2, (int) p[0]); // El Id de la persona (clave foránea) 
                }
                pstmtE.executeUpdate();
                // Si todo va bien, confirmar la transacción 
                conn.commit();
            } catch (SQLException e)
            { // Si hay algún error, deshacer la transacción 
                conn.rollback();
                e.printStackTrace();
                Mensajes.error(jf, "Error al actualizar los datos: " + e.getMessage());
            }
        }
    }

    public static void obtenerDatosFiltrados(Connection conn, String sexo, String vc, String c, String e,
            Boolean desnutricion, Boolean sobreP, Boolean alergias, Boolean obesidad, Boolean diabetes,
            Boolean otras, String orden, String tipo, JTable tabla)
    {

        String sqlBase = "SELECT Persona.Id, Persona.Nombre, Persona.PrimerApellido, Persona.SegundoApellido, Persona.Sexo, "
                + "Persona.Desnutricion, Persona.Sobrepeso, Persona.Alergias, Persona.Obesidad, Persona.Diabetes, Persona.OtrasEnfermedades, "
                + "COUNT(Historial_Clinico.Id) AS Citas, "
                + (tipo.equals("A") ? "Alumnos.vivecon, Alumnos.carrera" : "Personal.estatus")
                + " FROM Persona "
                + (tipo.equals("A") ? "INNER JOIN Alumnos ON Persona.Id = Alumnos.Id " : "INNER JOIN Personal ON Persona.Id = Personal.Id ")
                + "LEFT JOIN Historial_Clinico ON Persona.Id = Historial_Clinico.Id ";

        String sqlCondition = "WHERE (Persona.Sexo = ? OR ? IS NULL) ";
        String sqlGroupBy = " GROUP BY Persona.Id, Persona.Nombre, Persona.PrimerApellido, Persona.SegundoApellido, "
                + " Persona.Sexo, Persona.Desnutricion, Persona.Sobrepeso, Persona.Alergias, Persona.Obesidad, "
                + "Persona.Diabetes, Persona.OtrasEnfermedades, "
                + (tipo.equals("A") ? "Alumnos.vivecon, Alumnos.carrera" : "Personal.estatus");

        // Definición de la consulta SQL dependiendo del tipo
        if ("A".equals(tipo))
        {
            sqlCondition += " AND (Alumnos.vivecon = ? OR ? IS NULL) "
                    + " AND (Alumnos.carrera = ? OR ? IS NULL) ";
        } else
        {
            sqlCondition += " AND (Personal.estatus = ? OR ? IS NULL) ";
        }

        // Filtros adicionales para las enfermedades, solo si son true
        if (desnutricion)
        {
            sqlCondition += " AND (Persona.Desnutricion = ? OR ? IS NULL) ";
        }
        if (sobreP)
        {
            sqlCondition += " AND (Persona.Sobrepeso = ? OR ? IS NULL) ";
        }
        if (alergias)
        {
            sqlCondition += " AND (Persona.Alergias = ? OR ? IS NULL) ";
        }
        if (obesidad)
        {
            sqlCondition += " AND (Persona.Obesidad = ? OR ? IS NULL) ";
        }
        if (diabetes)
        {
            sqlCondition += " AND (Persona.Diabetes = ? OR ? IS NULL) ";
        }
        if (otras)
        {
            sqlCondition += " AND (Persona.OtrasEnfermedades IS NOT NULL AND Persona.OtrasEnfermedades <> '') ";
        }

        // Ordenar resultados
        String sqlOrder = "";
        if (orden != null)
        {
            sqlOrder = orden.equals("ASC") ? " ORDER BY Citas ASC" : " ORDER BY Citas DESC";
        }

        // Construir la consulta final
        String sql = sqlBase + sqlCondition + sqlGroupBy + sqlOrder;

        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            int index = 1;

            // Establecer parámetros para los filtros
            stmt.setString(index++, sexo);
            stmt.setString(index++, sexo);
            if ("A".equals(tipo))
            {
                stmt.setString(index++, vc);
                stmt.setString(index++, vc);
                stmt.setString(index++, c);
                stmt.setString(index++, c);
            } else
            {
                stmt.setString(index++, e);
                stmt.setString(index++, e);
            }
            if (desnutricion)
            {
                stmt.setBoolean(index++, desnutricion);
                stmt.setBoolean(index++, desnutricion);
            }
            if (sobreP)
            {
                stmt.setBoolean(index++, sobreP);
                stmt.setBoolean(index++, sobreP);
            }
            if (alergias)
            {
                stmt.setBoolean(index++, alergias);
                stmt.setBoolean(index++, alergias);
            }
            if (obesidad)
            {
                stmt.setBoolean(index++, obesidad);
                stmt.setBoolean(index++, obesidad);
            }
            if (diabetes)
            {
                stmt.setBoolean(index++, diabetes);
                stmt.setBoolean(index++, diabetes);
            }

            // Limpiar las filas y columnas de la tabla antes de agregar nuevos datos
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0);  // Limpiar filas actuales
            modelo.setColumnCount(0);  // Limpiar encabezados actuales

            try (ResultSet rs = stmt.executeQuery())
            {
                ResultSetMetaData metaData = rs.getMetaData();
                int col = metaData.getColumnCount();
                Object[] columnNames = new Object[col];

                // Ajustar los encabezados eliminando la columna "Tipo_persona"
                for (int i = 1; i <= col; i++)
                {
                    if (!metaData.getColumnName(i).equals("Tipo_persona"))
                    {
                        columnNames[i - 1] = metaData.getColumnName(i);
                    }
                }
                modelo.setColumnIdentifiers(columnNames); // Configura los encabezados

                // Añadir filas a la tabla
                while (rs.next())
                {
                    Object[] result = new Object[col];
                    int resultIndex = 0;

                    for (int i = 1; i <= col; i++)
                    {
                        // Excluir la columna "Tipo_persona" de los resultados
                        if (!metaData.getColumnName(i).equals("Tipo_persona"))
                        {
                            result[resultIndex++] = rs.getObject(i);
                        }
                    }
                    modelo.addRow(result);  // Añadir fila completa al modelo de JTable
                }
            }
        } catch (SQLException ema)
        {
            ema.printStackTrace();
        }
    }

    public static void eliminarR(JFrame jf, Connection conn, int cve) throws SQLException
    {

        String query = (Principal.tipo == 1 ? "DELETE FROM Alumnos WHERE Alumnos.Id = ?" : "DELETE FROM Personal WHERE Personal.Id = ?");
        try (PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, cve);
            int rowsAffected = stmt.executeUpdate();

        }
        query = "DELETE FROM Historial_clinico WHERE Historial_clinico.Id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, cve);
            int rowsAffected = stmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
            Mensajes.error(jf, "Error al intentar borrar el registro: " + e.getMessage());
        }

        query = "DELETE FROM Persona WHERE Persona.Id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, cve);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0)
            {
                Mensajes.exito(jf, "Eliminación exitosa.");
            } else
            {
                Mensajes.error(jf, "El registro no se pudo borrar.");
            }
        }

    }

}
