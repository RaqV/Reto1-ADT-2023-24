/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import clases.*;
import exception.DaoException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


/**
 *
 * @author rvalv
 */
public class Controlador {
    
    private final Dao daoBd = DaoFactory.getDaoBd();
    private final Dao daoFi = DaoFactory.getDaoFi();
    
     public boolean crearUnidadDidactica(UnidadDidactica unidad) throws DaoException{
        
        return daoBd.crearUnidadDidactica(unidad);
       
    }
     
    public boolean crearConvocatoriaExamen(ConvocatoriaExamen convocatoria) throws DaoException {
        
        return daoFi.crearConvocatoria(convocatoria);
    }
    
    public boolean crearEnunciado(Enunciado enunciado, ConvocatoriaExamen convocatoria) throws DaoException {
        if(daoFi.asignarEnunciadoAConvocatoria(enunciado, convocatoria)){
            return daoBd.crearEnunciado(enunciado);
        }
        return false;
    }

    public void consultarEnunciadosPorUD() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean visualizarEnunciado(Enunciado enunciado) throws DaoException, IOException {
        
        enunciado = this.consultarEnunciado(enunciado);
        
        if (enunciado != null){
            File fileToPrint = new File(enunciado.getRuta());
            Desktop.getDesktop().open(fileToPrint);
            
            return true;
        } 
        return false;
    }

    public void asignarEnunciadoAConvocatoria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Enunciado consultarEnunciado(Enunciado enunciado) throws DaoException {
        return daoBd.consultarEnunciado(enunciado);
    }

    public UnidadDidactica consultarUnidadDidactica(UnidadDidactica unidad) throws DaoException {
        return daoBd.consultarUnidadDidactica(unidad);
    }

    public ConvocatoriaExamen consultarConvocatoriaExamen(ConvocatoriaExamen convocatoria) throws DaoException {
        return daoFi.consultarConvocatoria(convocatoria);
    }
}
