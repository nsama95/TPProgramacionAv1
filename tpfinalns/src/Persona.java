import Anotaciones.Columna;
import Anotaciones.Id;
import Anotaciones.Tabla;
import Anotaciones.Columna;

@Tabla(nombre = "Persona")
public class Persona {
	@Id
	@Columna(nombre = "Id")
	private Integer id;
	@Columna(nombre = "Dni")
	private Integer dni ;

	@Columna(nombre = "Nombre")
	private String nombre;
	@Columna(nombre = "Apellido")
	private String apellido;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Integer getDni() {
		return dni;
	}
	public void setDni(Integer dni) {
		this.dni = dni;
	}
	public Persona() {
	}
	
	public Persona(String nombre, String apellido) {
		this.nombre = nombre;
		this.apellido = apellido;
	}
	@Override 	
	public String toString()
	{
		String p="";
		return  p.concat("nombre:"+this.nombre + " apellido: " + this.apellido) ;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj.getClass().equals(this.getClass()))
		{
			Persona p = (Persona)obj; 
			
			if(this.nombre.equals(p.nombre) && this.apellido.equals(p.apellido))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	

	@Override
	public int hashCode() 
	{
		int primo = 21;
		int resultado = 0;
		resultado += primo*this.nombre.hashCode();
		resultado += primo*this.apellido.hashCode();
		
		return resultado;
	}
}
