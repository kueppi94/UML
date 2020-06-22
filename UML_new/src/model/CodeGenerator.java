package model;

import javafx.scene.Node;

/*
 * Dient als grobe Vorlage verschiedenster Codegeneratoren (Java, C#, Python...). * 
 */

public abstract class CodeGenerator {
	
	private String outputDir;
	private Node canvas;
	private String ext;
	
	/**
	 * @param canvas Die Zeichenfläche, die die UML-Entitäten enthält
	 * @param outputDir Verzeichnis, in dem die Dateien erstellt werden sollen
	 * @param File extension
	 * 
	 */
	public CodeGenerator(Node canvas, String outputDir, String ext) {
		this.outputDir = outputDir;
		this.canvas = canvas;
		this.ext = ext;
	}
	
	public abstract String getPropertyCode(Property property);
	
	public abstract String getParameterCode(Parameter parameter, String separator);
	
	public abstract String getMethodCode(model.Method method);
	
	public abstract void saveClassToFile(UMLClass umlClass);
}
