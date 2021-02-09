package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.layout.AnchorPane;

/*
 * Eine Klasse, welche Java-Code UML-Nodes überführt
 */

public class CodeLoaderJava extends CodeLoader {
	
	public CodeLoaderJava(AnchorPane canvas, String inputDir, String ext) {
		super(canvas, inputDir, ext);		
	}

	@Override
	public UMLClass getClassFromCode(String code) {
		
		code = code.replaceAll("[\n\t]", "");
		code = code.trim();
		
		String classSignature = code.substring(0, code.indexOf("{"));
		//imports und package Befehle ausschneiden
		classSignature = classSignature.substring(classSignature.lastIndexOf(";") + 1, classSignature.length());
		
		String[] classSignatureArray = classSignature.trim().split("\\s+");
		
		ArrayList<String> classSignatureList = new ArrayList<String>(Arrays.asList(classSignatureArray));
		
		
		//Klasse hat keine Sichtbarkeit
		if(classSignatureList.get(0).equals("class"))
			classSignatureList.add(0, null);		
		
		Visibility v = Visibility.find(classSignatureList.get(0));
		String className = classSignatureList.get(2);
		String superclass = null;
		
		if(classSignatureList.contains("extends"))
			superclass = classSignatureList.get(4).trim();		
		
		
		UMLClass umlClass = new UMLClass(className);
		umlClass.setVisibility(v);	
		
		umlClass.setSuperclassHelper(superclass);
		
		List<Method> list = getMethodsFromCode(code);		
		
		for(Method m : list)
			umlClass.addMethod(m);
		
		List<Property> propList = getPropertiesFromCode(code);
		
		for(Property p : propList)
			umlClass.addProperty(p);
		
		return umlClass;
	}
	
	private List<Property> getPropertiesFromCode(String code){
		ArrayList<Property> list = new ArrayList<Property>();
		
		//Start des Klassen- Interfacekörpers
		int entityContentIndex = code.indexOf("{") + 1;
		String entityContent = code.substring(entityContentIndex, code.length() -1);
		//Trennt Klassen und Interfaces logisch auf, sodass Methoden leichter erkannt werden können
		String[] entityContentArray = entityContent.split(";|}");
		
		for(String s : entityContentArray)
		{
			//Daten aufbereiten
			s = s.trim();
			
			//Überspringe Methoden
			if(s.contains("("))
				continue;
			
			
			String[] help = s.split(" ");				
			
			Visibility v;
			DataType datatype;
			String name;
			
			if(help.length == 3) {
				v = Visibility.find(help[0]);	
				datatype = DataType.valueOf(help[1].toUpperCase());
				name = help[2];
			}
			else if(help.length == 2) {
				v = Visibility.NO_MODIFIER;	
				datatype = DataType.valueOf(help[0].toUpperCase());
				name = help[1];
			}
			//Zu wenig oder zu viele Argumente
			else
				continue;
			
			
			Property p = new Property(v, name, datatype);		
			
			list.add(p);
		}		
		
		return list;
	}
	
	private List<Method> getMethodsFromCode(String code) {
		ArrayList<Method> list = new ArrayList<Method>();
		
		//Start des Klassen- Interfacekörpers
		int entityContentIndex = code.indexOf("{") + 1;
		String entityContent = code.substring(entityContentIndex, code.length() -1);
		//Trennt Klassen und Interfaces logisch auf, sodass Methoden leichter erkannt werden können
		String[] entityContentArray = entityContent.split(";|}");
		
		
		
		for(String s : entityContentArray) {						
			//Der Abschnitt ist keine Methode
			if(!s.contains("("))
				continue;	
			
			//System.out.println(s);
						
			String[] help = s.split(" ");
						
			Visibility v = Visibility.find(help[0]);
			boolean isAbstract = s.contains("abstract");
						
			
			String[] paras = s.substring(s.indexOf("(") +1, s.indexOf(")")).split(",");
			
			Method m = new Method(v, isAbstract, "name", DataType.BOOLEAN);			
			
			
			for(int i = 0; i < paras.length; i++)	{				
				System.out.println(paras[i]);
				paras[i] = paras[i].strip();
				String[] para = paras[i].split(" ");
				
				//ungültiger Parameter
				if(para.length < 2)
					continue;
				
				
				Parameter p = new Parameter(para[1], DataType.valueOf(para[0].toUpperCase()));			
				
				m.addParameter(p);
				
			}			
			
			list.add(m);			
		}	
		return list;
	}
	

	@Override
	public UMLInterface getInterfaceFromCode(String code) {
		code = code.replaceAll("[\n\t]", "");
		code = code.trim();
		
		String interfaceSignature = code.substring(0, code.indexOf("{"));
		//imports und package Befehle ausschneiden
		interfaceSignature = interfaceSignature.substring(interfaceSignature.lastIndexOf(";") + 1, interfaceSignature.length());	
		
		String[] interfaceSignatureArray = interfaceSignature.trim().split("\\s+");
		
		ArrayList<String> interfaceSignatureList = new ArrayList<String>(Arrays.asList(interfaceSignatureArray));
		
		
		//Interface hat keine Sichtbarkeit im Quelltext -> Dummy-Wert einfügen
		if(interfaceSignatureList.get(0).equals("interface"))
			interfaceSignatureList.add(0, null);			
		
		String interfaceName = interfaceSignatureList.get(2);			
		
		UMLInterface umlInterface = new UMLInterface(interfaceName);			
		
		List<Method> list = getMethodsFromCode(code);		
		
		for(Method m : list)
			umlInterface.addMethod(m);
		
		List<Property> propList = getPropertiesFromCode(code);
		
		for(Property p : propList)
			umlInterface.addProperty(p);
		
		return umlInterface;
		
		
	}

	@Override
	public List<UMLClass> getAllClasses() {
		ArrayList<UMLClass> classes = new ArrayList<UMLClass>();
		
		try {
			for(File f : files) {
				String code = Files.readString(Paths.get(f.getAbsolutePath()));
				if(code.contains("class"))
					classes.add(getClassFromCode(code));				
					
			}			
		}
		catch(IOException e) {
			return null;
		}
		
		
		return classes;
	}

	@Override
	public List<UMLInterface> getAllInterfaces() {
		ArrayList<UMLInterface> interfaces = new ArrayList<UMLInterface>();
		
		try {
			for(File f : files) {
				String code = Files.readString(Paths.get(f.getAbsolutePath()));
				if(code.contains("interface"))
					interfaces.add(getInterfaceFromCode(code));
			}			
		}
		catch(IOException e) {
			return null;
		}
		
		
		return interfaces;
	}	
}
