 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import clases.ConvocatoriaExamen;
import clases.Dificultad;
import clases.Enunciado;
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
                    crearConvocatoriaExamen(controlador);
                    break;
                case 3:
                    crearEnunciado(controlador);
                    break;
                case 4:
                    //consultarEnunciados();
                    break;
                case 5:
                    //consultarConvocatoriasExamen();
                    break;
                case 6:
                    visualizarEnunciado(controlador);
                    break;
                case 7:
                    //controlador.asignarEnunciadoAConvocatoria();
                    break;
                default:
                    System.out.println("Agur");
            }
           } while (opc!=8);
            
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

    private static void crearConvocatoriaExamen(Controlador controlador) throws DaoException {
        
        ConvocatoriaExamen convocatoria = new ConvocatoriaExamen();
        convocatoria.setConvocatoria(Util.introducirCadena("Introduce el Acronimo"));
        convocatoria.setDescripcion(Util.introducirCadena("Introduce Descripción"));
        convocatoria.setFecha(Util.leerFecha("Introduce la fecha"));
        convocatoria.setCurso(Util.introducirCadena("Introduce el curso"));
        
                
        System.out.println(controlador.crearConvocatoriaExamen(convocatoria)? "Convocatoria de Examen Creada":" No se ha podido crear la Convocatoria de Examen" );
    }

    private static void crearEnunciado(Controlador controlador) throws DaoException {
      String [] posibilidades = {"Alta", "Media", "Baja"};
      UnidadDidactica unidad = new UnidadDidactica();
      ConvocatoriaExamen convocatoria = new ConvocatoriaExamen();
        
      Enunciado enunciado = new Enunciado();
      enunciado.setDescripcion(Util.introducirCadena("Introduce la Descripción"));
      String cadena = Util.introducirCadena(posibilidades, "Introduce la dificultad: Alta, Baja o Media");
      enunciado.setNivel(Dificultad.valueOf(cadena));
      enunciado.setDisponibleClase(Util.leerBoolean("Esta Disponible????"));
      enunciado.setRuta(Util.introducirCadena("Introduce la ruta en la que esta guardado"));
      
      //Unidades Didácticas incluidas para el Enunciado
        System.out.println("Introduce ahora las Unidades Didácticas que se trabajan en el Enunciado: ");
      do{ 
        unidad.setAcronimo(Util.introducirCadena("Introduce el Acronimo"));
        //Tenemos que comprobar que exista la unidad
        if ((unidad = controlador.consultarUnidadDidactica(unidad))!= null){
            enunciado.getUnidades().add(unidad);
        }else{
            System.out.println("No existe la Unidad Didáctica para la que quieres crear el Enunciado. Tienes que crearla!!");
        }
      }while(Util.leerBoolean("Quires añadir más Uniddades Didácticas?"));
      
      if(!enunciado.getUnidades().isEmpty()){
        //Convocatoria de Examen para el enunciado
        convocatoria.setConvocatoria(Util.introducirCadena("Introduce la convocatoria"));
        //Tenemos que comprobar que exista la convocatoria
        if (controlador.consultarConvocatoriaExamen(convocatoria)!= null){
          System.out.println(controlador.crearEnunciado(enunciado, convocatoria)? "Enunciado de Examen Creado":" No se ha podido crear el Enunciado" );
        }else{
            System.out.println("No existe la Convocatoria para la que quieres crear el Enunciado. Crea primero la Convocatoria!!");
        }
      }else{
          System.out.println("No existe ninguna de la Unidades Didácticas para las que quieres crear el Enunciado. Crea primero alguna de estas U.D.!!");
      }
    }

    private static void visualizarEnunciado(Controlador controlador) {
        Enunciado enunciado = new Enunciado();
        
        enunciado.setId(Util.leerInt("Introduce la Id del Enunciado"));
        System.out.println(controlador.visualizarEnunciado(enunciado)? "Enunciado de Examen Visualizado":" No se ha podido visualizar el Enunciado" );
    }
}
