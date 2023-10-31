/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pack1;

/**
 *
 * @author HP
 */
public class Datos
{
    private String cve;
    private String nom;
    private String primerAp;
    private String segundoAp;
    private char sexo;
    private boolean desnutricion;
    private boolean sobrepeso;
    private boolean alergias;
    private boolean obesidad;
    private boolean diabetes;
    private String otras;

    public Datos()
    {
    }

    public Datos(String cve, String nom, String primerAp, String segundoAp, char sexo, boolean desnutricion, boolean sobrepeso, boolean alergias, boolean obesidad, boolean diabetes, String otras)
    {
        this.cve = cve;
        this.nom = nom;
        this.primerAp = primerAp;
        this.segundoAp = segundoAp;
        this.sexo = sexo;
        this.desnutricion = desnutricion;
        this.sobrepeso = sobrepeso;
        this.alergias = alergias;
        this.obesidad = obesidad;
        this.diabetes = diabetes;
        this.otras = otras;
    }

    /**
     * @return the cve
     */
    public String getCve()
    {
        return cve;
    }

    /**
     * @param cve the cve to set
     */
    public void setCve(String cve)
    {
        this.cve = cve;
    }

    /**
     * @return the nom
     */
    public String getNom()
    {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom)
    {
        this.nom = nom;
    }

    /**
     * @return the primerAp
     */
    public String getPrimerAp()
    {
        return primerAp;
    }

    /**
     * @param primerAp the primerAp to set
     */
    public void setPrimerAp(String primerAp)
    {
        this.primerAp = primerAp;
    }

    /**
     * @return the segundoAp
     */
    public String getSegundoAp()
    {
        return segundoAp;
    }

    /**
     * @param segundoAp the segundoAp to set
     */
    public void setSegundoAp(String segundoAp)
    {
        this.segundoAp = segundoAp;
    }

    /**
     * @return the sexo
     */
    public char getSexo()
    {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(char sexo)
    {
        this.sexo = sexo;
    }

    /**
     * @return the desnutricion
     */
    public boolean isDesnutricion()
    {
        return desnutricion;
    }

    /**
     * @param desnutricion the desnutricion to set
     */
    public void setDesnutricion(boolean desnutricion)
    {
        this.desnutricion = desnutricion;
    }

    /**
     * @return the sobrepeso
     */
    public boolean isSobrepeso()
    {
        return sobrepeso;
    }

    /**
     * @param sobrepeso the sobrepeso to set
     */
    public void setSobrepeso(boolean sobrepeso)
    {
        this.sobrepeso = sobrepeso;
    }

    /**
     * @return the alergias
     */
    public boolean isAlergias()
    {
        return alergias;
    }

    /**
     * @param alergias the alergias to set
     */
    public void setAlergias(boolean alergias)
    {
        this.alergias = alergias;
    }

    /**
     * @return the obesidad
     */
    public boolean isObesidad()
    {
        return obesidad;
    }

    /**
     * @param obesidad the obesidad to set
     */
    public void setObesidad(boolean obesidad)
    {
        this.obesidad = obesidad;
    }

    /**
     * @return the diabetes
     */
    public boolean isDiabetes()
    {
        return diabetes;
    }

    /**
     * @param diabetes the diabetes to set
     */
    public void setDiabetes(boolean diabetes)
    {
        this.diabetes = diabetes;
    }

    /**
     * @return the otras
     */
    public String getOtras()
    {
        return otras;
    }

    /**
     * @param otras the otras to set
     */
    public void setOtras(String otras)
    {
        this.otras = otras;
    }
    
}
