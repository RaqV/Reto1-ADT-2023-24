/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rvalv
 */
public class Enunciado implements Serializable
{
    private static final long serialVersionUID = 1L;
  
    private long id;
    
    private String descripcion;
    
    private Dificultad nivel;
    
    private boolean disponibleClase;
    
    private String ruta;
    
    //Relaciones
    private List<UnidadDidactica> unidades = new ArrayList<>();
    
    //private List<ConvocatoriaExamen> convocatorias = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Dificultad getNivel() {
        return nivel;
    }

    public Integer getNivelI() {
        return nivel.ordinal();
    }
    
    public void setNivel(Dificultad nivel) {
        this.nivel = nivel;
    }

     public void setNivel(Integer nivel) {
      
            for (Dificultad t :Dificultad.values()){
                if (t.ordinal()==nivel){
                    this.nivel = t;
                }
            }
    }
     
    public boolean isDisponibleClase() {
        return disponibleClase;
    }

    public void setDisponibleClase(boolean disponibleClase) {
        this.disponibleClase = disponibleClase;
    }
    
      public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public List<UnidadDidactica> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadDidactica> unidades) {
        this.unidades = unidades;
    }

//    public List<ConvocatoriaExamen> getConvocatorias() {
//        return convocatorias;
//    }
//
//    public void setConvocatorias(List<ConvocatoriaExamen> convocatorias) {
//        this.convocatorias = convocatorias;
//    }
    
}
