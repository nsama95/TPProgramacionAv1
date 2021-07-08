
import java.util.ArrayList;

import Servicios.Consultas;
//import Utilidades.UBean;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Persona p = new Persona();
		
		p.setId(1);
		p.setDni(18111);
		p.setApellido("gomez");
		p.setNombre("julio");
		
		//Consultas.guardar(p);
		//Consultas.modificar(p);
		//Consultas.eliminar(p);
		//Persona per = (Persona) Consultas.obtenerPorId(p.getClass(), p.getId());
		//ArrayList<Persona> arr = (ArrayList<Persona>) Consultas.obtenerTodos(Persona.class);
	}

}
