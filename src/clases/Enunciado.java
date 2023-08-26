/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;

/**
 *
 * @author rvalv
 */
public class Enunciado implements Serializable
{
    private static final long serialVersionUID = 1L;
  
    private Long id;
    
    private String descripcion;
    
    private Dificultad dificultad;
    
    private boolean disponibleClase;

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
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
