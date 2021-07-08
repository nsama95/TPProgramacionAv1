package Utilidades;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;


public class UBean {
	
	// devuelve los atributos declarados de object en una lista para obtener la posicion de los elementos
	public static ArrayList<Field> obtenerAtributos(Object o) {
		ArrayList<Field> fields = new ArrayList<Field>();
		Class cl = o.getClass();
		//getDeclaredFields->todos los atributos declarados en la clase,getFields trae los atrubutos accesibles desde fuera desde la clase
		for(Field field: cl.getDeclaredFields()) {
			fields.add(field);
		}
		return fields;
	}
	
	//ejecuta los setter del objeto
	public static void ejecutarSet(Object o, String att, Object valor) {
		Class cl = o.getClass();
		//creo un array de objet de una posicion ya que es un solo parametro
		Object[] parametros = new Object[1];
		String nombreSetter = "set".concat(att.substring(0, 1).toUpperCase()).concat(att.substring(1));
		parametros[0] = valor;
		
		for(Method m: cl.getDeclaredMethods()) {
			if(m.getName().equals(nombreSetter)) {
				try{
					m.invoke(o, parametros);
					break;
				} 
				catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//  retorna el valos del atributo que le paso por parametro y ejecuta el get
	public static Object ejecutarGet(Object o, String att) {
		
		Object retorno = null;
		Class cl = o.getClass();
		String nombreGetter = "get".concat(att.substring(0, 1).toUpperCase()).concat(att.substring(1));

		for (Method m : cl.getDeclaredMethods()) {
			if (nombreGetter.equals(m.getName())) {
				try {
					retorno = m.invoke(o);
					break;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}

		}
		return retorno;
	}
}
