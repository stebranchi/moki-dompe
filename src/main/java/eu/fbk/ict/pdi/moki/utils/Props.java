package eu.fbk.ict.pdi.moki.utils;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class Props {

	public static void main(String[] args)throws Exception{ 
		ClassLoader classLoader = Props.class.getClassLoader();
		File file = new File(classLoader.getResource("somefile").getFile());
	    FileReader reader=new FileReader(file);  
	      
	    Properties p=new Properties();  
	    p.load(reader);  
	      
	    System.out.println(p.getProperty("user"));  
	    System.out.println(p.getProperty("password"));  
	}  
}