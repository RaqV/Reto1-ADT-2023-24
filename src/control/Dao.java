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
    public UnidadDidactica consultarUnidadDidactica(UnidadDidactica u) throws DaoException;
    public ConvocatoriaExamen consultarConvocatoria(ConvocatoriaExamen c) throws DaoException;
    public Enunciado consultarEnunciado(Enunciado e)throws DaoException;
    public boolean asignarEnunciadoAConvocatoria(Enunciado e, ConvocatoriaExamen c) throws DaoException; 
    public List<ConvocatoriaExamen> consultarConvocatoriasE(Enunciado e)throws DaoException;
}