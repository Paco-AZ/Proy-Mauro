package modelo.entidades;

import java.util.List;

// Esta clase sirve para transportar la matriz de datos y sus encabezados
public class DatosTabla
{

    private String[] encabezados;
    private List<Object[]> filas;

    public DatosTabla(String[] encabezados, List<Object[]> filas)
    {
        this.encabezados = encabezados;
        this.filas = filas;
    }

    public String[] getEncabezados()
    {
        return encabezados;
    }

    public List<Object[]> getFilas()
    {
        return filas;
    }
}
