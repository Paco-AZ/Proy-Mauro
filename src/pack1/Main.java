/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pack1;

/**
 *
 * @author HP
 */
public class Main
{

    public static void main(String[] args)
    {
        Datos datos[] = null;
        datos=Controlador.altaAlumno(datos, null, "12345", "Jose", "Azteca", "Otomi", 'h', true, true, true, true, true, "Deficiencia renal", 4, 2);
        System.out.println(datos[0].toString());

    }
}
