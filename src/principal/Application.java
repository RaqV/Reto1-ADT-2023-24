/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import control.Controlador;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import utilidades.Util;
import vista.Menu;


/**
 *
 * @author rvalv
 */
//VERSIÓN 4: INDEPENDIZAR EL CONTROLADOR DEL MODO VISTA Y ACTUALIZAR EL BALANCE DEL MOVIMIENTO Y ACTUALIZACIÓN
//DE LA CUENTA CON EL NUEVO BALANCE. IMPLEMENTAR TRANSACCIÓN PARA ACTUALIZAR CTA Y CREAR MOVIMIENTO
public class Application {

    //private static Dao dao = new DaoImplementationJDBC();
    //private static Dao dao = new DaoImplementationFile();
            
    public static void main(String[] args) throws IOException {
        
        Menu.visualizarMenu(new Controlador());
    }
    
}
