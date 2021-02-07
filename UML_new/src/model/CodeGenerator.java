package model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/*
 * Dient als grobe Vorlage verschiedenster Codegeneratoren (Java, C#, Python...). * 
 */

public abstract class CodeGenerator {
	
	private String outputDir;
	private AnchorPane canvas;
	private String ext;
	
	/**
	 * @param canvas Die Zeichenfläche, die die UML-Entitäten enthält
	 * @param outputDir Verzeichnis, in dem die Dateien erstellt werden sollen
	 * @param File extension
	 * 
	 */
	public CodeGenerator(AnchorPane canvas, String outputDir, String ext) {
		this.outputDir = outputDir;
		this.canvas = canvas;
		this.ext = ext;
	}
	
	/**
	 * Ermittelt den Quellcode für eine Eigenschaft und gibt diesen zurück
	 * @param property Eigenschaft, für den der Quellcode erstellt werden soll.
	 * @return Erstellter Quellcode für die Eigenschaft
	 */
	public abstract String getPropertyCode(Property property);
	
	/**
	 * Ermittelt den Quellcode für einen Parameter einer Methode.
	 * @param parameter Der Parameter, für den der Quellcode erstellt werden soll.
	 * @param separator Trennzeichen zwischen Parametern - üblicherweise ein Komma
	 * @return
	 */
	public abstract String getParameterCode(Parameter parameter, String separator);
	
	/**
	 * Ermittelt den Quellcode für eine Methode.
	 * @param method Methode, für den der Quellcode erstellt werden soll
	 * @return Erstellter Quellcode für die Methode
	 */
	public abstract String getMethodCode(model.Method method);
	
	/**
	 * Ermittelt den gesamten Quellcode für eine Entity (Interface oder Klasse)
	 * @param umlEntity Entität, für die der Quellcode erstellt werden soll
	 * @return Erstellter Quellcode für die Entität
	 */
	public abstract String getEntityCode(Entity umlEntity);
	
	/**
	 * 	Schreibt den Quellcode der Klasse oder des Interfaces in eine entsprechende Datei 
	 */
	public void saveAllClasses() throws FileNotFoundException {
		ObservableList<Node> nodes = canvas.getChildren();
		
		for(Node entity : nodes) {
			if(!(entity instanceof UMLClass || entity instanceof UMLInterface))
				continue;
			
			Entity umlEntity = (Entity)entity;
			
			String entityCode = getEntityCode(umlEntity);	
			
			PrintWriter out = new PrintWriter(outputDir + "\\" + umlEntity.entityNameProperty().get() + ext);
			
			out.println(entityCode);
			
			out.close();
			
		}		
	}	
}
