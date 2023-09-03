/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import clases.UnidadDidactica;
import exception.DaoException;
import java.awt.Desktop;


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
    
      /*
    private static void consultarDatosCliente() throws DaoException {  
        Long id;
        Customer clte = new Customer();
        
        id = Util.leerLong("Introduce el identificador del Cliente que quieres consultar");
               
        clte = dao.consultarDatosCliente(id);
        
        if (clte==null){
            System.out.println("El Cliente no existe");
        }else{
            clte.getDatos();
        }
    }

    private static void consultarCuentasCliente() throws DaoException {     
        Long id;
        List<Account> ctas = new ArrayList<>();
        
        id = Util.leerLong("Introduce el identificador del Cliente del que quieres consultar las cuentas");
        
        if (dao.existeClte(id)){
            ctas = dao.consultarCuentasCliente(id);
       
            if (!ctas.isEmpty()){
                System.out.printf("Las cuentas del cliente %d son: %n", id);
                for(Account c: ctas){
                    c.getDatos();
                }   
            }else {
                System.out.printf("El cliente no tiene cuentas");
            }
        }else{
            System.out.println("No existe este Cliente");
        } 
        
        
    }

    private static void crearCuentaCliente() throws DaoException {   
        Account cta;
        boolean mas = true;
        
        Long idClte = Util.leerLong("Introduce el identificador del Cliente del que quieres insertar una cuentas");
    
        if (dao.existeClte(idClte)){
            do{
                cta = new Account();
                cta.setDatos();
                dao.crearCuentaCliente(idClte, cta);
                mas = Util.leerBoolean("¿Quieres introducir mas ctas?");
            }while (mas);
        }else{
            System.out.println("No existe este Cliente");
        }
            
    }

    private static void agregarClienteCuenta() throws DaoException {    
        
        Long idCta = Util.leerLong("Introduce el identificador de la Cuenta");
    
        if (dao.existeCta(idCta)){
            
            Long idClte = Util.leerLong("Introduce el identificador del Cliente");
            
            if (dao.existeClte(idClte)){
                dao.agregarClienteCuenta(idCta, idClte); 
            }
        }
    }

    private static void consultarDatosCuenta() throws DaoException {   
        Long idCta = Util.leerLong("Introduce el identificador de la Cuenta");
        
        if (dao.existeCta(idCta)){
            dao.consultarDatosCuenta(idCta).getDatos();  
        }
    }

    private static void realizarMovimiento() throws DaoException {  
        Movement mov = new Movement();
        
        Long idCta = Util.leerLong("Introduce el identificador de la Cuenta");
        
        if (dao.existeCta(idCta)){
            mov.setDatos();
            dao.realizarMovimiento(idCta, mov);
        }
    }

    private static void consultarMovimientoCuenta() throws DaoException {
        boolean pvez = true;
       
        List<Movement> movimientos = null;
        
        Long idCta = Util.leerLong("Introduce el identificador de la Cuenta");
        
        if (dao.existeCta(idCta)){
            for ( Movement mov : dao.consultarMovimientoCuenta(idCta)){
                if (pvez){
                    pvez = false;
                    System.out.printf("Los movimientos para la cuenta %d son: ", idCta);
                }
                mov.getDatos();
            }
        }
    }  */
     
      public void crearConvocatoriaExamen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void crearEnunciado() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void consultarEnunciados() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visualizarEnunciado() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
        
	    /*
		File fileToPrint = new File("C:\\Users\\rvalv\\OneDrive\\Documentos\\Ficha_DUAL_Tartanga_tutora.docx");
		Desktop.getDesktop().open(fileToPrint);
	*/
    }

    public void asignarEnunciadoAConvocatoria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void consultarConvocatoriasExamen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
