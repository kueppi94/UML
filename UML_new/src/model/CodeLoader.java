package model;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

import javafx.scene.layout.AnchorPane;

/*
 * Dient als grobe Vorlage verschiedenster CodeLoader (Java, C#, Python...). * 
 */

public abstract class CodeLoader {
	protected String inputDir;
	protected AnchorPane canvas;
	protected String ext;
	
	protected File[] files;
	
	/**
	 * 
	 * @param canvas Zeichenfl�che, auf der die UML-Entit�ten erstellt werden sollen.
	 * @param inputDir Verzeichnis welches die Quellcode-Dateien enth�lt
	 * @param ext Dateiendung der Quellcode-Dateien
	 */
	public CodeLoader(AnchorPane canvas, String inputDir, String ext) {
		this.inputDir = inputDir;
		this.canvas = canvas;
		this.ext = ext;
		
		files = getFilesFromFolder();
	}
	
	/**
	 * Erstellt eine UML-Klasse auf Basis des �bergebenen Codes
	 * @param code Code, f�r den die Klasse erstellt werden soll
	 * @return erstellte UML-Node
	 */
	public abstract UMLClass getClassFromCode(String code);
	/**
	 * Erstellt ein UML-Interface auf Basis des �bergebenen Codes
	 * @param code Code, f�r den das Interface erstellt werden soll
	 * @return erstellte UML-Node
	 */
	public abstract UMLInterface getInterfaceFromCode(String code);
	
	/**
	 * Gibt eine Liste von allen UML-Nodes der Klassen zur�ck
	 * @return Liste aller UML-Nodes
	 */
	public abstract List<UMLClass> getAllClasses();
	/**
	 * Gibt eine Liste von allen UML-Nodes der Interfaces zur�ck
	 * @return Liste aller UML-Nodes
	 */
	public abstract List<UMLInterface> getAllInterfaces();
	
	
	/**
	 * Ermittelt alle Dateien aus dem inputDir mit g�ltiger Dateiendung
	 * @return Array von Files
	 */
	private File[] getFilesFromFolder() {
		File folder = new File(inputDir);
		FileFilter filter = new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				if(pathname.getAbsolutePath().toLowerCase().endsWith(ext))
					return true;			
			return false;
			}
		};
		
		return folder.listFiles(filter);
		
	}
}
