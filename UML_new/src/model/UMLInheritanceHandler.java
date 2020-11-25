package model;

import java.awt.Paint;
import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class UMLInheritanceHandler extends ConnectionLine {
	
	//UMLClass superclass;
	//UMLClass subclass;
	
	public UMLInheritanceHandler(UMLClass superclass, UMLClass subclass) {
		super(subclass, superclass);
		
		subclass.setSuperclass(superclass);
	}
	
	
	/*
	 * Erstellt die notwendigen Komponenten für die Zeichnungsfläche und gibt diese zurück.	 
	 */
	public Node connect(){				
		Polygon triangle = new Polygon(15.0, 20.0,
			    30.0, 0.0,
			    0.0, 0.0);
		
		triangle.setFill(Color.WHITE);			
		triangle.setStroke(Color.BLACK);
		triangle.setStrokeWidth(3.0);
		
		this.add(triangle, 15);
		
		return this;
	}
}


