 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import clases.UnidadDidactica;
import control.Controlador;
import exception.DaoException;
import utilidades.Util;


/**
 *
 * @author yeguo
 */
public class Menu {

    public static void visualizarMenu(Controlador controlador) throws DaoException {
        int opc;

        do{
         
            opc=leerOpcionMenu();

            switch(opc){
                case 1:
                    crearUnidadDidactica(controlador);
                    break;
                case 2:
                    controlador.crearConvocatoriaExamen();
                    break;
                case 3:
                    controlador.crearEnunciado();
                    break;
                case 4:
                    controlador.consultarEnunciados();
                    break;
                case 5:
                    controlador.consultarConvocatoriasExamen();
                    break;
                case 6:
                    controlador.visualizarEnunciado();
                    break;
                case 7:
                    controlador.asignarEnunciadoAConvocatoria();
                    break;
                default:
                    System.out.println("Agur");
            }
           } while (opc!=9);
            
    }

    
    private static int leerOpcionMenu() {
        int opc;
        
        System.out.println("*********************************************************************************");
        System.out.println("                               MENU    ");
        System.out.println("1. Crear Unidad Didáctica");
        System.out.println("2. Crear Convocatoria de Examen");
        System.out.println("3. Crear Enunciado.");
        System.out.println("4. Consultar los Enunciados que referencian una Unidad Didáctica");
        System.out.println("5. Consultar en que Convocatorias de Examen se ha utilizado un Enunciado");
        System.out.println("6. Visualizar Enunciado");
        System.out.println("7. Asignar un enunciado a una Convocatoria");
        System.out.println("8. Salir");
        System.out.println("*********************************************************************************");
        
        opc= utilidades.Util.leerInt(0, 8, "Introduce la opción deseada");
        return opc;
    }

    

    private static void crearUnidadDidactica(Controlador controlador) throws DaoException {
        
    
        UnidadDidactica unidad = new UnidadDidactica();
        unidad.setAcronimo(Util.introducirCadena("Introduce el Acronimo"));
        unidad.setTitulo(Util.introducirCadena("Introduce el título"));
        unidad.setEvaluacion(Util.introducirCadena("Introduce Evaluación"));
        unidad.setDescripcion(Util.introducirCadena("Introduce Descripción"));
                
        System.out.println(controlador.crearUnidadDidactica(unidad)? "Unidad Dídáctica Creada":" No se ha podido crear la Unidad Didáctica" );
    }
}
