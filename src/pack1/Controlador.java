/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pack1;

import cjb.ci.Mensajes;
import java.util.Date;
import java.util.function.ObjDoubleConsumer;
import javax.swing.JFrame;

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
     *
     * @param array Es el arreglo de datos para indicar el numero de personas
     * que hay
     * @param matriz Es donde se guardaran los datos
     * @param hc Son los datos que se estan ingresando de la consulta
     * @param pos Indica en que persona se estan ingresando los datos
     * @param jf
     * @param cve Es el numero de cuenta del paciente
     * @return
     */
    private static HistorialClinico[][] insertaF(Datos[] array, HistorialClinico matriz[][],HistorialClinico hc, int pos, JFrame jf, String cve)
    {
        if (matriz == null)
        {
            matriz = new HistorialClinico[array.length][1];
            matriz[pos][0] = hc;
        } else
        {
            HistorialClinico nvo[][] = new HistorialClinico[array.length][];
            int h = validaMatricula(array, cve, jf);
            for (int i = 0; i < array.length; i++)
            {
                if (i == pos)
                {
                    if (matriz[i][0] == null)
                    {
                        nvo[i] = new HistorialClinico[1];
                        nvo[pos][0] = hc;
                    } else
                    {
                        nvo[i] = new HistorialClinico[matriz[i].length + 1];
                        System.arraycopy(matriz[i], 0, nvo[i], 0, matriz[i].length);
                        nvo[pos][matriz[i].length] = hc;
                    }

                } else
                {
                    
                    nvo[i] = new HistorialClinico[matriz[i].length];
                    System.arraycopy(matriz[i], 0, nvo[i], 0, matriz[i].length);
                }
            }
            matriz = nvo;
        }

        return matriz;
    }

    /**
     * Metodo Para Ingresar una consulta en un paciente
     *
     * @param array es el arreglo de datos que se esta ocupando
     * @param matriz Sera donde se van a guardar los datos de las consultas
     * @param cve Es el numero de cuenta del paciente
     * @param jf
     * @param pA Son los padecimientos que sufre el paciente
     * @param aP Son los padecimientos previos del paciente
     * @param m Seran los medicamentos recomendados
     * @param t Es el tratamiento que se recomendo
     * @param Fecha Pues es la fecha en que se realiza la consulta
     */
    public static void consulta(Datos array[], HistorialClinico matriz[][], String cve, JFrame jf, String pA, String aP, String m, String t, Date Fecha)
    {
        int pos = validaMatricula(array, cve, jf);
        HistorialClinico hc = new HistorialClinico(pA, aP, m, t, Fecha);
        insertaF(array, matriz, hc, pos, jf, cve);
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
     *
     * @param array el arreglo del cual se van a comparar las matriculas
     * @param cve la clave que va a ser validada
     * @param jf el frame donde se mostraran los mensajes
     * @return retorna true si la matricula es valida, y false si no lo es (ya
     * existe dentro del arreglo)
     */
    private static int validaMatricula(Datos array[], String cve, JFrame jf)
    {
        int j;
        if (array != null)
        {
            for (int i = 0; i < array.length; i++)
            {
                if (cve.equals(array[i].getCve()))
                {

                    return i;
                }
            }
        }

        return -1;
    }

    public static Datos[] altaAlumno(Datos array[], JFrame jf, String cve, String nom, String pApellido, String sApellido, char sexo, boolean desnut, boolean sobrepeso, boolean alergias, boolean obecidad, boolean diabetes, String otras, int viveCon, int carrera)
    {
        if (validaMatricula(array, cve, jf) == -1)
        {
            Datos nvoA = new Alumnos(viveCon, carrera, cve, nom, pApellido, sApellido, sexo, desnut, sobrepeso, alergias, obecidad, diabetes, otras);
            array = inserta(array, nvoA);
        } else
        {
            Mensajes.error(jf, "Error la matricula Ya EXISTE");
        }
        return array;
    }

    public static Datos[] altaPersonal(Datos array[], JFrame jf, char estatus, String cve, String nom, String pApellido, String sApellido, char sexo, boolean desnut, boolean sobrepeso, boolean alergias, boolean obecidad, boolean diabetes, String otras)
    {
        if (validaMatricula(array, cve, jf) == -1)
        {
            Datos nvoA = new Personal(estatus, cve, nom, pApellido, sApellido, sexo, desnut, sobrepeso, alergias, obecidad, diabetes, otras);
            array = inserta(array, nvoA);
        } else
        {
            Mensajes.error(jf, "Error la matricula Ya EXISTE");
        }
        return array;
    }

    public static Datos[] modificacionesP(JFrame jf, Datos array[], char estatus, String cve, char sexo, boolean desnut, boolean sobrepeso, boolean alergias, boolean obecidad, boolean diabetes, String otras)
    {
        int pos = validaMatricula(array, cve, jf);
        if (validaMatricula(array, cve, jf) != -1 && array[pos] instanceof Personal)
        {
            Datos nvoA = new Personal(estatus, array[0].getCve(), array[0].getNom(), array[0].getPrimerAp(), array[0].getSegundoAp(), sexo, desnut, sobrepeso, alergias, obecidad, diabetes, otras);
            array = reutilizar(array, nvoA, pos, jf);
        } else
        {
            Mensajes.error(jf, "La matricula no existe o pertenece a otra categoria");
        }
        return array;
    }

    public static Datos[] modificacionesA(JFrame jf, Datos array[], int viveC, int carrera, String cve, char sexo, boolean desnut, boolean sobrepeso, boolean alergias, boolean obecidad, boolean diabetes, String otras)
    {
        int pos = validaMatricula(array, cve, jf);
        if (validaMatricula(array, cve, jf) != -1 && array[pos] instanceof Alumnos)
        {
            Datos nvoA = new Alumnos(viveC, carrera, array[0].getCve(), array[0].getNom(), array[0].getPrimerAp(), array[0].getSegundoAp(), sexo, desnut, sobrepeso, alergias, obecidad, diabetes, otras);
            array = reutilizar(array, nvoA, pos, jf);
        } else
        {
            Mensajes.error(jf, "La matricula no existe o pertenece a otra categoria");
        }
        return array;
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
        if (b=='h')
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
                s+="Ingenieria en Software";
                break;
            case 2:
                s+="Ingenieria en Plasticos";
                break;
            case 3:
                s+="Ingenieria en Mecanica";
                break;
            case 4:
                s+="Ingenieria en Computacion";
                break;
            case 5:
                s+="Licenciatura Proteccion ciudadana";
                break;
            default:
                s+="No existe esa carrera";
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
                s+="Solo";
                break;
            case 2:
                s+="Con mi papá";
                break;
            case 3:
                s+="Con mi mamá";
                break;
            case 4:
                s+="Con mi mamá y papá";
                break;
            case 5:
                s+="Otros";
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
        int z=0;
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] instanceof Alumnos)
            {
                z++;
            }
        }
        Object obj[][]=new Object[z][13];
        for (int i = 0,j=0; i < arr.length; i++)
        {
            if (arr[i] instanceof Alumnos)
            {
                obj[i][0]=arr[i].getCve();
                obj[i][1]=arr[i].getNom();
                obj[i][2]=arr[i].getPrimerAp();
                obj[i][3]=arr[i].getSegundoAp();
                obj[i][4]=tC(arr[i].getSexo());
                obj[i][5]=tF(arr[i].isDesnutricion());
                obj[i][6]=tF(arr[i].isSobrepeso());
                obj[i][7]=tF(arr[i].isAlergias());
                obj[i][8]=tF(arr[i].isObesidad());
                obj[i][9]=tF(arr[i].isDiabetes());
                obj[i][10]=arr[i].getOtras();
                obj[i][11]=iTC(((Alumnos)arr[i]).carrera);
                obj[i][12]=iTF(((Alumnos)arr[i]).viveCon);
                j++;
            }
        }
        return obj;
    }
    public Object[][] tablaP(Datos arr[])
    {
        int z=0;
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] instanceof Personal)
            {
                z++;
            }
        }
        Object obj[][]=new Object[z][12];
        for (int i = 0,j=0; i < arr.length; i++)
        {
            if (arr[i] instanceof Personal)
            {
                obj[i][0]=arr[i].getCve();
                obj[i][1]=arr[i].getNom();
                obj[i][2]=arr[i].getPrimerAp();
                obj[i][3]=arr[i].getSegundoAp();
                obj[i][4]=tC(arr[i].getSexo());
                obj[i][5]=tF(arr[i].isDesnutricion());
                obj[i][6]=tF(arr[i].isSobrepeso());
                obj[i][7]=tF(arr[i].isAlergias());
                obj[i][8]=tF(arr[i].isObesidad());
                obj[i][9]=tF(arr[i].isDiabetes());
                obj[i][10]=arr[i].getOtras();
                obj[i][11]=iTC(((Personal)arr[i]).getEstatus());
                j++;
            }
        }
        return obj;
    }
}
