package Utilidades;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Propiedades {
	
	public static String getProperty(String nombreProperty) {
		
		Properties props = new Properties();
		try 
		{
			//lee la lista de propiedades
			props.load(new FileInputStream(System.getProperty("user.dir")+"/Bin/Resources/framework.properties"));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return props.getProperty(nombreProperty);
	}
	
	
	

}

