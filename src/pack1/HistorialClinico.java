/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pack1;

import java.util.Date;

/**
 *
 * @author HP
 */
public class HistorialClinico
{
    private String padecimientoAct;
    private String antecedentesPer;
    private String medicamento;
    private String planTratamiento;
    private Date Fecha;

    public HistorialClinico()
    {
    }

    public HistorialClinico(String padecimientoAct, String antecedentesPer, String medicamento, String planTratamiento, Date Fecha)
    {
        this.padecimientoAct = padecimientoAct;
        this.antecedentesPer = antecedentesPer;
        this.medicamento = medicamento;
        this.planTratamiento = planTratamiento;
        this.Fecha = Fecha;
    }

    /**
     * @return the padecimientoAct
     */
    public String getPadecimientoAct()
    {
        return padecimientoAct;
    }

    /**
     * @param padecimientoAct the padecimientoAct to set
     */
    public void setPadecimientoAct(String padecimientoAct)
    {
        this.padecimientoAct = padecimientoAct;
    }

    /**
     * @return the antecedentesPer
     */
    public String getAntecedentesPer()
    {
        return antecedentesPer;
    }

    /**
     * @param antecedentesPer the antecedentesPer to set
     */
    public void setAntecedentesPer(String antecedentesPer)
    {
        this.antecedentesPer = antecedentesPer;
    }

    /**
     * @return the medicamento
     */
    public String getMedicamento()
    {
        return medicamento;
    }

    /**
     * @param medicamento the medicamento to set
     */
    public void setMedicamento(String medicamento)
    {
        this.medicamento = medicamento;
    }

    /**
     * @return the planTratamiento
     */
    public String getPlanTratamiento()
    {
        return planTratamiento;
    }

    /**
     * @param planTratamiento the planTratamiento to set
     */
    public void setPlanTratamiento(String planTratamiento)
    {
        this.planTratamiento = planTratamiento;
    }

    /**
     * @return the Fecha
     */
    public Date getFecha()
    {
        return Fecha;
    }

    /**
     * @param Fecha the Fecha to set
     */
    public void setFecha(Date Fecha)
    {
        this.Fecha = Fecha;
    }

    @Override
    public String toString()
    {
        return "HistorialClinico{" + "padecimientoAct=" + padecimientoAct + ", antecedentesPer=" + antecedentesPer + ", medicamento=" + medicamento + ", planTratamiento=" + planTratamiento + ", Fecha=" + Fecha + '}';
    }
    
}
