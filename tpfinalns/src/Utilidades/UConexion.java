package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class UConexion {
	private static final String DRIVER_CONECTION = Propiedades.getProperty("prop.Driver").toString();
	private static final String PATH_CONECTION = Propiedades.getProperty("prop.ubicaciondb").toString();
	private static final String USER = Propiedades.getProperty("prop.usuariodb").toString();
	private static final String PASSWORD = Propiedades.getProperty("prop.password").toString();
	private static Connection CONN = null;

	private UConexion(){
		try {
			
			Class.forName(DRIVER_CONECTION);
			CONN = DriverManager.getConnection(PATH_CONECTION, USER, PASSWORD);
			System.out.println("Conexion abierta ");
			
			} 
			
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (SQLException e) 
			{
				
				e.printStackTrace();
			}
	}
		
	public static Connection getConnection()
	{
	    if (CONN == null){
	    	new UConexion();
	    	}
	    return CONN;
	}

	public static void cerrarConexion()
	{
		if (CONN != null)
		{
			try {
				System.out.println("Conexion cerrada");
				CONN.close();
				CONN = null;
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	}
}
