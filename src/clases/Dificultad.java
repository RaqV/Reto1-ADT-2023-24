package clases;

/**
 * Esta clase define el privilegio del usuario.
 * @author Steven Arce
 */
public enum Dificultad {
    
    ALTA, MEDIA, BAJA;

   
    
    public static Dificultad getDificultad(String dificultad)
    {
        for(Dificultad col : values())
        {
            if(col.equals(dificultad))
                return col;
        }
        return Dificultad.ALTA;
    }
   
}
