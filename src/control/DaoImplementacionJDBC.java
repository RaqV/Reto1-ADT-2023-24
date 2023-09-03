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
     /*
    final String OBTENERcte = "SELECT * FROM customer WHERE id = ?" ;
    final String OBTENERcta = "SELECT * FROM account WHERE id = ?" ;
    final String INSERTcta = "INSERT INTO account(balance, beginBalance, beginBalanceTimestamp, creditLine, description, type) VALUES (?, ?, ?, ?, ?, ?)";
    final String INSERTctaClte = "INSERT INTO customer_account (customers_id, accounts_id) VALUES (?, ?)";
    final String OBTENERctasClte = "SELECT * FROM account WHERE id IN (SELECT accounts_id FROM customer_account WHERE customers_id = ?)" ;
    final String INSERTmov = "INSERT INTO movement (amount, balance, description, timestamp, account_id) VALUES (?, ?, ?, ?, ?)";
    final String OBTEMERmovCta = "SELECT * FROM movement WHERE account_id = ?";
    */
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
    
//    /**
//     * Inserta en la base de datos un nuevo cliente.
//     * @param c Los datos del cliente.
//    */
//    
//    public void crearCliente(Customer c) throws DaoException {   
//      
//        ResultSet rs = null;
//        
//        try{
//            //Ejecutamos el alta
//            this.conectar();
//            
//            //El stat.RETURN_GENERATED_KEYS es para que me devuelva la clave autómatica que se crea para el clte
//            stat = con.prepareStatement(INSERTcte, stat.RETURN_GENERATED_KEYS);
//            
//            //Meto los valores de los datos del cliente dentro del stat:
//            stat.setString(1, c.getCity());
//            stat.setString(2, c.getEmail());
//            stat.setString(3, c.getFirstName());
//            stat.setString(4, c.getLastName());
//            stat.setString(5, c.getMiddleInitial());
//            stat.setLong(6, c.getPhone());
//            stat.setString(7, c.getState());
//            stat.setString(8, c.getStreet());
//            stat.setInt(9, c.getZip());
//            
//            if (stat.executeUpdate()== 0){
//                 throw new DaoException("Puede que no se haya guardato");
//            };
//            
//            //Queremos obtener con que clave se ha guardado el cliente
//            rs =stat.getGeneratedKeys();
//            if(rs.next()){
//                c.setId(rs.getLong(1));
//            }else {
//                throw new DaoException("No puedo asignar ID a este cliente");
//            }
//            
//            //Desconexión
//            this.desconectar();
//        } catch (SQLException e){
//            throw new DaoException("ERROR AL CREAR CLIENTE"+e.getMessage());
//        } catch (Exception e1) {
//            throw new DaoException("ERROR "+e1.getMessage());
//        } finally {
//            //Cerramos ResultSet
//            if (rs != null) {
//                try {
//                    rs.close();
//                }catch (SQLException ex) {
//                    new DaoException ("Error en SQL", ex);
//                }
//            }
//        }
//    }
//    
//    /**
//     * Devuelve los datos de un determinado cliente.
//     * @param id El identificador del cliente.
//     * @return Los datos del cliente.
//    */
//    public Customer consultarDatosCliente(long id) throws DaoException { 
//        
//        ResultSet rs = null;
//        Customer clte;
//        
//        try{
//            this.conectar();
//            
//            //String select = "select * from customer where id="+id;
//            stat = con.prepareStatement(OBTENERcte);
//            //stat=this.con.prepareStatement(select);
//            
//            stat.setLong(1, id);
//            
//            rs = stat.executeQuery();
//            
//            if (rs.next()){
//                clte = convertirClte(rs);
//            }else
//                throw new DaoException ("No se ha encontrado el clte ");
//            
//            this.desconectar();
//        }catch (SQLException e){
//            throw new DaoException ("Error de SQL "+ e.getMessage());
//        }catch (Exception e1){
//            throw new DaoException ("ERROR " + e1.getMessage());
//        }finally{
//            //Cerramos ResultSet
//            if (rs != null) {
//                try {
//                    rs.close();
//                }catch (SQLException ex) {
//                    new DaoException ("Error en SQL", ex);
//                }
//            }
//        }
//        return clte;
//    }
//
//    public Customer convertirClte(ResultSet rs) throws DaoException{
//        try {
//            String city = rs.getString("city");
//            String email = rs.getString("email");
//            String firstName = rs.getString("firstName");
//            String lastName = rs.getString("lastName");
//            String middleInitial = rs.getString("middleInitial");
//            Long phone = rs.getLong("phone");
//            String state = rs.getString("state");
//            String street = rs.getString("street");
//            int zip = rs.getInt("zip");
//            Long id = rs.getLong("id"); 
//            Customer clte = new Customer (id, city, email, firstName, lastName, middleInitial, phone, state, street, zip);
//              
//            return clte;
//        } catch (SQLException ex) {
//            throw new DaoException ("Error SQL "+ ex.getMessage());
//        }
//    }
//    
//    /**
//     * Verifica si existe un cliente o no.
//     * @param id El identificador de cada cliente
//     * @return existe un booleano que dice si existe:TRUE o sino existe:FALSE.
//    */
//    public boolean existeClte(Long id) throws DaoException {  
//        boolean existe = false;
//        ResultSet rs = null;
//        
//        try{
//            this.conectar();
//            
//            stat = con.prepareStatement(OBTENERcte);
//            
//            stat.setLong(1, id);
//            
//            rs = stat.executeQuery();
//            
//            if (rs.next()){
//                existe = true;
//            }else
//                System.out.println("No se ha encontrado el cliente");
//            this.desconectar();
//        }catch (SQLException e){
//            throw new DaoException ("Error de SQL "+ e.getMessage());
//        }catch (Exception e1){
//            //throw new DaoException ("ERROR " + e1.getMessage());
//        }finally{
//            //Cerramos ResultSet
//            if (rs != null) {
//                try {
//                    rs.close();
//                }catch (SQLException ex) {
//                    new DaoException ("Error en SQL", ex);
//                }
//            }
//        }
//        return existe;
//    }
//    
//    /**
//     * Verifica si existe la cuenta o no.
//     * @param id Es el identificador de cada cuenta.
//     * @return existe un booleano dependiendo si existe:TRUE o sino
//     * existe:FALSE.
//     */
//    public boolean existeCta(Long id) throws DaoException {  
//        boolean existe = false;
//        ResultSet rs = null;
//        
//        try{
//            this.conectar();
//            
//            stat = con.prepareStatement(OBTENERcta);
//            
//            stat.setLong(1, id);
//            
//            rs = stat.executeQuery();
//            
//            if (rs.next()){
//                existe = true;
//            }else
//                System.out.println("No se ha encontrado la cuenta");
//            this.desconectar();
//        }catch (SQLException e){
//            throw new DaoException ("Error de SQL "+ e.getMessage());
//        }catch (Exception e1){
//            //throw new DaoException ("ERROR " + e1.getMessage());
//        }finally{
//            //Cerramos ResultSet
//            if (rs != null) {
//                try {
//                    rs.close();
//                }catch (SQLException ex) {
//                    new DaoException ("Error en SQL", ex);
//                }
//            }
//        }
//        return existe;
//    }
//    
//    /**
//     * Retorna las cuentas de un cliente en particular.
//     * @param idCliente El identificador del cliente.
//     * @return Retorna una lista de cuentas.
//    */
//    public ArrayList<Account> consultarCuentasCliente(Long idClte) throws DaoException { 
//       
//        ResultSet rs = null;
//        ArrayList<Account> ctas = new ArrayList<>();
//            
//        try{
//            this.conectar();
//            
//            stat = con.prepareStatement(OBTENERctasClte);
//            stat.setLong(1, idClte);
//            
//            rs = stat.executeQuery();
//            
//            while (rs.next()){
//                ctas.add(convertirCta(rs));
//            }   
//           
//            this.desconectar();
//         }catch (SQLException e){
//            throw new DaoException ("Error de SQL "+ e.getMessage());
//        }catch (Exception e1){
//            throw new DaoException ("ERROR " + e1.getMessage());
//        }finally{
//            //Cerramos ResultSet
//            if (rs != null) {
//                try {
//                    rs.close();
//                }catch (SQLException ex) {
//                    new DaoException ("Error en SQL", ex);
//                }
//            }
//        }
//        return ctas;
//    }
//    
//    public Account convertirCta(ResultSet rs) throws DaoException{
//        try {
//            Double balance = rs.getDouble("balance");
//            Double beginBalance = rs.getDouble("beginBalance");
//            Timestamp fecha = rs.getTimestamp("beginBalanceTimestamp");
//            Double creditLine = rs.getDouble("creditLine");
//            String description = rs.getString("description");
//            AccountType type = null;
//            for (AccountType t :AccountType.values()){
//                if (t.ordinal()==rs.getInt("type")){
//                    type = t;
//                }
//            }
//            Long id = rs.getLong("id");
//            Account cta = new Account(id, balance, beginBalance, fecha, creditLine, description, type);
//        
//        return cta;
//        } catch (SQLException ex) {
//            throw new DaoException ("Error SQL "+ ex.getMessage());
//        }
//    }
//    
//    /**
//     * Permite insertar una nueva cuenta.
//     * @param cta Los datos de la cuenta.
//     * @param idCliente El identificador del cliente.
//     */
//    public void crearCuentaCliente(Long idClte, Account cta) throws DaoException {  
//        ResultSet rs = null;
//        
//        try{
//            //Ejecutamos el alta
//            this.conectar();
//            
//            //Insertamoe la cuenta en Account
//            //El stat.RETURN_GENERATED_KEYS es para que me devuelva la clave autómatica que se crea para el clte
//            stat = con.prepareStatement(INSERTcta, stat.RETURN_GENERATED_KEYS);
//            
//            //Meto los valores de los datos de la cta dentro del stat:
//            stat.setDouble(1, cta.getBalance());
//            stat.setDouble(2, cta.getBeginBalance());
//            stat.setTimestamp(3, cta.getBeginBalanceTimestamp());
//            stat.setDouble(4, cta.getCreditLine());
//            stat.setString(5, cta.getDescription());
//            stat.setInt(6, cta.getTypeInt());
//          
//            
//            if (stat.executeUpdate()== 0){
//                 throw new DaoException("Puede que no se haya guardato");
//            }else {
//                //Insertamos la relación entre cta y clte
//                 //Queremos obtener con que clave se ha guardado la cta
//                rs =stat.getGeneratedKeys();
//                if(rs.next()){
//                    cta.setId(rs.getLong(1));
//                
//                    this.desconectar();
//                    this.conectar();
//                    //Insertamoe la cuenta-client
//                    stat = con.prepareStatement(INSERTctaClte);
//
//                    //Meto los valores de los datos 
//                    stat.setLong(1, idClte);
//                    stat.setLong(2, cta.getId());
//
//                    if (stat.executeUpdate()== 0){
//                        throw new DaoException("Puede que no se haya guardado");
//                    }
//                    
//                } else {
//                    throw new DaoException("No puedo asignar ID a esta cuenta");
//                }
//            };
//  
//            //Desconexión
//            this.desconectar();
//        } catch (SQLException e){
//            throw new DaoException("ERROR AL CREAR CTA"+e.getMessage());
//        } catch (Exception e1) {
//            throw new DaoException("ERROR "+e1.getMessage());
//        } finally {
//            //Cerramos ResultSet
//            if (rs != null) {
//                try {
//                    rs.close();
//                }catch (SQLException ex) {
//                    new DaoException ("Error en SQL", ex);
//                }
//            }
//        }
//    }
//
//    /**
//     * Agrega un cliente en una cuenta ya existente.
//     * @param idClte El identificador del cliente.
//     * @param idCta El idedntificador de la cuenta.
//    */
//    public void agregarClienteCuenta(Long idCta, Long idClte) throws DaoException {
//        ResultSet rs = null;
//        
//        try{
//            //Ejecutamos el alta
//            this.conectar();
//           
//            //Insertamos la cuenta-cliente
//            stat = con.prepareStatement(INSERTctaClte);
//
//            //Meto los valores de los datos 
//            stat.setLong(1, idClte);
//            stat.setLong(2, idCta);
//
//            if (stat.executeUpdate()== 0){
//                throw new DaoException("Puede que no se haya guardado");
//            }
//           
//            //Desconexión
//            this.desconectar();
//        } catch (SQLException e){
//            throw new DaoException("ERROR AL CREAR CTA-CLTE"+e.getMessage());
//        } catch (Exception e1) {
//            throw new DaoException("ERROR "+e1.getMessage());
//        } finally {
//            //Cerramos ResultSet
//            if (rs != null) {
//                try {
//                    rs.close();
//                }catch (SQLException ex) {
//                    new DaoException ("Error en SQL", ex);
//                }
//            }
//        }
//        
//    }
//    
//    /**
//     * Retorna los datos de una determinada cuenta.
//     * @param id El identificador de una cuenta.
//     * @return Los datos de una cuenta.
//    */
//    public Account consultarDatosCuenta(Long id) throws DaoException {
//        Account cta = new Account();
//        ResultSet rs = null;
//        
//        try{
//            this.conectar();
//            
//            stat = con.prepareStatement(OBTENERcta);
//            
//            stat.setLong(1, id);
//            
//            rs = stat.executeQuery();
//            
//            if (rs.next()){
//                cta = convertirCta(rs);
//            }else
//                System.out.println("No se ha encontrado la cuenta");
//            
//            this.desconectar();
//        
//        }catch (SQLException e){
//            throw new DaoException ("Error de SQL "+ e.getMessage());
//        }catch (Exception e1){
//            //throw new DaoException ("ERROR " + e1.getMessage());
//        }finally{
//            //Cerramos ResultSet
//            if (rs != null) {
//                try {
//                    rs.close();
//                }catch (SQLException ex) {
//                    new DaoException ("Error en SQL", ex);
//                }
//            }
//        }
//        
//        return cta;
//    }
//
//     /**
//     * Inserta en la base de datos un nuevo movimiento.
//     * @param mov Los datos de un movimiento.
//     * @param idCta El identificador de la cuenta
//     */
//    public void realizarMovimiento(Long idCta, Movement mov) throws DaoException {  
//        ResultSet rs = null;
//        
//        try{
//            this.conectar();
//            
//            stat = con.prepareStatement(INSERTmov, stat.RETURN_GENERATED_KEYS);
//            
//            stat.setDouble(1, mov.getAmount());
//            stat.setDouble(2, mov.getBalance());
//            stat.setString(3, mov.getDescription());
//            stat.setTimestamp(4, mov.getTimestamp());
//            stat.setLong(5, idCta);     
//            
//            rs = stat.executeQuery();
//            
//            if (rs.next()){
//                mov.setId(rs.getLong(1));
//                System.out.println("El movimiento generado es el siguiente: ");
//                mov.getDatos();
//            }
//            this.desconectar();
//            
//        }catch (SQLException e){
//            throw new DaoException ("Error de SQL "+ e.getMessage());
//        }catch (Exception e1){
//            //throw new DaoException ("ERROR " + e1.getMessage());
//        }finally{
//            //Cerramos ResultSet
//            if (rs != null) {
//                try {
//                    rs.close();
//                }catch (SQLException ex) {
//                    new DaoException ("Error en SQL", ex);
//                }
//            }
//        }
//        
//        try {
//            //AHora actualizar el balance de la cuenta coger la descripcion para sumar o restar balance cuenta amoount
//            actualizarDatosDeUnaCuenta(idCta,mov.getId());
//        } catch (Exception ex) {
//            Logger.getLogger(DaoImplementacionJDBC.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private void actualizarDatosDeUnaCuenta(long idCuenta, long idMovimiento) throws Exception{
//        
//        String update,descripcionDelMovimiento = " ";
//        double cantidadTransferidaMovimiento = 0;
//        
//        
//        String selectMovimiento = "Select * from movement where id = ?";
//        
//        this.conectar();
//        
//        stat = con.prepareStatement(selectMovimiento);
//        stat.setLong(1,idMovimiento);
//        ResultSet rs = stat.executeQuery();
//        
//        if(rs.next()){
//            descripcionDelMovimiento = rs.getString("description");
//            cantidadTransferidaMovimiento = rs.getDouble("amount");
//        }
//        
//        if(descripcionDelMovimiento.equalsIgnoreCase("Payment")){
//            update = "Update account set balance = balance - ? where id = ?";
//        }else
//           update = "Update account set balance = balance + ? where id = ?";
//        stat = con.prepareStatement(update);
//        stat.setDouble(1,cantidadTransferidaMovimiento);
//        stat.setLong(2, idCuenta);
//       
//        stat.executeUpdate();
//        this.desconectar();
//    }
//    /**
//     * Retorna los datos de todos los movimientos relacionados a una cuenta.
//     * @param id El identificador de una cuenta.
//     * @return Una lista de los datos de los movimientos.
//     */
//    public ArrayList<Movement> consultarMovimientoCuenta(Long id) throws DaoException {
//        ArrayList<Movement> movimientos = null;
//        
//        ResultSet rs = null;
//        
//        try{
//            this.conectar();
//            
//            stat = con.prepareStatement(OBTEMERmovCta);
//            
//            stat.setLong(1, id);     
//            
//            rs = stat.executeQuery();
//            
//            while (rs.next()){
//                movimientos.add(convertirMov(rs));
//            }
//            this.desconectar();
//            
//        }catch (SQLException e){
//            throw new DaoException ("Error de SQL "+ e.getMessage());
//        }catch (Exception e1){
//            //throw new DaoException ("ERROR " + e1.getMessage());
//        }finally{
//            //Cerramos ResultSet
//            if (rs != null) {
//                try {
//                    rs.close();
//                }catch (SQLException ex) {
//                    new DaoException ("Error en SQL", ex);
//                }
//            }
//        }
//        return movimientos;
//    }   
//    
//    public Movement convertirMov(ResultSet rs) throws DaoException{
//        try {
//            Double balance = rs.getDouble("balance");
//            Double amount = rs.getDouble("amount");
//            Timestamp fecha = rs.getTimestamp("timestamp");
//            String description = rs.getString("description");
//            Long cta = rs.getLong("account_id");
//            Long id = rs.getLong("id");
//            Movement mov = new Movement(id, amount, balance, description, fecha, cta);
//        
//        return mov;
//        } catch (SQLException ex) {
//            throw new DaoException ("Error SQL "+ ex.getMessage());
//        }
//    }

    @Override
    public boolean crearUnidadDidactica(UnidadDidactica u) throws DaoException {
    
        Boolean correcto = true;
       
        try{
            //Ejecutamos el alta
            this.conectar();        

            stat = con.prepareStatement(INSERTunidad);
           
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Enunciado> consultarEnunciados(UnidadDidactica u) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ConvocatoriaExamen> consultarConvocatorias(Enunciado e) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Enunciado consultarEnuciado(Enunciado e) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void asignarEnunciadoAConvocatoria(Enunciado e) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
