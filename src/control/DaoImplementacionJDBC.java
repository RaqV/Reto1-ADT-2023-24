 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import clases.ConvocatoriaExamen;
import clases.Dificultad;
import clases.Enunciado;
import clases.UnidadDidactica;
import exception.DaoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Esta clase es la implementación de la interfaz DAO, que permite la conexión
 * con nuestra base de datos.
 * @author rvalv
 */
public class DaoImplementacionJDBC implements Dao {
    //Atributos
    private Connection con = null;
    //private Properties props;
    private PreparedStatement stat = null;
    
    //Los siguientes atributos se utilizan para recoger los valores del fich de configuración
    private ResourceBundle configFile;
    private String driverBD;
    private String urlBD;
    private String userBD;
    private String passwordBD;
    
   
   
     //Sentencias SQL
    final String INSERTunidad = "INSERT INTO unidad(acronimo, titulo, evaluacion, descripcion) VALUES( ?, ?, ?, ?)";
    final String INSERTenunciado = "INSERT INTO enunciado(descripcion, nivel, disponible, ruta) VALUES( ?, ?, ?, ?)";
    final String INSERTenuciadoUnidadD = "INSERT INTO unidad_enunciado (unidad_id, enunciado_id) VALUES (?, ?)";
    
    final String OBTENERUnidad = "SELECT * FROM unidad WHERE acronimo = ?" ;
    final String OBTENEREnunciado = "SELECT * FROM enunciado WHERE id = ?" ;
    final String OBTENEREnunciadosUnidad = "SELECT * FROM enunciado WHERE id = (SELECT enunciado_id FROM unidad_enunciado WHERE unidad_id = ?)"; 
    
    public DaoImplementacionJDBC() {
        
        /* Podemos hacer la conexión directamente o mediante un fich de conexión
        this.props = new Properties();
        props.put("user", "root");
        props.put("password", "");
        */        
        //Para la conexión utilizamos un fichero de configuaración, config que guardamos en el paquete control:
        this.configFile = ResourceBundle.getBundle("control.config");
        this.driverBD = this.configFile.getString("Driver");
        this.urlBD = this.configFile.getString("Conn");
        this.userBD = this.configFile.getString("DBUser");
        this.passwordBD = this.configFile.getString("DBPass");
       
    }
     /**
     * Permite abrir la conexión con la base de datos.
     */
     private void conectar() throws DaoException {
         try{
             //El siguiente driver no se puede utilizar por que es para un conector de versión superior
             //Class.forName("com.mysql.cj.jdbc.Driver");
             Class.forName(this.driverBD);
             con=DriverManager.getConnection(this.urlBD, this.userBD, this.passwordBD);
        }catch(SQLException e){
            throw new DaoException("Error de SQL al conectar"+ e.getMessage());
       }catch (ClassNotFoundException e1) {
            throw new DaoException("No se ha encontrado el driver para la conexión"+ e1.getMessage());
        }
        /* CONTROLAR LAS EXCEPCIONES CON UN DaoException o LOGGER
            }catch (Exception e3){
            throw new DaoException("ERROR"+e3.getMessage())
        /* CONTROLAR LAS EXCEPCIONES CON UN DaoException o LOGGER
            }catch (Exception e3){
            throw new DaoException("ERROR"+e3.getMessage())
        /* CONTROLAR LAS EXCEPCIONES CON UN DaoException o LOGGER
            }catch (Exception e3){
            throw new DaoException("ERROR"+e3.getMessage())
        /* CONTROLAR LAS EXCEPCIONES CON UN DaoException o LOGGER
            }catch (Exception e3){
            throw new DaoException("ERROR"+e3.getMessage())
        } */
    }
    
    /**
     * Cierra la conexión con la bases de datos.
    */
    private void desconectar() throws DaoException {
        try{
            if (stat != null) {
                stat.close();
            }
            if (con != null) {
                con.close();
            }
        }catch(SQLException e){
            throw new DaoException ("Error de SQL "+ e.getMessage()); 
        }
    }
    
//  


    @Override
    public boolean crearUnidadDidactica(UnidadDidactica u) throws DaoException {
    
        Boolean correcto = true;
        //ResultSet rs = null;
       
        try{
            //Ejecutamos el alta
            this.conectar();        

             //El stat.RETURN_GENERATED_KEYS es para que me devuelva la clave autómatica que se crea para el clte
            stat = con.prepareStatement(INSERTunidad, stat.RETURN_GENERATED_KEYS);
           
           //Meto los valores de los datos del cliente dentro del stat:
           stat.setString(1, u.getAcronimo());
           stat.setString(2, u.getTitulo());
           stat.setString(3, u.getEvaluacion());
           stat.setString(4, u.getDescripcion());         
//            
            if (stat.executeUpdate()== 0){
//                 throw new DaoException("Puede que no se haya guardato");
                correcto = false;
            };
//      
       
       }catch (SQLException e){
           throw new DaoException("ERROR AL CREAR Unidad"+e.getMessage());
        } catch (Exception e1) {
            throw new DaoException("ERROR "+e1.getMessage());
       } 
        this.desconectar();
        return correcto;
    }

    @Override
    public boolean crearConvocatoria(ConvocatoriaExamen c) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean crearEnunciado(Enunciado e) throws DaoException {
         Boolean correcto = true;
        ResultSet rs = null;
       
        try{
            //Ejecutamos el alta
            this.conectar();        

             //El stat.RETURN_GENERATED_KEYS es para que me devuelva la clave autómatica que se crea para el clte
            stat = con.prepareStatement(INSERTenunciado, stat.RETURN_GENERATED_KEYS);
           
           //Meto los valores de los datos del cliente dentro del stat:
           stat.setString(1, e.getDescripcion());
           stat.setString(2, e.getNivel().toString());
           stat.setInt(3, (e.isDisponibleClase() ? 1 : 0));
           stat.setString(4, e.getRuta());         
            
            //Queremos obtener con que clave se ha guardado el cliente
            if (stat.executeUpdate()== 0){
//                 throw new DaoException("Puede que no se haya guardato");
                return(false);
            };
            
            rs =stat.getGeneratedKeys();
            if(rs.next()){
                e.setId(rs.getLong(1));
                
                 //Ahora guardamos las relaciones de Enunciado con las UD
                for(UnidadDidactica unidad:e.getUnidades()){
                     stat = con.prepareStatement(INSERTenuciadoUnidadD);

                     stat.setLong(1, unidad.getId());
                     stat.setLong(2, e.getId());
                     
                      if (stat.executeUpdate()== 0){
//                        throw new DaoException("Puede que no se haya guardato");
                          return(false);
                      }
                }     
            }else {
                correcto = false;
                throw new DaoException("No puedo asignar ID a este cliente");
            }
    
       }catch (SQLException ex){
           throw new DaoException("ERROR AL CREAR Unidad"+ex.getMessage());
        } catch (Exception e1) {
            throw new DaoException("ERROR "+e1.getMessage());
       } 
        this.desconectar();
        return correcto;
    }

  

   

    @Override
    public Enunciado consultarEnunciado(Enunciado e) throws DaoException {
        ResultSet rs = null;
        Enunciado enun = null;
        
        try{
            this.conectar();
            
            stat = con.prepareStatement(OBTENEREnunciado);
          
            stat.setLong(1, e.getId());
            
            rs = stat.executeQuery();
            
            if (rs.next()){
                enun = new Enunciado();
                enun.setId(rs.getLong(1));
                enun.setDescripcion(rs.getString(2));
                enun.setNivel(Dificultad.valueOf(rs.getString(3)));
                enun.setDisponibleClase((rs.getInt(4)==1 ? true: false));
                enun.setRuta(rs.getString(5));
               
                // Ahora cargamos las Unides Didácticas de los enunciados
                /*
                stat = con.prepareStatement(OBTENEREnunciadosUnidad);
                
                stat.setLong(1, unidad.getId());
                
                rs = stat.executeQuery();
            
                while(rs.next()){
                    enunciado = new Enunciado();
                    enunciado.setId(rs.getLong(1));
                    enunciado.setDescripcion(rs.getString(2));
                    enunciado.setNivel(Dificultad.valueOf(rs.getString(3)));
                    enunciado.setDisponibleClase((rs.getInt(4)==1 ? true: false));
                    enunciado.setRuta(rs.getString(5));
                }
      */
            }else
                //throw new DaoException ("No se ha encontrado el Enunciado ");
           
            this.desconectar();
        }catch (SQLException ex){
            throw new DaoException ("Error de SQL "+ ex.getMessage());
        }catch (Exception e1){
            throw new DaoException ("ERROR " + e1.getMessage());
        }finally{
            //Cerramos ResultSet
            if (rs != null) {
                try {
                    rs.close();
                }catch (SQLException ex) {
                    new DaoException ("Error en SQL", ex);
                }
            }
        }
        return enun;
    }

    

    @Override
    public UnidadDidactica consultarUnidadDidactica(UnidadDidactica u) throws DaoException {
        ResultSet rs = null;
        UnidadDidactica unidad= null;
        Enunciado enunciado;
        
        try{
            this.conectar();
            
            stat = con.prepareStatement(OBTENERUnidad);
          
            stat.setString(1, u.getAcronimo());
            
            rs = stat.executeQuery();
            
            if (rs.next()){
                unidad = new UnidadDidactica();
                unidad.setId(rs.getLong(1));
                unidad.setAcronimo(rs.getString(2));
                unidad.setTitulo(rs.getString(3));
                unidad.setEvaluacion(rs.getString(4));
                unidad.setDescripcion(rs.getString(5));
               
                // Ahora cargamos los enunciados que tratan esta Unidad Didática
                stat = con.prepareStatement(OBTENEREnunciadosUnidad);
                
                stat.setLong(1, unidad.getId());
                
                rs = stat.executeQuery();
            
                while(rs.next()){
                    enunciado = new Enunciado();
                    enunciado.setId(rs.getLong(1));
                    enunciado.setDescripcion(rs.getString(2));
                    enunciado.setNivel(Dificultad.valueOf(rs.getString(3)));
                    enunciado.setDisponibleClase((rs.getInt(4)==1 ? true: false));
                    enunciado.setRuta(rs.getString(5));
                }
            }else
                //throw new DaoException ("No se ha encontrado la Unidad Didáctica ");
           
            this.desconectar();
        }catch (SQLException e){
            throw new DaoException ("Error de SQL "+ e.getMessage());
        }catch (Exception e1){
            throw new DaoException ("ERROR " + e1.getMessage());
        }finally{
            //Cerramos ResultSet
            if (rs != null) {
                try {
                    rs.close();
                }catch (SQLException ex) {
                    new DaoException ("Error en SQL", ex);
                }
            }
        }
        return unidad;
    }

    @Override
    public ConvocatoriaExamen consultarConvocatoria(ConvocatoriaExamen c) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean asignarEnunciadoAConvocatoria(Enunciado e, ConvocatoriaExamen c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ConvocatoriaExamen> consultarConvocatoriasE(Enunciado e) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

}
