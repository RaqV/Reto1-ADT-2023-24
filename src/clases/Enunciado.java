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
  
    private Integer id;
    
    private String descripcion;
    
    private Dificultad dificultad;
    
    private boolean disponibleClase;
    
    private String ruta;
    
    //Relaciones
    private List<Unidad> unidades = new ArrayList<>();
    
    private List<ConvocatoriaExamen> convocatorias = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
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

    public List<Unidad> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidad> unidades) {
        this.unidades = unidades;
    }

    public List<ConvocatoriaExamen> getConvocatorias() {
        return convocatorias;
    }

    public void setConvocatorias(List<ConvocatoriaExamen> convocatorias) {
        this.convocatorias = convocatorias;
    }
    
}
