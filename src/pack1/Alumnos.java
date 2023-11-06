/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pack1;

/**
 *
 * @author HP
 */
public class Alumnos extends Datos
{
    int viveCon;
    int carrera;

    public Alumnos()
    {
    }

    public Alumnos(int viveCon, int carrera, String cve, String nom, String primerAp, String segundoAp, char sexo, boolean desnutricion, boolean sobrepeso, boolean alergias, boolean obesidad, boolean diabetes, String otras)
    {
        super(cve, nom, primerAp, segundoAp, sexo, desnutricion, sobrepeso, alergias, obesidad, diabetes, otras);
        this.viveCon = viveCon;
        this.carrera = carrera;
    }

    @Override
    public String toString()
    {
        return super.toString()+ "Alumnos{" + "viveCon=" + viveCon + ", carrera=" + carrera + '}';
    }
    
    
}
