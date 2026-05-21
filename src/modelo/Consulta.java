package modelo;

public class Consulta {
    private int idConsulta;
    private String nombrePaciente;
    private String apellidoPPaciente;
    private String apellidoMPaciente;
    private String fecha;
    private String padecimiento;
    private String antecedentes;
    private String medicamento;
    private String planTratamiento;
    // ... otros campos

    public Consulta(int idConsulta, String nombrePaciente, String fecha, String padecimiento, String antecedentes, String medicamento, String planTratamiento)
    {
        this.idConsulta = idConsulta;
        this.nombrePaciente = nombrePaciente;
        this.fecha = fecha;
        this.padecimiento = padecimiento;
        this.antecedentes = antecedentes;
        this.medicamento = medicamento;
        this.planTratamiento = planTratamiento;
    }

    public Consulta(Object[] lista)
    {
        this.idConsulta = (Integer)lista[4];
        this.nombrePaciente = String.valueOf(lista[1]);
        this.apellidoPPaciente = String.valueOf(lista[2]);
        this.apellidoMPaciente = String.valueOf(lista[3]);
        this.fecha = String.valueOf(lista[10]);
        this.padecimiento = String.valueOf(lista[6]);
        this.antecedentes = String.valueOf(lista[7]);
        this.medicamento = String.valueOf(lista[8]);
        this.planTratamiento = String.valueOf(lista[9]);
    }

    
    
    public int getIdConsulta()
    {
        return idConsulta;
    }

    public String getNombrePaciente()
    {
        return nombrePaciente;
    }

    public String getFecha()
    {
        return fecha;
    }

    public String getPadecimiento()
    {
        return padecimiento;
    }

    public String getAntecedentes()
    {
        return antecedentes;
    }

    public String getMedicamento()
    {
        return medicamento;
    }

    public String getPlanTratamiento()
    {
        return planTratamiento;
    }

    public String getApellidoP()
    {
        return apellidoPPaciente;
    }

    public String getApellidoM()
    {
        return apellidoMPaciente;
    }

    
    
    @Override
    public String toString() {
        // Así es como se verá en tu lista
        return "Cita #" + idConsulta + " - " + nombrePaciente + " (" + fecha + ")";
    }
}