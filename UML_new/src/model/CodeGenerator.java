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
	
	public abstract String getPropertyCode(Property property);
	
	public abstract String getParameterCode(Parameter parameter, String separator);
	
	public abstract String getMethodCode(model.Method method);
	
	public abstract String getEntityCode(Entity umlEntity);
	
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
