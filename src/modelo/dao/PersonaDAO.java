package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import servicios.GestorAuditoria;
import modelo.conexion.DBConnection;
import modelo.entidades.DatosTabla;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Consulta;
import modelo.seguridad.SesionActual;

public class PersonaDAO
{

    // Mudanza del método de Eliminación
    // Nota que ya no recibe un JFrame. Solo devuelve true/false o lanza excepción.
    public boolean eliminarPersona(int idPersona, int idUsuarioActual, int tipoPersona) throws SQLException
    {
        Connection conn = null;
        boolean exito = false;

        try
        {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Inicia Transacción ACID

            // 1. Borrar de la tabla hija (Alumno o Personal)
            String queryHija = (tipoPersona == 1) ? "DELETE FROM Alumno WHERE Id = ?" : "DELETE FROM Personal WHERE Id = ?";
            try (PreparedStatement stmt1 = conn.prepareStatement(queryHija))
            {
                stmt1.setInt(1, idPersona);
                stmt1.executeUpdate();
            }

            // 2. Borrar del Historial Clínico
            String queryHC = "DELETE FROM Historial_clinico WHERE Id = ?";
            try (PreparedStatement stmt2 = conn.prepareStatement(queryHC))
            {
                stmt2.setInt(1, idPersona);
                stmt2.executeUpdate();
            }

            // 3. Borrar de la tabla padre Persona
            String queryPadre = "DELETE FROM Persona WHERE Id = ?";
            try (PreparedStatement stmt3 = conn.prepareStatement(queryPadre))
            {
                stmt3.setInt(1, idPersona);
                int rowsAffected = stmt3.executeUpdate();

                if (rowsAffected > 0)
                {
                    // Si todo fue bien, registrar en Auditoría y hacer Commit
                    GestorAuditoria.registrarAccion(conn, idUsuarioActual, "DELETE", "Persona y dependencias", "ID:" + idPersona);
                    conn.commit();
                    exito = true;
                } else
                {
                    conn.rollback();
                }
            }

        } catch (SQLException e)
        {
            if (conn != null)
            {
                conn.rollback(); // Si algo falla, se deshacen TODOS los deletes.
            }
            throw e; // Relanza la excepción para que el Controlador la maneje
        } finally
        {
            DBConnection.close(conn);
        }

        return exito;
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

        // Insertar en Alumno o Personal según el tipo de persona
        if (persona[11].equals("A"))
        {
            String insertAlumnoSql = "INSERT INTO Alumno (Id, vivecon, carrera) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertAlumnoSql))
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

    public DatosTabla obtenerDatosFiltrados(String sexo, String vc, String c, String e,
            Boolean desnutricion, Boolean sobreP, Boolean alergias, Boolean obesidad, Boolean diabetes,
            Boolean otras, String orden, String tipo) throws SQLException
    {

        String sqlBase = "SELECT Persona.Id, Persona.Nombre, Persona.PrimerApellido, Persona.SegundoApellido, Persona.Sexo, "
                + "Persona.Desnutricion, Persona.Sobrepeso, Persona.Alergias, Persona.Obesidad, Persona.Diabetes, Persona.OtrasEnfermedades, "
                + "COUNT(Historial_Clinico.Id) AS Citas, "
                + (tipo.equals("A") ? "Alumno.vivecon, Alumno.carrera" : "Personal.estatus")
                + " FROM Persona "
                + (tipo.equals("A") ? "INNER JOIN Alumno ON Persona.Id = Alumno.Id " : "INNER JOIN Personal ON Persona.Id = Personal.Id ")
                + "LEFT JOIN Historial_Clinico ON Persona.Id = Historial_Clinico.Id ";

        String sqlCondition = "WHERE (Persona.Sexo = ? OR ? IS NULL) ";
        String sqlGroupBy = " GROUP BY Persona.Id, Persona.Nombre, Persona.PrimerApellido, Persona.SegundoApellido, "
                + " Persona.Sexo, Persona.Desnutricion, Persona.Sobrepeso, Persona.Alergias, Persona.Obesidad, "
                + "Persona.Diabetes, Persona.OtrasEnfermedades, "
                + (tipo.equals("A") ? "Alumno.vivecon, Alumno.carrera" : "Personal.estatus");

        if ("A".equals(tipo))
        {
            sqlCondition += " AND (Alumno.vivecon = ? OR ? IS NULL) AND (Alumno.carrera = ? OR ? IS NULL) ";
        } else
        {
            sqlCondition += " AND (Personal.estatus = ? OR ? IS NULL) ";
        }

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

        String sqlOrder = (orden != null) ? (orden.equals("ASC") ? " ORDER BY Citas ASC" : " ORDER BY Citas DESC") : "";
        String sql = sqlBase + sqlCondition + sqlGroupBy + sqlOrder;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            int index = 1;
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

            try (ResultSet rs = stmt.executeQuery())
            {
                ResultSetMetaData metaData = rs.getMetaData();
                int colCount = metaData.getColumnCount();

                // 1. Extraer Nombres de Columnas (Omitiendo Tipo_persona)
                List<String> columnasList = new ArrayList<>();
                for (int i = 1; i <= colCount; i++)
                {
                    if (!metaData.getColumnName(i).equals("Tipo_persona"))
                    {
                        columnasList.add(metaData.getColumnName(i));
                    }
                }
                String[] columnas = columnasList.toArray(new String[0]);

                // 2. Extraer Filas de Datos
                List<Object[]> filas = new ArrayList<>();
                while (rs.next())
                {
                    Object[] filaObj = new Object[columnasList.size()];
                    int objIndex = 0;
                    for (int i = 1; i <= colCount; i++)
                    {
                        if (!metaData.getColumnName(i).equals("Tipo_persona"))
                        {
                            filaObj[objIndex++] = rs.getObject(i);
                        }
                    }
                    filas.add(filaObj);
                }

                // 3. Retornar el objeto limpio sin UI
                return new DatosTabla(columnas, filas);
            }
        }
    }

    public Object[] obtenerNombreCompleto(String cve, String tipo) throws SQLException
    {
        String sql = "SELECT Persona.Id, Persona.Nombre, Persona.PrimerApellido, Persona.SegundoApellido "
                + "FROM Persona WHERE Persona.Id = ? AND Persona.Tipo_persona = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
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
                    return null; // No se encontró la clave
                }
            }
        }
    }

    /**
     * Obtiene Todos los Datos (TD) de una persona por su Clave/ID. Reemplaza a
     * la antigua lógica de DBConnection.obtenerTD
     */
    public Object[] obtenerTodosLosDatos(String cve, String tipo) throws SQLException
    {
        String sql = "SELECT Persona.*, " + (tipo.equals("A") ? "Alumno.vivecon, Alumno.carrera" : "Personal.estatus")
                + " FROM Persona JOIN " + (tipo.equals("A") ? "Alumno" : "Personal")
                + " ON Persona.Id = " + (tipo.equals("A") ? "Alumno.Id" : "Personal.Id")
                + " WHERE Persona.Id = ? AND Persona.Tipo_persona = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setString(1, cve);
            stmt.setString(2, tipo);

            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())
                {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int col = metaData.getColumnCount();
                    Object[] obj = new Object[col - 1]; // -1 para ignorar Tipo_persona

                    for (int i = 1, j = 0; i <= col; i++)
                    {
                        if (rs.getObject(i) != null && !rs.getObject(i).equals(tipo))
                        {
                            obj[j++] = rs.getObject(i);
                        }
                    }
                    return obj;
                } else
                {
                    return null; // No se encontró la clave
                }
            }
        }
    }

    public List<Consulta> obtenerHistorialClinico(String cve, String tipo) throws SQLException
    {
        // Tu misma consulta original, pero estructurada limpia
        String sql = "SELECT Persona.Id, Persona.Nombre, Persona.PrimerApellido, Persona.SegundoApellido, "
                + "Historial_Clinico.* "
                + "FROM Persona INNER JOIN Historial_Clinico "
                + "ON Persona.Id = Historial_Clinico.Id "
                + "WHERE Persona.Id = ? AND Persona.Tipo_persona = ?";

        List<Consulta> historial = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setString(1, cve);
            stmt.setString(2, tipo);

            try (ResultSet rs = stmt.executeQuery())
            {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next())
                {
                    Object[] fila = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++)
                    {
                        fila[i - 1] = rs.getObject(i); // Guardamos desde el índice 0 en Java
                    }
                    Consulta cita = new Consulta(fila);
                    historial.add(cita);
                }
            }
        }
        return historial; // Devuelve la lista (estará vacía si no tiene citas)
    }

    public DatosTabla obtenerTodos(String tipo) throws SQLException
    {
        String sql = "SELECT Persona.Id, Persona.Nombre, Persona.PrimerApellido, Persona.SegundoApellido, "
                + "Persona.Sexo, Persona.Desnutricion, Persona.Sobrepeso, Persona.Alergias, "
                + "Persona.Obesidad, Persona.Diabetes, Persona.OtrasEnfermedades, "
                + "COUNT(Historial_Clinico.Id) AS Citas, "
                + (tipo.equals("A") ? "Alumno.vivecon, Alumno.carrera" : "Personal.estatus")
                + " FROM Persona "
                + (tipo.equals("A") ? "JOIN Alumno ON Persona.Id = Alumno.Id " : "JOIN Personal ON Persona.Id = Personal.Id ")
                + "LEFT JOIN Historial_Clinico ON Persona.Id = Historial_Clinico.Id "
                + "WHERE Persona.Tipo_persona = ? "
                + "GROUP BY Persona.Id, Persona.Nombre, Persona.PrimerApellido, Persona.SegundoApellido, "
                + "Persona.Sexo, Persona.Desnutricion, Persona.Sobrepeso, Persona.Alergias, "
                + "Persona.Obesidad, Persona.Diabetes, Persona.OtrasEnfermedades, "
                + (tipo.equals("A") ? "Alumno.vivecon, Alumno.carrera" : "Personal.estatus");

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setString(1, tipo);

            try (ResultSet rs = stmt.executeQuery())
            {
                ResultSetMetaData metaData = rs.getMetaData();
                int colCount = metaData.getColumnCount();

                // Extraer encabezados (omitiendo Tipo_persona si estuviera)
                List<String> columnasList = new ArrayList<>();
                for (int i = 1; i <= colCount; i++)
                {
                    columnasList.add(metaData.getColumnName(i));
                }
                String[] columnas = columnasList.toArray(new String[0]);

                // Extraer datos
                List<Object[]> filas = new ArrayList<>();
                while (rs.next())
                {
                    Object[] fila = new Object[colCount];
                    for (int i = 1; i <= colCount; i++)
                    {
                        fila[i - 1] = rs.getObject(i);
                    }
                    filas.add(fila);
                }
                return new DatosTabla(columnas, filas);
            }
        }
    }

    /**
     * Inserta un nuevo registro de Persona y su especialización (Alumno o
     * Personal).
     */
    public boolean insertarRegistro(Object[] persona, int tipoPersona) throws SQLException
    {
        boolean exito = false;

        // 1. Validar que no exista (Asumo que tienes el método existeEnTabla que ya habíamos visto)
        if (existeEnTabla("Persona", (Integer) persona[0]))
        {
            return false; // Ya existe, no se inserta
        }

        String insertPersonaSql = "INSERT INTO Persona (Id, Nombre, PrimerApellido, SegundoApellido, Sexo, Desnutricion, Sobrepeso, Alergias, Obesidad, Diabetes, OtrasEnfermedades, Tipo_persona) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection())
        {
            conn.setAutoCommit(false); // Transacción manual

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
                pstmt.setString(12, (String) persona[11]); // "A" o "P"
                pstmt.executeUpdate();

                // Insertar en la tabla hija correspondiente
                if (tipoPersona == 1)
                { // Alumno
                    String insertAlumnoSql = "INSERT INTO Alumno (Id, vivecon, carrera) VALUES (?, ?, ?)";
                    try (PreparedStatement pstmtA = conn.prepareStatement(insertAlumnoSql))
                    {
                        pstmtA.setInt(1, (Integer) persona[0]);
                        pstmtA.setString(2, (String) persona[12]);
                        pstmtA.setString(3, (String) persona[13]);
                        pstmtA.executeUpdate();
                    }
                } else if (tipoPersona == 2)
                { // Personal
                    String insertPersonalSql = "INSERT INTO Personal (Id, Estatus) VALUES (?, ?)";
                    try (PreparedStatement pstmtP = conn.prepareStatement(insertPersonalSql))
                    {
                        pstmtP.setInt(1, (Integer) persona[0]);
                        pstmtP.setString(2, (String) persona[12]);
                        pstmtP.executeUpdate();
                    }
                }

                // Si todo sale bien, registramos en Auditoría usando el Singleton
                int idUsuarioActivo = SesionActual.getInstance().getIdUsuario();
                String tablaAfectada = (tipoPersona == 1) ? "Persona/Alumno" : "Persona/Personal";
                GestorAuditoria.registrarAccion(conn, idUsuarioActivo, "INSERT", tablaAfectada, " ID: " + persona[0]);

                conn.commit();
                exito = true;

            } catch (SQLException e)
            {
                conn.rollback();
                throw e; // Lanza el error al Controlador
            }
        }
        return exito;
    }

    // Método auxiliar (como lo tenías originalmente)
    private boolean existeEnTabla(String tabla, int id) throws SQLException
    {
        String checkQuery = "SELECT * FROM " + tabla + " WHERE Id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(checkQuery))
        {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery())
            {
                return rs.next();
            }
        }
    }

    /**
     * Inserta un nuevo registro en el historial clínico del paciente.
     */
    public boolean insertarConsultaMedica(int idPaciente, String padecimiento, String antecedentes, String medicamento, String plan) throws SQLException
    {
        boolean exito = false;
        String sql = "INSERT INTO Historial_Clinico (Id, padecimiento_actual, Antecedentes, Medicamento, PlanDeTratamiento, Fecha) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection())
        {
            conn.setAutoCommit(false); // Iniciamos transacción

            try (PreparedStatement pstmt = conn.prepareStatement(sql))
            {
                pstmt.setInt(1, idPaciente);
                pstmt.setString(2, padecimiento);
                pstmt.setString(3, antecedentes);
                pstmt.setString(4, medicamento);
                pstmt.setString(5, plan);

                // Generamos la fecha actual directamente para MySQL
                java.sql.Date fechaSQL = new java.sql.Date(System.currentTimeMillis());
                pstmt.setDate(6, fechaSQL);

                pstmt.executeUpdate();

                // Registramos en la Bitácora de Auditoría ("Quién" insertó la cita)
                int idUsuarioActivo = SesionActual.getInstance().getIdUsuario();
                GestorAuditoria.registrarAccion(conn, idUsuarioActivo, "INSERT", "Historial_Clinico", "Paciente ID: " + idPaciente);

                conn.commit();
                exito = true;
            } catch (SQLException e)
            {
                conn.rollback();
                throw e;
            }
        }
        return exito;
    }

    /**
     * Actualiza los datos de la Persona y de su tabla hija (Alumno o Personal).
     */
    public boolean modificarRegistro(Object[] p, int tipoPersona) throws SQLException
    {
        boolean exito = false;

        String sqlPersona = "UPDATE Persona SET Nombre = ?, PrimerApellido = ?, SegundoApellido = ?, "
                + "Desnutricion = ?, Sobrepeso = ?, Alergias = ?, Obesidad = ?, Diabetes = ?, "
                + "OtrasEnfermedades = ? WHERE Id = ?";

        String sqlEspecializada = (tipoPersona == 1)
                ? "UPDATE Alumno SET vivecon = ?, carrera = ? WHERE Id = ?"
                : "UPDATE Personal SET estatus = ? WHERE Id = ?";

        try (Connection conn = DBConnection.getConnection())
        {
            conn.setAutoCommit(false); // Iniciamos transacción

            try (PreparedStatement pstmtPersona = conn.prepareStatement(sqlPersona); PreparedStatement pstmtE = conn.prepareStatement(sqlEspecializada))
            {

                // 1. Update a la tabla Persona (Los índices coinciden con el arreglo de tu interfaz)
                pstmtPersona.setString(1, (String) p[1]); // Nombre
                pstmtPersona.setString(2, (String) p[2]); // Primer Apellido
                pstmtPersona.setString(3, (String) p[3]); // Segundo Apellido
                pstmtPersona.setBoolean(4, (Boolean) p[4]); // Desnutrición
                pstmtPersona.setBoolean(5, (Boolean) p[5]); // Sobrepeso
                pstmtPersona.setBoolean(6, (Boolean) p[6]); // Alergias
                pstmtPersona.setBoolean(7, (Boolean) p[7]); // Obesidad
                pstmtPersona.setBoolean(8, (Boolean) p[8]); // Diabetes
                pstmtPersona.setString(9, (String) p[9]); // Otras
                pstmtPersona.setInt(10, Integer.parseInt(p[0].toString())); // ID

                int rowsPersona = pstmtPersona.executeUpdate();

                // 2. Update a la tabla Hija (Alumno o Personal)
                if (tipoPersona == 1)
                { // Alumno
                    pstmtE.setString(1, (String) p[10]); // ViveCon
                    pstmtE.setString(2, (String) p[11]); // Carrera
                    pstmtE.setInt(3, Integer.parseInt(p[0].toString())); // ID
                } else
                { // Personal
                    pstmtE.setString(1, (String) p[10]); // Estatus
                    pstmtE.setInt(2, Integer.parseInt(p[0].toString())); // ID
                }
                pstmtE.executeUpdate();

                // 3. Auditoría (Registro de "Quién modificó")
                if (rowsPersona > 0)
                {
                    int idUsuarioActivo = SesionActual.getInstance().getIdUsuario();
                    String tablaAfectada = (tipoPersona == 1) ? "Persona/Alumno" : "Persona/Personal";
                    GestorAuditoria.registrarAccion(conn, idUsuarioActivo, "UPDATE", tablaAfectada, " ID: " + p[0]);
                }

                conn.commit();
                exito = (rowsPersona > 0);

            } catch (SQLException e)
            {
                conn.rollback();
                throw e; // Lanzamos el error al Controlador
            }
        }
        return exito;
    }

    public List<Object[]> obtenerListaDatosFiltrados(String sexo, String viveCon, String carrera, String estatus,
            boolean desnutricion, boolean sobrepeso, boolean alergias,
            boolean obesidad, boolean diabetes, boolean otras,
            String frecuencia, String tipo) throws SQLException
    {

        String sqlBase = "SELECT Persona.Id, Persona.Nombre, Persona.PrimerApellido, Persona.SegundoApellido, Persona.Sexo, "
                + "Persona.Desnutricion, Persona.Sobrepeso, Persona.Alergias, Persona.Obesidad, Persona.Diabetes, Persona.OtrasEnfermedades, "
                + "COUNT(Historial_Clinico.Id) AS Citas, "
                + (tipo.equals("A") ? "Alumno.vivecon, Alumno.carrera" : "Personal.estatus")
                + " FROM Persona "
                + (tipo.equals("A") ? "INNER JOIN Alumno ON Persona.Id = Alumno.Id " : "INNER JOIN Personal ON Persona.Id = Personal.Id ")
                + "LEFT JOIN Historial_Clinico ON Persona.Id = Historial_Clinico.Id ";

        String sqlCondition = "WHERE (Persona.Sexo = ? OR ? IS NULL) ";
        String sqlGroupBy = " GROUP BY Persona.Id, Persona.Nombre, Persona.PrimerApellido, Persona.SegundoApellido, "
                + " Persona.Sexo, Persona.Desnutricion, Persona.Sobrepeso, Persona.Alergias, Persona.Obesidad, "
                + "Persona.Diabetes, Persona.OtrasEnfermedades, "
                + (tipo.equals("A") ? "Alumno.vivecon, Alumno.carrera" : "Personal.estatus");

        if ("A".equals(tipo))
        {
            sqlCondition += " AND (Alumno.vivecon = ? OR ? IS NULL) AND (Alumno.carrera = ? OR ? IS NULL) ";
        } else
        {
            sqlCondition += " AND (Personal.estatus = ? OR ? IS NULL) ";
        }

        if (desnutricion)
        {
            sqlCondition += " AND (Persona.Desnutricion = ? OR ? IS NULL) ";
        }
        if (sobrepeso)
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

        String sqlOrder = (frecuencia != null) ? (frecuencia.equals("ASC") ? " ORDER BY Citas ASC" : " ORDER BY Citas DESC") : "";
        String sql = sqlBase + sqlCondition + sqlGroupBy + sqlOrder;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            int index = 1;
            stmt.setString(index++, sexo);
            stmt.setString(index++, sexo);

            if ("A".equals(tipo))
            {
                stmt.setString(index++, viveCon);
                stmt.setString(index++, viveCon);
                stmt.setString(index++, carrera);
                stmt.setString(index++, carrera);
            } else
            {
                stmt.setString(index++, estatus);
                stmt.setString(index++, estatus);
            }
            if (desnutricion)
            {
                stmt.setBoolean(index++, desnutricion);
                stmt.setBoolean(index++, desnutricion);
            }
            if (sobrepeso)
            {
                stmt.setBoolean(index++, sobrepeso);
                stmt.setBoolean(index++, sobrepeso);
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

            try (ResultSet rs = stmt.executeQuery())
            {
                ResultSetMetaData metaData = rs.getMetaData();
                int colCount = metaData.getColumnCount();

                // 1. Extraer Nombres de Columnas
                List<String> columnasList = new ArrayList<>();
                for (int i = 1; i <= colCount; i++)
                {
                    if (!metaData.getColumnName(i).equalsIgnoreCase("Tipo_persona"))
                    {
                        columnasList.add(metaData.getColumnName(i));
                    }
                }

                // 2. Extraer Filas de Datos
                List<Object[]> lista = new ArrayList<>();
                while (rs.next())
                {
                    // --- AQUÍ ESTABA EL ERROR ---
                    // Debemos crear el arreglo con el tamaño exacto de las columnas que SÍ queremos guardar
                    Object[] fila = new Object[columnasList.size()];

                    int indiceFila = 0; // Índice para nuestro nuevo arreglo 'fila'

                    for (int i = 1; i <= colCount; i++)
                    {
                        // Solo guardamos si la columna no es "Tipo_persona"
                        if (!metaData.getColumnName(i).equalsIgnoreCase("Tipo_persona"))
                        {
                            fila[indiceFila] = rs.getObject(i);
                            indiceFila++; // Avanzamos al siguiente espacio en nuestro arreglo
                        }
                    }
                    lista.add(fila);
                }

                // 3. Retornar los datos
                // Nota: Si necesitas los nombres de columnas y los datos, deberías devolver un objeto DatosTabla 
                // como habíamos planeado, pero si solo quieres la lista, así queda:
                return lista;
            }
        }
    }
}
