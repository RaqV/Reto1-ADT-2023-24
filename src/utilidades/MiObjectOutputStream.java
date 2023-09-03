package utilidades;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MiObjectOutputStream extends ObjectOutputStream{
	
	//Sobrescribimos el método que crea la cabecera 
		protected void writeStreamHeader() throws IOException {
			// No hacer nada. 
			reset();
		}

		//Constructores
		public MiObjectOutputStream () throws IOException{ 
			super();
		}
		
		public MiObjectOutputStream(OutputStream out) throws IOException{
			super(out);
		}
}

