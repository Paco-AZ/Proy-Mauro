/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pack1;

import BD.AccessDBConnection;
import cjb.ci.Mensajes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.function.ObjDoubleConsumer;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import poo.Principal;

/**
 *
 * @author HP
 */
public class Controlador
{

    /**
     * Metodo para insertar un nuevo elemento a un arreglo de tipo dato
     *
     * @param array recive el arreglo donde se va a incertar el dato
     * @param d el dato que se desea insertar
     * @return retorna la direccion de memoria del nuevo arreglo con el dato
     * insertado
     */
    private static Datos[] inserta(Datos array[], Datos d)
    {
        if (array == null)
        {
            array = new Datos[1];
            array[0] = d;

        } else
        {
            Datos nvo[] = new Datos[array.length + 1];
            System.arraycopy(array, 0, nvo, 0, array.length);
            nvo[array.length] = d;
            array = nvo;
        }
        return array;
    }

    /**
     * Metodo para insertar un nuevo elemento a un arreglo de tipo dato
     *
     * @param array recive el arreglo donde se va a incertar el dato
     * @param d el dato que se desea insertar
     * @return retorna la direccion de memoria del nuevo arreglo con el dato
     * insertado
     */
    public static Datos[] elimina(Datos array[], int pos)
    {
        if (array.length == 1)
        {
            array = null;

        } else
        {
            Datos nvo[] = new Datos[array.length - 1];
            for (int i = 0, j = 0; i < array.length; i++)
            {
                if (i != pos)
                {
                    nvo[j++] = array[i];
                }
            }
            array = nvo;
        }
        return array;
    }

    public static HistorialClinico[][] eliminaM(Datos array[], int pos, HistorialClinico matriz[][])
    {
        if (array.length == 1)
        {
            matriz = null;

        } else
        {

            HistorialClinico nvoM[][] = new HistorialClinico[array.length - 1][];
            for (int i = 0, j = 0; i < array.length; i++)
            {
                if (i != pos)
                {
                    if (matriz[i] != null)
                    {
                        for (int k = 0; k < matriz[i].length; k++)
                        {

                            nuevaConsulta(nvoM, matriz[i][k], j);

                        }
                    }
                    j++;
                }
            }
            matriz = null;
            matriz = nvoM;
        }
        return matriz;
    }

    private static Datos[] reutilizar(Datos array[], Datos d, int pos, JFrame jf)
    {
        if (array == null)
        {
            Mensajes.error(jf, "Error no hay Nadie registrado");

        } else
        {
            array[pos] = d;
        }
        return array;
    }

    /**
     * Metodo para obtener el nombre completos desde el cve
     *
     * @param array el arreglo del cual se van a comparar las matriculas
     * @param cve la clave que va a ser validada
     * @param jf el frame donde se mostraran los mensajes
     * @return retorna true si la matricula es valida, y false si no lo es (ya
     * existe dentro del arreglo)
     */
    public static Object[] ObtenerNC(String cve, JFrame jf)
    {
        int j;
        String url = "jdbc:ucanaccess://BD\\Proyecto2P.accdb";

        try (Connection conn = DriverManager.getConnection(url))
        {

            
            return AccessDBConnection.obtenerCVE(conn, cve, jf, (Principal.tipo == 1 ? "A" : "P"));

        } catch (SQLException e)
        {
            System.out.println("Clave no encontrada");
            return null;
        }
    }
    
    public static Object[] ObtenerTD(String cve, JFrame jf)
    {
        int j;
        String url = "jdbc:ucanaccess://BD\\Proyecto2P.accdb";

        try (Connection conn = DriverManager.getConnection(url))
        {
            return AccessDBConnection.obtenerTD(conn, cve, jf, (Principal.tipo == 1 ? "A" : "P"));
        } catch (SQLException e)
        {
            return null;
        }
    }

    public String tF(boolean b)
    {
        String s = "";
        if (b)
        {
            s += "Si";
        } else
        {
            s += "No";
        }
        return s;
    }

    public String tC(char b)
    {
        String s = "";
        if (b == 'h')
        {
            s += "Hombre";
        } else
        {
            s += "Mujer";
        }
        return s;
    }

    public String iTC(int b)
    {
        String s = "";
        switch (b)
        {
            case 1:
                s += "Ingenieria en Software";
                break;
            case 2:
                s += "Ingenieria en Plasticos";
                break;
            case 3:
                s += "Ingenieria en Mecanica";
                break;
            case 4:
                s += "Ingenieria en Computacion";
                break;
            case 5:
                s += "Licenciatura Proteccion ciudadana";
                break;
            default:
                s += "No existe esa carrera";
                throw new AssertionError();
        }
        return s;
    }

    public String iTF(int b)
    {
        String s = "";
        switch (b)
        {
            case 1:
                s += "Solo";
                break;
            case 2:
                s += "Con mi papá";
                break;
            case 3:
                s += "Con mi mamá";
                break;
            case 4:
                s += "Con mi mamá y papá";
                break;
            case 5:
                s += "Otros";
                break;
            default:
                throw new AssertionError();
        }
        return s;
    }

    public String dT(Date b)
    {
        String s = "";
        s += b.toString();
        return s;
    }

    public Object[][] tablaA(Datos arr[])
    {
        int z = 0;
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] instanceof Alumnos)
            {
                z++;
            }
        }
        Object obj[][] = new Object[z][13];
        for (int i = 0, j = 0; i < arr.length; i++)
        {
            if (arr[i] instanceof Alumnos)
            {
                obj[i][0] = arr[i].getCve();
                obj[i][1] = arr[i].getNom();
                obj[i][2] = arr[i].getPrimerAp();
                obj[i][3] = arr[i].getSegundoAp();
                obj[i][4] = tC(arr[i].getSexo());
                obj[i][5] = tF(arr[i].isDesnutricion());
                obj[i][6] = tF(arr[i].isSobrepeso());
                obj[i][7] = tF(arr[i].isAlergias());
                obj[i][8] = tF(arr[i].isObesidad());
                obj[i][9] = tF(arr[i].isDiabetes());
                obj[i][10] = arr[i].getOtras();
                obj[i][11] = iTC(((Alumnos) arr[i]).carrera);
                obj[i][12] = iTF(((Alumnos) arr[i]).viveCon);
                j++;
            }
        }
        return obj;
    }

    public Object[][] tablaP(Datos arr[])
    {
        int z = 0;
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] instanceof Personal)
            {
                z++;
            }
        }
        Object obj[][] = new Object[z][12];
        for (int i = 0, j = 0; i < arr.length; i++)
        {
            if (arr[i] instanceof Personal)
            {
                obj[i][0] = arr[i].getCve();
                obj[i][1] = arr[i].getNom();
                obj[i][2] = arr[i].getPrimerAp();
                obj[i][3] = arr[i].getSegundoAp();
                obj[i][4] = tC(arr[i].getSexo());
                obj[i][5] = tF(arr[i].isDesnutricion());
                obj[i][6] = tF(arr[i].isSobrepeso());
                obj[i][7] = tF(arr[i].isAlergias());
                obj[i][8] = tF(arr[i].isObesidad());
                obj[i][9] = tF(arr[i].isDiabetes());
                obj[i][10] = arr[i].getOtras();
                obj[i][11] = iTC(((Personal) arr[i]).getEstatus());
                j++;
            }
        }
        return obj;
    }

    public String reportes(Datos[] array, HistorialClinico[][] matriz, int opc)
    {
        String s = "";
        switch (opc)
        {
            case 1:
                for (int i = 0; i < array.length; i++)
                {
                    if (array[i].getSexo() == 'h')
                    {
                        s += array[i].getNom() + " Ha tenido " + (String.valueOf(matriz[i].length)) + " consultas" + "\n";
                    }
                }
                break;
            case 2:
                for (int i = 0; i < array.length; i++)
                {
                    if (array[i].getSexo() == 'm')
                    {
                        s += array[i].getNom() + " Ha tenido " + (String.valueOf(matriz[i].length)) + " consultas" + "\n";
                    }
                }
                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            default:
                throw new AssertionError();
        }
        return s;
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

    /**
     * Metodo que aumenta un renglon a la matriz de historial clinico
     *
     * @param hC la matriz a la que se le va a agregar el renglon
     * @return la matriz con un renglon mas
     */
    public static HistorialClinico[][] nuevoHistorial(HistorialClinico[][] hC)
    {
        if (hC == null)
        {
            hC = new HistorialClinico[1][];
        } else
        {
            HistorialClinico nvo[][] = new HistorialClinico[hC.length + 1][];
            System.arraycopy(hC, 0, nvo, 0, hC.length);
            hC = nvo;
        }
        return hC;
    }

    /**
     * metodo que agrega una nueva columna a la matriz de historial clinico en
     * un renglon dado e inserta la consulta en la columna creada en ese renglon
     *
     * @param hC la matriz a la que se le va a agregar una columna
     * @param dato el dato que se va a insertar
     * @param pos la posicion donde se va a crear la nueva columna
     * @return la matriz con la nueva columna y el dato incertado
     */
    public static HistorialClinico[][] nuevaConsulta(HistorialClinico[][] hC, HistorialClinico dato, int pos)
    {
        if (hC[pos] == null)
        {
            hC[pos] = new HistorialClinico[1];
        } else
        {
            HistorialClinico nvo[] = new HistorialClinico[hC[pos].length + 1];
            System.arraycopy(hC[pos], 0, nvo, 0, hC[pos].length);
            hC[pos] = nvo;
        }
        hC[pos][hC[pos].length - 1] = dato;
        return hC;
    }
}
