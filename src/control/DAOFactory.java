/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;


import java.util.ResourceBundle;

/**
 *
 * @author yeguo
 */
public class DaoFactory {
    
    public static Dao getDaoBd(){
        return new DaoImplementacionJDBC();
        
        
    }
    
     public static Dao getDaoFi(){
       
        return new DaoImplementacionFile();
    }
    
    
}
