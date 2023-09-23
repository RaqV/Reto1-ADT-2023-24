/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author rvalv
 */
public class ConvocatoriaExamen implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String convocatoria;
    
    private String descripcion;
    
    private LocalDate fecha;
    
    private String curso;
    
    private long idEnunciado;

    public long getIdEnunciado() {
        return idEnunciado;
    }

    public void setIdEnunciado(long idEnunciado) {
        this.idEnunciado = idEnunciado;
    }
    
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(String convocatoria) {
        this.convocatoria = convocatoria;
    }

    
}
