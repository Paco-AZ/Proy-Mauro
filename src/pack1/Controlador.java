/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pack1;

import cjb.ci.Mensajes;
import javax.swing.JFrame;

/**
 *
 * @author HP
 */
public class Controlador
{

    public static Datos[] inserta(Datos array[], Datos d, JFrame jf)
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
        Mensajes.exito(jf, "Se ha guardado correctamente");
        return array;
    }
}
