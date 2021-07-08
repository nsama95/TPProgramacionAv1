package Servicios;

import Utilidades.UBean;
import Utilidades.UConexion;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.lang.annotation.Annotation;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import Anotaciones.Columna;
import Anotaciones.Id;
import Anotaciones.Tabla;

public class Consultas {
	
	public static String guardar(Object o) {
	
		String query = "insert into ".concat(o.getClass().getAnnotation(Tabla.class).nombre()).concat(" (");
		ArrayList<Field> fields = UBean.obtenerAtributos(o);

		for (Field field : fields) {
			if (field.getAnnotation(Columna.class) != null) {
				query += field.getAnnotation(Columna.class).nombre().concat(",");
			}
		}

		query = query.substring(0, query.length()- 1);
		query += ") values (";

		for (Field field : fields) {
			if (field.getAnnotation(Columna.class) != null) {
				if(field.getType().equals(String.class)) {
					query += "'" + UBean.ejecutarGet(o, field.getName()).toString() + "',";
				}
				else {
					query +=  UBean.ejecutarGet(o, field.getName()).toString() + ",";
				}
				
			}
		}
		query = query.substring(0, query.length() - 1).concat(")");
		try {
			System.out.println(query);
			Connection con = UConexion.getConnection();
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.execute();
			UConexion.cerrarConexion();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return query;
	}


	public static void modificar(Object o) {

		
			String query = "update ".concat(o.getClass().getAnnotation(Tabla.class).nombre()).concat(" set ");
			
			ArrayList<Field> fields = UBean.obtenerAtributos(o);
	
			String queryId = null;
			for (Field f : fields) {
				
				if (f.getAnnotation(Columna.class) != null) {
					
					query +=f.getAnnotation(Columna.class).nombre();
					if(f.getType() == String.class) {
						query += " = '".concat(UBean.ejecutarGet(o, f.getName()).toString()).concat("',");
					}
					else {
						query += " = ".concat(UBean.ejecutarGet(o, f.getName()).toString()).concat( ",");
					}
				}
					
				
			}
			query = query.substring(0, query.length() - 1);
			query += " ";
			
				for (Field f : fields) {
				
				if (f.getAnnotation(Id.class) != null) {
					
					queryId = "where " + f.getName().toString() + " = " + UBean.ejecutarGet(o, f.getName().toString());
				}
					
				
			}
			
			query += queryId;
			try {
				System.out.println(query);
			Connection con = UConexion.getConnection();
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);	
			if(ps != null) {
				ps.execute();
			}
			UConexion.cerrarConexion();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void eliminar(Object o) {
		
			
			
			String query = "DELETE FROM ".concat(o.getClass().getAnnotation(Tabla.class).nombre()).concat(" WHERE ");
			ArrayList<Field> fields = UBean.obtenerAtributos(o);
			
			
			for (Field f : fields) {
				if (f.getAnnotation(Id.class) != null) {
					query += f.getAnnotation(Columna.class).nombre().concat(" = ").concat(UBean.ejecutarGet(o, f.getName()).toString());
				}
			}
			try {
				System.out.println(query);
			Connection con = UConexion.getConnection();
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);	
			if(ps != null) {
				ps.execute();
			}
			UConexion.cerrarConexion();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object obtenerPorId(Class c, Object id)  {
		System.out.println(id);
		try {
			Object obj = null;
			Constructor[] constructors = c.getConstructors();
			for (Constructor ct : constructors) {
				if (ct.getParameterCount() == 0) {
					obj = ct.newInstance();
					break;
				}
			}
			
			ArrayList<Field> fields = UBean.obtenerAtributos(obj);

			String query = "SELECT * FROM ".concat(obj.getClass().getAnnotation(Tabla.class).nombre()).concat(" WHERE ");
			
			PreparedStatement pst = null;
			for (Field f : fields) {
				if (f.getAnnotation(Id.class) != null) {
					query +=  f.getAnnotation(Columna.class).nombre() + " = " ;
					
					if (f.getAnnotatedType().getType().equals(String.class))
					{
						query +="'"+ id.toString()+"'";
					}
					
					else
					{
						query += id.toString();
					}
				}
			}
			
			
			System.out.println(query);
			Connection con = UConexion.getConnection();
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);	
			if(ps != null) {
				ps.execute();
			}
			UConexion.cerrarConexion();
			return obj;
			} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		return id;

		
	}



	public static ArrayList<?> obtenerTodos(Class<?> c) {
		try {
		ArrayList<Object> listPersona = new ArrayList<Object>();
		Object obj = null;
		Constructor[] constructors = c.getConstructors();
		for (Constructor ct : constructors) {
			if (ct.getParameterCount() == 0) {
				obj = ct.newInstance();
				break;
			}
		}
		ArrayList<Field> fields = UBean.obtenerAtributos(obj);
		
		String query = "SELECT * FROM ".concat(obj.getClass().getAnnotation(Tabla.class).nombre());
		
		System.out.println(query);
			Connection con = UConexion.getConnection();
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);	
			
			//es un enlace al resultado
			ResultSet result= ps.executeQuery();
			
			int i = 1;
			while (result.next()) {
				for (Field f : fields) {

					if (f.getAnnotation(Id.class) == null) {
						
						if (f.getAnnotatedType().getType().equals(String.class))
						{
							UBean.ejecutarSet(obj, f.getName().toString(), result.getString(i));
						}
						 else {
						UBean.ejecutarSet(obj, f.getName().toString(), result.getInt(i));
					}
					}
					i++;
				}
				listPersona.add(c.cast(obj));
				i = 1;
			}	
	
			UConexion.cerrarConexion();
			System.out.println(listPersona);
			return listPersona;
		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;	
	}

}
