 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import clases.ConvocatoriaExamen;
import clases.Enunciado;
import clases.UnidadDidactica;
import exception.DaoException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rvalv
 */
public interface Dao {
    public boolean crearUnidadDidactica(UnidadDidactica u) throws DaoException; 
    public boolean crearConvocatoria(ConvocatoriaExamen c) throws DaoException;
    public boolean crearEnunciado(Enunciado e) throws DaoException;
    public List<Enunciado> consultarEnunciados(UnidadDidactica u) throws DaoException;
    public List<ConvocatoriaExamen> consultarConvocatorias(Enunciado e) throws DaoException;
    public Enunciado consultarEnuciado(Enunciado e)throws DaoException;
    public void asignarEnunciadoAConvocatoria(Enunciado e) throws DaoException; 
}