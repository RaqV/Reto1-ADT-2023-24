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
import utilidades.MyObjectOutputStream;
import utilidades.Util;
import static utilidades.Util.calculoFichero;

/**
 *
 * @author rvalv
 */
public class DaoImplementacionFile implements Dao {

    //Atributos
    private ResourceBundle configFile;
    private String rutaFich;

    private File fichConvocatorias;

    public DaoImplementacionFile() {
        this.configFile = ResourceBundle.getBundle("control.config");
        this.rutaFich = this.configFile.getString("Fich");

        this.fichConvocatorias = new File(rutaFich);
        //this.fichConvocatorias = new File("convocatorias.obj");

    }

    @Override
    public boolean crearConvocatoria(ConvocatoriaExamen c) throws DaoException {
        ObjectOutputStream os = null;
        boolean correcto = false;

        try {
            if (fichConvocatorias.exists()) {
                os = new MyObjectOutputStream(new FileOutputStream(fichConvocatorias, true));
            } else {
                os = new ObjectOutputStream(new FileOutputStream(fichConvocatorias));
            }

            os.writeObject(c);
            correcto = true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return correcto;
    }

//   
    @Override
    public boolean crearUnidadDidactica(UnidadDidactica u) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        //VACIO 
    }

    @Override
    public boolean crearEnunciado(Enunciado e) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        //VACIO 
    }

    @Override
    public Enunciado consultarEnunciado(Enunciado e) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //VACIO 
    }

  

    @Override
    public UnidadDidactica consultarUnidadDidactica(UnidadDidactica u) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ConvocatoriaExamen consultarConvocatoria(ConvocatoriaExamen c) throws DaoException {
        // Listamos toda la información

        ConvocatoriaExamen convocatoria = null;

        ObjectInputStream ois = null;

        // Buscamos en el fichero
        if (fichConvocatorias.exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(fichConvocatorias));

                while (true) {
                    convocatoria = (ConvocatoriaExamen) ois.readObject();
                    //
                    if (convocatoria.getConvocatoria().equalsIgnoreCase(c.getConvocatoria())) {
                        break;
                    }
                }
            } catch (EOFException e) {

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            } catch (ClassNotFoundException e) {

                e.printStackTrace();
            } finally {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return convocatoria;
    }

    @Override
    public boolean asignarEnunciadoAConvocatoria(Enunciado e, ConvocatoriaExamen c) {
        File aux = new File("temporal.dat");
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        int numConvocatorias;
        boolean modificado = false;
        ConvocatoriaExamen convocatoria;

        if (fichConvocatorias.exists()) {
            numConvocatorias = Util.calculoFichero(fichConvocatorias);

            try {
                ois = new ObjectInputStream(new FileInputStream(fichConvocatorias));
                oos = new ObjectOutputStream(new FileOutputStream(aux));

                for (int i = 0; i < numConvocatorias; i++) {
                    convocatoria = (ConvocatoriaExamen) ois.readObject();
                    if (convocatoria.getConvocatoria().equalsIgnoreCase(c.getConvocatoria())) {
                        convocatoria.setIdEnunciado(e.getId());
                        modificado = true;
                    }
                    oos.writeObject(convocatoria);
                }
                //Hay que poner una excepción
//		if (!modificado) {
//                    System.out.println("NO se ha encontrado la alumna buscada");
//		}
            } catch (IOException ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            } finally {
                try {
                    ois.close();
                    oos.flush();
                    oos.close();
                    if (modificado) {
                        fichConvocatorias.delete();
                        aux.renameTo(fichConvocatorias);
                    }
                } catch (IOException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
            }
        }
//        else {
//			System.out.println("No hay información en el fichero. Ir a la opción 1 de menu");
//		}
        return modificado;
    }

    @Override
    public List<ConvocatoriaExamen> consultarConvocatoriasE(Enunciado e) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
