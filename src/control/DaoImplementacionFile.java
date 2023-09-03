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
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilidades.MiObjectOutputStream;
import static utilidades.Util.calculoFichero;

/**
 *
 * @author rvalv
 */
public class DaoImplementacionFile implements Dao{
    
    //Atributos
    private ResourceBundle configFile;
    private String rutaCuentas;
    private String rutaClientes;
    private String rutaCtasCltes;
    private String rutaMovimientos;
    private String rutaCltesMovs;
    
    private File fichCuentas;
    private File fichClientes;
    private File fichCtasCltes;
    private File fichMovimientos;
    private File fichCltesMovs;

    public DaoImplementacionFile() {
        this.configFile = ResourceBundle.getBundle("control.configFich");
        this.rutaCuentas = this.configFile.getString("cuentas");
        this.rutaClientes = this.configFile.getString("clientes");
        this.rutaCtasCltes = this.configFile.getString("ctasCltes");
        this.rutaMovimientos = this.configFile.getString("movimientos");
        this.rutaCltesMovs = this.configFile.getString("cltesMovs");
        
        this.fichCuentas = new File(this.rutaCuentas);
        this.fichClientes = new File(this.rutaClientes);
        this.fichCtasCltes= new File(this.rutaCtasCltes);
        this.fichMovimientos = new File(this.rutaMovimientos);
        this.fichCltesMovs = new File(this.rutaCltesMovs);   
    }
    
 
//    @Override
//    public void crearCliente(Customer c) throws DaoException {
//        MiObjectOutputStream moos = null;
//        ObjectOutputStream oos = null;
//        
//        //El id del clientes se calcula de forma automática
//        c.setId(calculoFichero(fichClientes)+1);
//        try {
//            if (fichClientes.exists()){
//                moos = new MiObjectOutputStream(new FileOutputStream(fichClientes, true));
//                moos.writeObject(c);
//            }else{
//                oos= new ObjectOutputStream(new FileOutputStream(fichClientes));
//                oos.writeObject(c);
//            }
//        } catch (FileNotFoundException e1) {
//            Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, e1);
//        } catch (IOException ex) {
//            Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception e1) {
//            throw new DaoException("ERROR "+e1.getMessage());
//        } finally {
//            if (moos!=null){
//                try {
//                    moos.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if (oos!=null){
//                try {
//                    oos.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            
//        }
//    }
//
//    @Override
//    public Customer consultarDatosCliente(long id) throws DaoException {
//        Customer clte = null;
//        FileInputStream fis = null;
//        ObjectInputStream ois = null;
//        boolean encontrado = false;
//        
//        if (fichClientes.exists()){
//            int numClientes = calculoFichero(fichClientes);
//            
//            try {
//                 ois = new ObjectInputStream (new FileInputStream(fichClientes));
//            
//                 for (int i=0; i<numClientes && !encontrado;i++){
//                    clte=(Customer) ois.readObject();
//   
//                    if (clte.getId()==id){
//                       // System.out.println("ENCONTRADO");
//                        encontrado = true;
//         
//                    }
//                }
//            } catch (FileNotFoundException ex) {
//                    Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                    Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//            } finally{
//                try {
//                    ois.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }  
//        }
//        if (encontrado){
//            return clte; 
//        }else{
//            return null;
//        }
//    }
//
//   
//    @Override
//    public boolean existeClte(Long id) throws DaoException {
//        ObjectInputStream ois = null;
//        Customer clte;
//        boolean encontrado = false;
//        int numClientes = calculoFichero(fichClientes);
//        
//        if (fichClientes.exists()){
//            try {
//                ois = new ObjectInputStream(new FileInputStream(fichClientes));
//
//                for (int i=0; i<numClientes && !encontrado;i++){
//                   clte = (Customer) ois.readObject(); 
//                   if (clte.getId() == id){
//                       encontrado=true;
//                   }
//                }
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//            }finally{
//                try {
//                    ois.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        return encontrado;
//    }
//
//    @Override
//    public boolean existeCta(Long id) throws DaoException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public List<Account> consultarCuentasCliente(Long idClte) throws DaoException {
//        
//        Map<Long,Account> ctas = new HashMap<>();
//        
//        if(fichCuentas.exists()){
//            ctas = obtenerCtasClte(idClte);
//            completarCtas(ctas);
//        }
//        
//        List<Account> cuentas = new ArrayList<>(ctas.values());  
//        return cuentas; 
//    }
//
//    @Override
//    public void crearCuentaCliente(Long idClte, Account cta) throws DaoException {
//        crearCuenta(cta);
//        crearCtaClte(idClte, cta);
//    }
//
//    @Override
//    public void agregarClienteCuenta(Long idCta, Long idClte) throws DaoException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Account consultarDatosCuenta(Long id) throws DaoException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void realizarMovimiento(Long idCta, Movement mov) throws DaoException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public ArrayList<Movement> consultarMovimientoCuenta(Long id) throws DaoException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Movement convertirMov(ResultSet rs) throws DaoException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    private void crearCuenta(Account cta) throws DaoException {
//        MiObjectOutputStream moos = null;
//        ObjectOutputStream oos = null;
//        
//        //El id del clientes se calcula de forma automática
//        cta.setId(calculoFichero(fichCuentas)+1);
//        try {
//            if (fichCuentas.exists()){
//                moos = new MiObjectOutputStream(new FileOutputStream(fichCuentas, true));
//                moos.writeObject(cta);
//            }else{
//                oos= new ObjectOutputStream(new FileOutputStream(fichCuentas));
//                oos.writeObject(cta);
//            }
//        } catch (FileNotFoundException e1) {
//            Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, e1);
//        } catch (IOException ex) {
//            Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception e1) {
//            throw new DaoException("ERROR "+e1.getMessage());
//        } finally {
//            if (moos!=null){
//                try {
//                    moos.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if (oos!=null){
//                try {
//                    oos.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            
//        }
//    }
//
//    private void crearCtaClte(Long idClte, Account cta) throws DaoException {
//        MiObjectOutputStream moos = null;
//        ObjectOutputStream oos = null;
//        
//        CustomerAccount ctaClte;
//        ctaClte = new CustomerAccount(idClte, cta.getId());
//        
//        try {
//            if (fichCtasCltes.exists()){
//                moos = new MiObjectOutputStream(new FileOutputStream(fichCtasCltes, true));
//                moos.writeObject(ctaClte);
//            }else{
//                oos= new ObjectOutputStream(new FileOutputStream(fichCtasCltes));
//                oos.writeObject(ctaClte);
//            }
//        } catch (FileNotFoundException e1) {
//            Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, e1);
//        } catch (IOException ex) {
//            Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception e1) {
//            throw new DaoException("ERROR "+e1.getMessage());
//        } finally {
//            if (moos!=null){
//                try {
//                    moos.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if (oos!=null){
//                try {
//                    oos.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            
//        }
//    } 
//
//    private Map<Long,Account> obtenerCtasClte(Long idClte) {
//        
//        ObjectInputStream ois = null;
//        CustomerAccount ctaClte;
//        Account cta;
//        
//        int numCuentas = calculoFichero(fichCtasCltes);
//        Map<Long,Account> cuentas = new HashMap<>();
//        
//        try {
//            ois = new ObjectInputStream(new FileInputStream(fichCtasCltes));
//            
//            for (int i=0; i<numCuentas;i++){
//               ctaClte = (CustomerAccount) ois.readObject(); 
//               if (ctaClte.getCustomers_id() == idClte){
//                   cta = new Account();
//                   cta.setId(ctaClte.getAccounts_id());
//                   cuentas.put(cta.getId(), cta);
//               }
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//        }finally{
//            try {
//                ois.close();
//            } catch (IOException ex) {
//                Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return cuentas;
//    }
//
//    private void completarCtas(Map<Long,Account> ctas) {
//        
//        ObjectInputStream ois = null;
//        Account cta;
//            
//        int numCuentas = calculoFichero(fichCuentas);
//        
//        try {
//            ois = new ObjectInputStream(new FileInputStream(fichCuentas));
//            
//            for (int i=0; i<numCuentas;i++){
//               cta= (Account) ois.readObject(); 
//               if (ctas.containsKey(cta.getId())){
//                   ctas.put(cta.getId(), cta);  
//               }
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//        }finally{
//            try {
//                ois.close();
//            } catch (IOException ex) {
//                Logger.getLogger(DaoImplementacionFile.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

    @Override
    public boolean crearUnidadDidactica(UnidadDidactica u) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        //VACIO 
    }

    @Override
    public boolean crearConvocatoria(ConvocatoriaExamen c) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean crearEnunciado(Enunciado e) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        //VACIO 
    }

    @Override
    public List<Enunciado> consultarEnunciados(UnidadDidactica u) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //VACIO 
    }

    @Override
    public List<ConvocatoriaExamen> consultarConvocatorias(Enunciado e) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //VACIO 
    }

    @Override
    public Enunciado consultarEnuciado(Enunciado e) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //VACIO 
    }

    @Override
    public void asignarEnunciadoAConvocatoria(Enunciado e) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        //VACIO 
    }
}
